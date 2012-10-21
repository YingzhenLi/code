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
selected = cPickle.loads(''.join(f.readlines()))
f.close()

'''import training data'''
'''
WARNING: 
IMPORT TRAINING DATA WITH ITEM DATA TOGATHER,SINCE I USE 
TRAINING DATA TO SORT THE ITEMS ACCODING TO THE HOT RANK
'''
'''f = open('rec_log_train.txt', 'r')
data1 = {}
data2 = {}
count = 0
print 'open the file'
# data1 = {user:{item: sign}, ...}
# sign: if accept: 1
#       else: -1* rejectTimes
# data2 = {item:[acceptUser1,...],...}
for line in f:
    train = line.split('\t')
    # extract selected users
    count += 1
    if count % 10000 == 0:
	print count
    if train[0] not in selected:
	continue
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
f.close()'''

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
                rank[ITEM] += (rec_log_train[USER_LOG][ITEM] > 0)    # omit the failings
    # sort by hot rank totally
    rank = sorted(rank, key = rank.__getitem__, reverse = True)
    # sort by hot rank in a specific cate
    for CATE in cateItem:
        temp = []
        for ITEM in rank:
            if ITEM in cateItem[CATE]:
                temp.append(ITEM)
        cateItem[CATE] = temp
	print cateItem[CATE]
    return cateItem

f = open('data_rec_log_train', 'r')
rec_log_train = cPickle.loads(''.join(f.readlines()))
f.close()

f = open('item.txt', 'r')
data1 = {}
data2 = {}
data3 = {}
count = 0
print 'open the file'
# data1 = {item:category ...}
# data2 = {category: [item1, item2,...],...}
# data3 = {item: {key1: weight1,...},...}
for line in f:
    item = line.split('\t')
    count += 1
    if count % 10000 == 0:
	print count
    # item = [item, category, keys]
    if item[0] not in data1:
        data1[item[0]] = item[1]
        keys = item[2].split(';')
        data3[item[0]] = {}
        for key in keys:
	    if key[-1] == '\n':
		key = key[:-1]
            if key not in data3[item[0]]:
                data3[item[0]][key] = 1.0/len(keys)
            else:
                data3[item[0]][key] += 1.0/len(keys)
    if item[1] not in data2:
        data2[item[1]] = [item[0]]
    else:
        data2[item[1]].append(item[0])
f.close()
print data1[item[0]], data2[item[1]], data3[item[0]]
data2 = sort_item(data2, rec_log_train)
f1 = open('data_itemCate', 'w')
f2 = open('data_cateItem', 'w')
f3 = open('data_itemKey', 'w')
print data1[item[0]], data2[item[1]], data3[item[0]]
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
   
