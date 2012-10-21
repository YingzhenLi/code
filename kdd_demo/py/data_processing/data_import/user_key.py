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

limit = 10000    
'''import user keywords data'''
f = open('user_key_word.txt', 'r')
data = {}
count = 0
# data = {user:{key1:w1,...}, ...}
for line in f:
    userIDkeyW = line.split('\t')
    # extract selected users
    if userIDkeyW[0] not in selected:
	count += 1
	continue
    # keyW = [key1:w1;key2:w2;...]
    userID = userIDkeyW[0]
    keyW = userIDkeyW[1][:-1]
    keyW = keyW.split(';')
    data[userID] = {}
    count +=1
    if count % 10000 == 0:
	print count
    for keyw in keyW:
        key, weight = keyw.split(':')
        data[userID][key] = float(weight)
f.close()
f = open('data_user_key_word', 'w')
data = cPickle.dumps(data)
f.write(data)
del data, userIDkeyW, userID, keyW, key, weight 
f.close()


