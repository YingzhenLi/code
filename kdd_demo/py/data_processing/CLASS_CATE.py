# -*- coding: utf-8 -*-
"""
Created on Wed May 16 04:36:53 2012
class_cate
@author: YINGZHENLI KDD@SYSU
"""

import cPickle
import GET
import IMPORT

'''data initialization'''
DATA = IMPORT.IMPORT(['itemKey', 'itemCate', 'keyword_class'])
class_cate = {} # {class:{cate:weight,...},...}

'''form class_cate'''
item_num = len(DATA['itemKey'].keys())  # number of the items
flag = {}
for item in DATA['itemKey'].keys():
    for key in DATA['itemKey'][item]:
        '''get class No. of the key'''
        CLASSSET = GET.get_class(key, DATA['keyword_class'])   # tbd: keyword_class
        if CLASSSET == -1:
            '''no match: go to the next item'''
            continue
        '''get the categories of the item'''
        CATE = DATA['itemCate'][item]
        '''compute the AVERAGE weight of the CATE(divided by item_num)'''
	for CLASS in CLASSSET:
	    if CLASS not in class_cate:
                class_cate[CLASS] = {CATE: DATA['itemKey'][item][key]}
		flag[CLASS] = {CATE: 1}
            elif CATE not in class_cate[CLASS]:
                class_cate[CLASS][CATE] = DATA['itemKey'][item][key]
		flag[CLASS][CATE] = 1
            else:
                class_cate[CLASS][CATE] += DATA['itemKey'][item][key]
		flag[CLASS][CATE] += 1
	
for CLASS in class_cate.keys():
    for CATE in class_cate[CLASS]:
	class_cate[CLASS][CATE] /= flag[CLASS][CATE]
            
'''save class_cate using cPickle'''
f = open('data_class_cate', 'w')
f.write(cPickle.dumps(class_cate))
f.close()
