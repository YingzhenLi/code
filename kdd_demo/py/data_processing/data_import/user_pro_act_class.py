#!/usr/bin/python
#coding:utf-8
	
# YINGZHENLI	KDD@SYSU
# DATA IMPORT USING MODULE CPICKLE
import cPickle
'''
WARNING: 
IMPORT TRAINING DATA WITH ITEM DATA TOGATHER,SINCE I USE 
TRAINING DATA TO SORT THE ITEMS ACCODING TO THE HOT RANK
'''
limit = 10000

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

f = open('selected_user', 'r')
selected = cPickle.loads(''.join(f.readlines()))
f.close()

'''import user action data'''
'''f = open('user_action.txt', 'r')
data = {}
# data = {user1:{targetUser1: [at, retweet, comment],...},...}
for line in f:
    action = line.split('\t')
    # extract selected users
    if action[0] not in selected:
	continue
    action[2] = int(action[2])
    action[3] = int(action[3])
    action[-1] = int(action[-1][:-1])
    # action = [user, targetUser, at, retweet, comment]
    if action[0] not in data:
        data[action[0]] = {action[1]: action[2:]}
    else:
        data[action[0]][action[1]] = action[2:]
f.close()
f = open('data_user_action', 'w')
dataAction = data
data = cPickle.dumps(data)
f.write(data)
del data, action
f.close()
# read in:
f = open('data_user_action', 'r')
data = cPickle.loads(''.join(f.readlines()))

#import user profile data
f = open('user_profile.txt', 'r')
data = {}
# data = {user:[birthYear, gender, tweet, tag], ...}
for line in f:
    profile = line.split('\t')
    # extract selected users
    if profile[0] not in selected:
	continue
    # profile = [user, birthYear, gender, tweet, tag]
    profile[-2] = int(profile[-2])
    profile[-1] = profile[-1][:-1]
    data[profile[0]] = profile[1:]
f.close()
f = open('data_user_profile', 'w')
dataProfile = data
data = cPickle.dumps(data)
f.write(data)
del data, profile
f.close()'''

'''user class'''
data = {}
# data = {user:class,...}

f = open('data_user_action', 'r')
dataAction = cPickle.loads(''.join(f.readlines()))
f.close()

f = open('data_user_profile', 'r')
dataProfile = cPickle.loads(''.join(f.readlines()))
f.close()

for user in dataProfile:
    action = 0
    if user not in dataAction:
	data[user] = -1
	continue
    for target in dataAction[user]:
	print dataAction[user][target]
	action += sum(dataAction[user][target])
    grade = dataProfile[user][2] * int(action >= 5)
    if grade == 0:
	data[user] = -1
    elif grade <= 100:
	data[user] = 0
    else:
	data[user] = 1
    print data[user]
f = open('data_user_class', 'w')
data = cPickle.dumps(data)
f.write(data)
del data, dataAction, dataProfile
f.close()
