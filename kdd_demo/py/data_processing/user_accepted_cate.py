# -*- coding: utf-8 -*-
"""
Created on Wed May 16 05:04:08 2012
get methods
@author: YINGZHENLI KDD@SYSU
"""

import cPickle

f = open('data_itemCate', 'r')
data1 = cPickle.loads(''.join(f.readlines()))
f.close()

f = open('data_user_accepted_item', 'r')
data2 = cPickle.loads(''.join(f.readlines()))
f.close()

data3 = {}
for user in data2.keys():
	data3[user] = []
	for item in data2[user]:
		if data1[item] not in data3[user]:
			data3[user].append(data1[item])

f = open('data_user_accepted_cate', 'w')
f.write(cPickle.dumps(data3))
f.close()
del data1, data2, data3
