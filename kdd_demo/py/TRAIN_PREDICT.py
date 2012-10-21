#! usr/bin/python

# YINGZHENLI KDD@SYSU

'''
TRAINING
optional: delete the # (also delete corresponding # in GRADE.py)and ativate the training of other parameters
'''
import GET
import GRADE
import cPickle
import time
import IMPORT
import random
from mpi4py import MPI
import math
import os
import sys

comm = MPI.COMM_WORLD
size = comm.Get_size()   
rank = comm.Get_rank()
path = os.getcwd() [:-3]+ '/data'
sys.path.append(path)
'''TRAINING PARAMETERS'''
k = 1
performance = 0
weight = [0, 0]
weight[0] = random.random(); weight[1] = random.random()
oldweight = weight
learning = 0.9
fweight = [[1.0/3, 1.0/3], [1.0/3, 1.0/3]]
candidate = {}
recommend = {}
dataAP = [{}, {}, {}]
averageWeight = [0, 0]
userNum = [0, 0, 0]
fw = open('data_output_evaluation', 'w')
# here we fix the omegas so no need for learning them
#fweight[0][0] = random.random(); fweight[0][1] = random.random()
#fweight[1][0] = random.random(); fweight[1][1] = random.random()

'''DATASETS'''
fileNames = ['itemCate', 'user_accepted_cate', 'user_accepted_item', 'user_action', 'user_sns', 
            'cateItem', 'user_key_word', 'keyword_class',
            'rec_log_train','rec_log_test', 'class_cate', 'user_class', 'itemKey'] 
DATA = IMPORT.IMPORT(fileNames)
'''DATA REC_LOG_TRAIN DISTRIBUTION'''
NORM = int(math.ceil(float(len(DATA['rec_log_train']))/size))
if rank < size - 1:
    DATA['rec_log_train'] = dict(DATA['rec_log_train'].items()[rank*NORM:(rank+1)*NORM])
if rank == size - 1:
    DATA['rec_log_train'] = dict(DATA['rec_log_train'].items()[rank*NORM:])

'''GET THE LIST OF RECOMMENDATION IN TRAINING SET'''
train = GET.get_train(DATA['rec_log_train'])
    
