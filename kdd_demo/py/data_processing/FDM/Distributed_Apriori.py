# -*- coding: utf-8 -*-
"""
Created on Mon Apr 09 09:12:04 2012
distributed computing of associate rule(Apriori)
@author: Yingzhen Li, KDD@SYSU
"""
from remote import remote
from polling import polling
from home import home
from mpi4py import MPI
import math
from dataImport import *
import cPickle
comm = MPI.COMM_WORLD
size = comm.Get_size()   
rank = comm.Get_rank()

k = 1
data = []
'''read data from txt'''
fileName = 'user_key_word'
# temp = importData(fileName)
f = open('data_' + fileName, 'r')
temp = cPickle.loads(''.join(f.readlines()))
f.close()
NORM = int(math.ceil(float(len(temp))/size))
if rank != 0:
    if rank < size - 1:
        data = dict(temp.items()[(rank-1)*NORM:rank*NORM])
    if rank == size - 1:
        data = dict(temp.items()[(rank-1)*NORM:])

'''initialize sites''' 
REMOTE = remote(data, {})
del temp, data
SUP = 0.2/NORM   # threshold of support
CONF = 0.7	 # threshold of confidence
'''rank 0: root need no initializaion'''
    
'''main'''
breakFlag = 0
GLOBAL_LARGE = []
while 1:
    '''remmote site'''
    Transaction = []
    if rank != 0:
        '''receive global_large from home site'''
        if k > 1 and breakFlag == 0:
            temp = comm.recv(source = 0, tag = 3*(k-1))
	    if temp == 'NO GLOBAL_LARGE':
		breakFlag = 1
	    else:
		REMOTE.global_large = temp
		del temp
            print 'REMOTE SITE ' + str(rank) + ' DONE: receive global_large from HOME SITE' + ', STEP ' + str(k)
        '''initialize'''
        if breakFlag == 0:
            Transaction = REMOTE.transaction(k)
        '''if no candidate then exit'''  
        if len(Transaction) == 0:
            breakFlag = 1

    '''break'''
    flag = comm.gather(breakFlag, 0)
    LARGE = comm.gather(REMOTE.global_large, 0)
    if rank == 0:
    	if sum(flag) == size - 1:
	    print 'break at step ' + str(k)
            break
    
    '''home site'''
    #temp = comm.gather(Transaction, 0)
    #candidate_set = set()
    #if rank == 0:
	#print 'HOME SITE: gather candidate transactions done' + ', STEP ' + str(k)     
        #for remoteJ in range(1,size):
            #candidate_set = candidate_set.union(set(temp[remoteJ]))
        #candidate_set = list(candidate_set)
	#if k > 1:	# data packed by cPickle when k > 1
            #for i in range(len(candidate_set)):
            	#candidate_set[i] = cPickle.loads(candidate_set[i])
    
    '''remote site'''
    '''receive candidate_set from home site'''
    candidate = Transaction
    local_large = []
    if rank != 0:
        '''compute local support'''
        if k > 1:
            if breakFlag == 0:
		print 'REMOTE SITE ' + str(rank) + ': get candidate from HOME, start local counting' + ', STEP ' + str(k)
                Transaction = REMOTE.local_large(candidate)
		print 'REMOTE SITE ' + str(rank) + ': Apriori_gen done' + ', STEP ' + str(k)
            '''if no candidate then exit'''  
            if len(Transaction) == 0:
                breakFlag = 1
                
    '''break'''
    flag = comm.gather(breakFlag, 0)
    if rank == 0:
    	if sum(flag) == size - 1:
	    print 'break at step ' + str(k)
            break
    
    '''remote site'''
    if rank != 0:
        '''polling'''
        local_large = REMOTE.polling(SUP, NORM, size - 1, Transaction)
        '''broadcast local_large sets to all polling sites'''
        for pollingJ in range(size - 1):
            '''send LL(i,j)(k) to rank j site''' 
	    print 'start sending LL(i,j)(k) to polling site' + str(pollingJ)  
            comm.send(local_large[pollingJ], pollingJ+1, 3*k-2)
	print 'REMOTE SITE ' + str(rank) + ': send local_large to POLLING SITES' + ', STEP ' + str(k)
  
    '''polling site'''
    Global_set = []
    Global_set_supp = []
    large_site = {} # large_site = {transaction:[site1,site2,...],...}
    if rank != 0:
        '''receive local_large sets from all remote sites'''
        local_pruning = []  #Local_Pruning = [transaction1,transaction2,...]
        for remoteJ in range(1,size):
            '''receive LL(i,j)(k) to rank j site'''
            temp = comm.recv(source = remoteJ, tag = 3*k - 2)
	    print 'POLLING SITE ' + str(rank) + ': receive local_large from REMOTE SITES' + ', STEP ' + str(k)
            for tranAndSup in temp:
                '''update large_site'''
                [local_pruning, large_site] = polling.insert(tranAndSup, remoteJ,local_pruning, large_site)
    
    '''gather support values'''
    temp = comm.gather(Transaction, 0)   
    if rank == 0:
    	for pollingJ in range(1,size):
	    comm.send(temp[1:], pollingJ, 3*k-1)
	
    if rank != 0:
        '''generate global large sets'''
	temp = comm.recv(source = 0, tag = 3*k-1)
        Global_set, Global_set_supp = polling.gen_global_set(temp, size-1, NORM, SUP, local_pruning)    
    
    '''homesite'''
    '''get global sets from all polling sites'''
    temp = comm.gather(Global_set, 0)
    temp_supp = comm.gather(Global_set_supp, 0)
    site = comm.gather(large_site, 0)
    if rank == 0:
	print 'HOME SITE: get Global_large and large_site from POLLING SITES ' + ', STEP ' + str(k)
        temp = temp[1:]
	temp_supp = temp_supp[1:]
        site = site[1:]
	if k == 1:
	    large_supp = {}
        '''generate large set'''
        large, large_supp = home.compute_large(temp, temp_supp, site, size - 1, CONF, large, large_supp)
        large_length = len(large)
        '''divide large into global_large'''
        Global_large = home.divide_global_set(size - 1, large)
        '''send global_large to remote sites'''
        for remoteJ in range(1,size):
            if flag[remoteJ] == 0:
		'''send global_large to remote site'''
		if remoteJ in Global_large.keys():
                    comm.send(Global_large[remoteJ], remoteJ, 3*k)
		else:
		    comm.send('NO GLOBAL_LARGE', remoteJ, 3*k)
	print 'STEP ' + str(k) + ' done, start STEP ' +str(k+1)
    del temp
    '''new loop'''
    k += 1
    
'''loop terminate'''
if rank == 0:
    large = []
    for remoteJ in range(1,size):
	large.extend(LARGE[remoteJ])
    large = list(set(large))
    saveData(large, 'data_keyword_class')
    print large
    
    '''EXIT'''
    comm.Abort()
