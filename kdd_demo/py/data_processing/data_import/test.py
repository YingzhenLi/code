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

limit = 10000
f = open('selected_user', 'r')
print f.readline()
f.seek(0)
selected = cPickle.loads(''.join(f.readlines()))
f.close()
print selected[0]
    
'''import rec_log_test'''
f = open('rec_log_test.txt', 'r')
data = {}
count = 0
# data = {user:[item,...], ...}
for line in f:
    test = line.split('\t')
    count += 1
    if count % 10000 == 0:
	print count
    if test[0] not in selected:
	continue
    # test = [user, item, result, timestamp]
    if test[0] not in data:
        data[test[0]] = []
    else:
        if test[1] not in data[test[0]]:
            data[test[0]].append(test[1])
f.close()
data = crack_save(data, 'data_rec_log_test', limit)
del data, test


