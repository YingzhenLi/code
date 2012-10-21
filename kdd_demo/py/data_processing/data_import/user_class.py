#!/usr/bin/python
#coding:utf-8
	
# YINGZHENLI	KDD@SYSU
# DATA IMPORT USING MODULE CPICKLE
import cPickle
'''
WARNING: 
IMPORT TRAINING DATA WITH ITEM DATA TOGATHER,SINCE WE USE 
TRAINING DATA TO SORT THE ITEMS ACCODING TO THE HOT RANK
'''

def crack_save(data, fileName, limit):
    count = 0
    fileBuf = 0
    ITER = data.iteritems()
    save = {}
    while 1:
        try:
            temp = ITER.next()
            save[temp[0]] = temp[1]
            count += 1
            if count == limit:
                f = open(fileName + str(fileBuf), 'w')
                f.write(cPickle.dumps(save))
                count = 0
                fileBuf += 1
                save = {}
        except StopIteration:
            if count != 0:
                f = open(fileName + str(fileBuf), 'w')
                f.write(cPickle.dumps(save))
            break

limit = 10000
f = open('selected_user', 'r')
selected = cPickle.loads(''.join(f.readlines()))
f.close()

# read in:
f = open('data_user_action', 'r')
dataAction = cPickle.loads(''.join(f.readlines()))
f.close()

f = open('data_user_profile', 'r')
dataProfile = cPickle.loads(''.join(f.readlines()))

'''user class'''
data = {}
# data = {user:class,...}
for user in dataProfile:
    if user not in dataAction:	# no interactions
	data[user] = -1
	continue
    action = 0
    for target in dataAction[user]:
	action += sum(dataAction[user][target])
	if action >= 20:	# min_action = 20
	    break
    grade = dataProfile[user] * (action >= 20)
    if grade == 0:
	data[user] = -1
    elif grade <= 100:	# min_activeness = 100
	data[user] = 0
    else:
	data[user] = 1
f = open('data_user_class', 'w')
data = cPickle.dumps(data)
f.write(data)
del data, dataAction, dataProfile
f.close()
