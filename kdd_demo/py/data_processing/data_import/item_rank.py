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
