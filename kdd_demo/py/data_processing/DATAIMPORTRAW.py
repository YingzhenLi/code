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

'''import user action data'''
f = open('user_action.txt', 'r')
data = {}
# data = {user1:{targetUser1: [at, retweet, comment],...},...}
for line in f:
    action = line.split('\t')
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
# f = open('data_user_action', 'r')
# data = cPickle.loads(''.join(f.readlines()))

'''import user profile data'''
f = open('user_profile.txt', 'r')
data = {}
# data = {user:[birthYear, gender, tweet, tag], ...}
for line in f:
    profile = line.split('\t')
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
f.close()

'''user class'''
data = {}
# data = {user:class,...}
for user in dataProfile:
    action = 0
    for target in dataAction[user]:
	action += sum(dataAction[user][action])
    grade = dataProfile[user] * (action >= 20)
    if grade == 0:
	data[user] = -1
    elif grade <= 100:
	data[user] = 0
    else:
	data[user] = 1
f = open('data_user_class', 'w')
data = cPickle.dumps(data)
f.write(data)
del data, dataAction, dataProfile
f.close()

'''import user SNS data'''
f = open('user_sns.txt', 'r')
data = {}
# data = {user:[followee1, followee2,...], ...}
for line in f:
    sns = line.split('\t')
    # sns = [follower, followee]
    if sns[0] not in data:
        data[sns[0]] = [sns[1][:-1]]
    else:
        data[sns[0]].append(sns[1][:-1])
f.close()
crack_save(data, 'data_user_sns', limit)
del data, sns

'''import training data'''
'''
WARNING: 
IMPORT TRAINING DATA WITH ITEM DATA TOGATHER,SINCE I USE 
TRAINING DATA TO SORT THE ITEMS ACCODING TO THE HOT RANK
'''
f = open('rec_log_train.txt', 'r')
data1 = {}
data2 = {}
# data1 = {user:{item: sign}, ...}
# sign: if accept: 1
#       else: -1* rejectTimes
# data2 = {item:[acceptUser1,...],...}
for line in f:
    train = line.split('\t')
    train[2] = float(train[2])
    # train = [user, item, result, timestamp]
    if train[0] not in data1:
        data1[train[0]] = {train[1]: train[2]}
    else:
        if train[1] not in data1[train[0]]:
            data1[train[0]][train[1]] = train[2]
        elif data1[train[0]][train[1]] < 0:
            if train[2] == 1:
                data1[train[0]][train[1]] = 1
                if train[0] not in data2:
                    data2[train[0]] = [train[1]]
                else:
                    data2[train[0]].append(train[1])
            else:
                data1[train[0]][train[1]] -= 1

# for saving storage, read in by segmentation
rec_log_train = data1
crack_save(data1, 'data_rec_log_train', limit)
crack_save(data2, 'data_user_accepted_item', limit)
del data1, data2
f.close()

'''import item data'''
'''
WARNING: 
IMPORT TRAINING DATA WITH ITEM DATA TOGATHER,SINCE I USE 
TRAINING DATA TO SORT THE ITEMS ACCODING TO THE HOT RANK
'''
# cateItem = {cate:[item,...],...}, rec_log_train = {user:{item:sign,...},...}
def sort_item(cateItem, rec_log_train):
    rank = {}
    for USER_LOG in rec_log_train.keys():
        for ITEM in rec_log_train[USER_LOG].keys():
            if ITEM not in rank:
                rank[ITEM] = rec_log_train[USER_LOG][ITEM]
            else:
                rank[ITEM] += rec_log_train[USER_LOG][ITEM] > 0    # omit the failings
    # sort by hot rank totally
    rank = sorted(rank, key = rank.__getitem__, reverse = True) 
    # sort by hot rank in a specific cate
    for CATE in cateItem:
        temp = []
        for ITEM in rank:
            if ITEM in CATE:
                temp.append(ITEM)
        cateItem[CATE] = temp
    return cateItem

f = open('item.txt', 'r')
data1 = {}
data2 = {}
data3 = {}
# data1 = {item:category ...}
# data2 = {category: [item1, item2,...],...}
# data3 = {item: {key1: weight1,...},...}
for line in f:
    item = line.split('\t')
    # item = [item, category, keys]
    if item[0] not in data1:
        data1[item[0]] = item[1]
        keys = item[2].split(';')
        data3[item[0]] = {}
        for key in keys:
            if key not in data3[item[0]]:
                data3[item[0]][key] = 1.0/len(keys)
            else:
                data3[item[0]][key] += 1.0/len(keys)
    if item[1] not in data2:
        data2[item[1]] = [item[0]]
    else:
        data2[item[1]].append(item[0])
f.close()
data2 = sort_item(data2, rec_log_train)
f1 = open('data_itemCate', 'w')
f2 = open('data_cateItem', 'w')
f3 = open('data_itemKey', 'w')
data1 = cPickle.dumps(data1)
data2 = cPickle.dumps(data2)
data3 = cPickle.dumps(data3)
f1.write(data1)
f2.write(data2)
f3.write(data3)
del data1, data2, data3, item
f1.close()
f2.close()
f3.close()
    
'''import user keywords data'''
f = open('user_key_word.txt', 'r')
data = {}
# data = {user:{key1:w1,...}, ...}
for line in f:
    userIDkeyW = line.split('\t')
    # keyW = [key1:w1;key2:w2;...]
    userID = userIDkeyW[0]
    keyW = userIDkeyW[1][:-1]
    keyW = keyW.split(';')
    data[userID] = {}
    for keyw in keyW:
        key, weight = keyw.split(':')
        data[userID][key] = float(weight)
f.close()
f = open('data_user_key_word', 'w')
data = cPickle.dumps(data)
f.write(data)
del data, userIDkeyW, userID, keyW, key, weight 
f.close()

'''import rec_log_test'''
f = open('rec_log_test.txt', 'r')
data = {}
# data = {user:[item,...], ...}
for line in f:
    test = line.split('\t')
    # test = [user, item, result, timestamp]
    if test[0] not in data:
        data[test[0]] = []
    else:
        if test[1] not in data[test[0]]:
            data[test[0]].append(test[1])
f.close()
data = crack_save(data, 'data_rec_log_test', limit)
del data, test

'''data_item_rank'''
f = open('data_rec_log_train', 'r')
# rank = [item,...]
rec_log_train = cPickle.loads(''.join(f.readlines()))   
f.close()
rank = {}
for USER_LOG in rec_log_train.keys():
    for ITEM in rec_log_train[USER_LOG]:
        if ITEM not in rank:
            rank[ITEM] = rec_log_train[USER_LOG][ITEM]
        else:
            rank[ITEM] += rec_log_train[USER_LOG][ITEM] > 0    # omit the failings？
    # sort by hot rank totally
rank = sorted(rank, key = rank.__getitem__, reverse = True)
f = open('data_item_rank', 'w')
f.write(cPickle.dumps(rank))
f.close()
del rank

'''result output'''
f = open('sub_small_header.csv', 'r')
head = f.readline()
fw = open('data_output_result', 'w')
for line in f:
    user = line.split(',')[0]
    fw.write(user+'\n')
f.close()
fw.close()
