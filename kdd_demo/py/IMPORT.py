# -*- coding: utf-8 -*-
"""
Created on Thu May 17 06:54:45 2012
DATA IMPORT FROM FILE WITH CPICKLE
@author: YINGZHENLI KDD@SYSU
"""

import cPickle
import os
import re

ALL = ['item_rank', 'rec_log_train', 'rec_log_test', 'user_accepted_item', 
       'itemCate', 'user_sns', 'user_action', 'cateItem', 'keyword_class', 
       'user_key_word']

def IMPORT(fileNames = ALL):
    DATA = {}
    path = os.getcwd() [:-3]+ '/data/'
    for fileName in fileNames:
        #if fileName in ['user_sns', 'rec_log_train', 'rec_log_test']:
        #    FILE = os.listdir(os.getcwd())
        #    for name in FILE:
        #        if re.match('data_' + fileName, name):
        #            f = open(name, 'r')
        #            temp = cPickle.loads(''.join(f.readlines()))
        #            DATA = dict(DATA.items() + temp.items())
        #            f.close()
        #else:
        f = open(path + 'data_' + fileName, 'r')
        # item_rank = [item,...] 
        DATA[fileName] = cPickle.loads(''.join(f.readlines()))
        if DATA[fileName] != None:
            print fileName + ' successful imported\n'
    f.close()
    if all(x in DATA.keys() for x in fileNames):
        print 'all data successfully imported'
    return DATA