'''TRAINING PROCESS'''
while k == 1: 
    if rank == 0:
	print 'USER ' + str(k) + ', larst performance = ' + str(performance)
	print 'USER ' + str(k) + ', larst weight = ' + str(weight[0]) + ' ' + str(weight[1])
    performance = 0
    weight = [0, 0]
    weight[0] = random.random(); weight[1] = random.random()
    oldweight = weight
    Gweight = [0, 0]
    #Gfweight = [[0, 0], [0, 0]]
    performance = 0
    '''TRAINING PROCESS IN COMPUTING SITES'''
    for userID in train.keys():        
        '''GET USER CLASS AND SEND USERS TO COMPUTING SITES(VIRTUAL)'''
        userClass = DATA['user_class'][userID] # DATA['user_class']  # 0 or 1: train; -1: fake, pass
        '''GRADING'''
	Round = 1
	while Round > 0:        
	    for itemID in train[userID].keys():
	    	# function test OK other than DANTE'S test
            	INFO = GRADE.GRADE(userID, itemID, weight[userClass], fweight[userClass], DATA, userClass)
            	'''GRADIENT: IF partial != [0, 0], THEN PARTIAL FWEIGHTS'''
            	sgn = lambda x: 1 if x >0 else -1 if x < 0 else 0
            	sign = sgn(train[userID][itemID])
            	performance += (INFO['GRADE'] - sign) ** 2
            	Gweight[userClass] += (INFO['GRADE'] - sign) * INFO['WEIGHT'] * INFO['h - s']
            	#Gfweight[userClass][0] += (INFO['GRADE'] - sign) * (INFO['GRADE'] + 1)/2 * INFO['partial'][0]
            	#Gfweight[userClass][1] += (INFO['GRADE'] - sign) * (INFO['GRADE'] + 1)/2 * INFO['partial'][1]

    	    '''GATHER GRADIENTS FROM ALL SITES(VIRTUAL)'''    
    	    temp = comm.gather(Gweight, 0)
    	    if rank == 0:
	    	#print 'REDUCE SITE: gather Gweight data from COMPUTING SITES'
            	GWEIGHT = [0, 0]    
            	for gw in temp:
                    GWEIGHT[0] += gw[0]
                    GWEIGHT[1] += gw[1]
            #temp = comm.gather(Gfweight, 0)
            #if rank == 0:
	    	#print 'REDUCE SITE: gather Gfweight data from COMPUTING SITES'
            	#GFWEIGHT = [[0, 0], [0, 0]]
            	#for gfw in temp:
                    #GFWEIGHT[0][0] += gfw[0][0]
                    #GFWEIGHT[0][1] += gfw[0][1]
                    #GFWEIGHT[1][0] += gfw[1][0]
                    #GFWEIGHT[1][1] += gfw[1][1]
            temp = comm.gather(performance, 0)
            if rank == 0:
	    	#print 'REDUCE SITE: gather performance data from COMPUTING SITES'
            	performance = 0
            	for pf in temp:
                    performance += pf
	
            '''LEARNING'''
            if rank == 0:
	    	temp = weight
            	weight[0] = weight[0] + (1 - learning) * GWEIGHT[0] + learning * (weight[0] - oldweight[0])
	    	if weight[0] > 1:
		    weight[0] = random.random() * 0.5 + 0.5
	    	if weight[0] < 0:
		    weight[0] = random.random() * 0.5
            	weight[1] = weight[1] + (1 - learning) * GWEIGHT[1] + learning * (weight[1] - oldweight[1])
	    	if weight[1] > 1:
		    weight[1] = random.random() * 0.5 + 0.5
	    	if weight[1] < 0:
		    weight[1] = random.random() * 0.5
	    	oldweight = temp
            	#fweight[0][0] -= learning * GFWEIGHT[0][0]
            	#fweight[0][1] -= learning * GFWEIGHT[0][1]
            	#fweight[1][0] -= learning * GFWEIGHT[1][0]
            	#fweight[1][1] -= learning * GFWEIGHT[1][1]
    
            '''BROADCAST REVISED WEIGHTS TO COMPUTING SITES(VIRTUAL)'''
            weight = comm.bcast(weight, 0)
            print 'weight = ', weight
            #fweight = comm.bcast(fweight, 0)
	    print 'performance = ', performance
	    if performance < 1:
		break
	    performance = 0
            performance = comm.bcast(performance, 0)
            oldweight = comm.bcast(oldweight, 0)
	    Round -= 1
	
	'''RECORDING WEIGHT'''
	averageWeight[userClass] += weight[userClass]
	userNum[userClass] += 1
	'''PREDICTION'''
    	candidate[userID] = {}
    	for itemID in DATA['rec_log_test'][userID]:
            # get the grading of the recommendation
            temp = GRADE.GRADE(userID, itemID, weight[userClass], fweight[userClass], DATA, userClass)
            if temp['WEIGHT'] == 0:
            	candidate[userID][itemID] = GRADE.get_hot_rank(itemID, DATA['itemCate'], DATA['cateItem']) * 0.5
            else:
            	candidate[userID][itemID] = temp['GRADE']
	
	#'''GATHER GRADING FROM COMPUTING SITES(VIRTUAL)'''        
	#temp = comm.gather(candidate, 0)

	'''GENERATE RECOMMENDATION'''
	#if rank == 0:
    	# union
    	print 'REDUCE SITE: gather candidate from COMPUTING SITES'
    	#for site in range(size):   
        #for userID in temp[site]:
        #if userID not in recommend:
	recommend[userID] = candidate[userID]
	#else:
        #recommend[userID] = dict(recommend[userID].items() + temp[site][userID].items())
        # pick out 3 items according to the grading rank
        # sort the items according to the gradings
	#print recommend[userID]	
        recommend[userID] = sorted(recommend[userID], key = recommend[userID].__getitem__, reverse = True)[0:3]
	print recommend[userID] 

	'''EVALUATION'''
	ap = 0
	flag = 0
	result = recommend[userID]
	string = str(userID) + '\t'
	fw.write(string)
	for i in range(len(result)):
		if result[i] in DATA['rec_log_test'][userID]:
			if DATA['rec_log_test'][userID][result[i]] == 1:
				string = result[i] + ',' + str(1) + '\t'
				fw.write(string)
				flag += 1
				ap += float(flag)/(i + 1)
			else:
				string = result[i] + ',' + str(-1) + '\t'
				fw.write(string)
		else:
			string = result[i] + ',' + str(-1) + '\t'
			fw.write(string)
	if flag != 0:
		string = ap/flag
	else:
		string = 0
	fw.write(str(string) + '\t' + str(weight[userClass]) + '\n')
	dataAP[DATA['user_class'][userID] + 1][userID] = string
    	print 'NEW USER'
    k -= 1

temp = comm.gather(averageWeight, 0)
temp2 = comm.gather(userNum, 0)

'''RECORDING'''
if rank == 0:
    averageAP = [0, 0, 0]
    for i in range(3):
	averageAP[i] = sum(dataAP[i].values())/len(dataAP[i].keys())
    print averageAP
    fw.write(str(averageAP))
    for i in range(1, size):
	averageWeight[0] += temp[i][0]
	averageWeight[1] += temp[i][1]
	userNum[0] += temp2[i][0]
	userNum[1] += temp2[i][1]
	userNum[2] += temp2[i][2]
    fw.write('inacitve user, ' + str(userNum[1]) + 'weight, ' + str(averageWeight[0]/userNum[1]) + '\n')
    fw.write('acitve user, ' + str(userNum[2]) + 'weight, ' + str(averageWeight[1]/userNum[2]) + '\n')
    fw.write('fake user, ' + str(userNum[0]) + '\n')

    '''EXIT'''
    comm.Abort()
