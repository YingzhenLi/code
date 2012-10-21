# -*- coding: utf-8 -*-
"""
Created on Mon Apr 09 09:12:04 2012
computing of associate rule(Apriori)
@author: Yingzhen Li, KDD@SYSU
"""
from remote import remote
from polling import polling
from home import home
import math
import cPickle
from dataImport import saveData, importData
size = 200

k = 1
'''read data from txt'''
fileName = 'user_key_word'
# temp = importData(fileName)
f = open('data_' + fileName,'r')
temp = cPickle.loads(''.join(f.readlines()))
f.close()
NORM = int(math.ceil(float(len(temp))/size))
'''initialize sites''' 
REMOTE = {} # dict of remote sites
for rank in range(size):
    if rank < size - 1:
        data = dict(temp.items()[rank * NORM : (rank + 1) * NORM])
        REMOTE[rank] = remote(data, {})
    if rank == size - 1:
        data = dict(temp.items()[rank * NORM:])
        REMOTE[rank] = remote(data, {})
        
SUP = 0.2/NORM   # threshold of support
CONF = 0.7
breakFlag = [0]* size
'''main'''
while 1:
    '''remmote site'''
    Transaction = [[]] * size
    print 'step ', k
    '''initialize'''
    for rank in range(size):
        if breakFlag[rank] == 0:
            Transaction[rank] = REMOTE[rank].transaction(k)
        if len(Transaction[rank]) == 0:   # check if no transaction
            breakFlag[rank] = 1
    '''if no candidate in all remote sites, then exit''' 
    if sum(breakFlag) == size:
        print 'BREAK IN STEP ' + str(k) + ' no new apriori set'
        break
            
    '''home site, union the Trasaction generated at every remote sites'''
    candidate_set = set()
    if k > 1:
        for remoteJ in range(size):
            candidate_set = candidate_set.union(set(Transaction[remoteJ]))
        candidate_set = list(candidate_set)
        for i in range(len(candidate_set)):
            candidate_set[i] = cPickle.loads(candidate_set[i])
    
    '''remote site'''
    local_large =[[]] * size
    for rank in range(size):
        '''compute local support'''
        if k > 1:
            if breakFlag[rank] == 0:
                Transaction[rank] = REMOTE[rank].local_large(candidate_set)
                print 'DONE! SITE ' + str(rank) 
            if len(Transaction[rank]) == 0:   # check if no transaction
                print 'BREAK IN STEP ' + str(k) + ' no new calculation'               
                breakFlag[rank] = 1
    '''if no candidate in all remote sites, then exit''' 
    if sum(breakFlag) == size:
        break
    for rank in range(size):
        '''polling'''
        local_large[rank] = REMOTE[rank].polling(SUP, NORM, size, Transaction[rank])
        '''broadcast local_large sets to all polling sites'''
  
    '''polling site'''
    Global_set = [[]] * size
    Global_set_supp = Global_set
    large_site = {} #large_site[rank] = {transaction:[site1,site2,...],...}
    for rank in range(size):
        '''receive local_large sets from all remote sites'''
        local_pruning = []  #Local_Pruning[rank] = [transaction1,transaction2,...]
        large_site[rank] = {} #large_site[rank] = {transaction:[site1,site2,...],...}
        for remoteJ in range(size):
            '''receive LL(i,j)(k) to rank j site'''
            temp = local_large[remoteJ][rank]
            for tranAndSup in temp:
                '''update large_site'''
                [local_pruning, large_site[rank]] = polling.insert(tranAndSup, remoteJ, local_pruning, large_site[rank])
            '''gather support values, in Transacion'''
            '''generate global large sets'''
        result = polling.gen_global_set(Transaction, size, NORM, SUP, local_pruning)
	Global_set[rank] = result[0]
	print Global_set[rank]
	Global_set_supp[rank] = result[1] 
	print Global_set_supp[rank]
    
    '''homesite'''
    '''get global sets from all polling sites, in Global_set'''
    '''generate large set'''
    large = {}
    large_supp = {}
    large, large_supp = home.compute_large(Global_set, Global_set_supp, large_site, size, large, large_supp)
    if len(large) == 0:  # no transactions added (global_large)
        print 'BREAK IN STEP ' + str(k) + ' global_set no update'
        break

    '''divide large into global_large'''
    Global_large = home.divide_global_set(size, large)
    '''send global_large to remote sites'''
    del temp
    k += 1
    '''receive global_large from home site, start next loop'''
    for rank in range(size):
        if breakFlag[rank] == 0:
            REMOTE[rank].global_large = Global_large[rank]
        else:
            print breakFlag[rank]
    
'''loop terminate'''
large = []
for rank in range(size):
    large.extend(REMOTE[rank].global_large)
large = list(set(large))
print large
saveData(large, 'data_keyword_class')
