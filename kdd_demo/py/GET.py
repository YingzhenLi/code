# -*- coding: utf-8 -*-
"""
Created on Wed May 16 05:04:08 2012
get methods
@author: YINGZHENLI KDD@SYSU
"""

'''data_user_action'''
# user_action = {user:{target:[at, retweet, comment],...},...}    
def get_action(user, target, user_action):
    if user not in user_action:
        return None
    if target not in user_action[user]:
        return None
    return user_action[user][target]
    
'''data_user_sns'''
# user_sns = {user:[followee,...],...}
def get_followee(user, user_sns):
    if user not in user_sns:
        return None
    return user_sns[user]
def is_followee(user, followee, user_sns):
    return followee in user_sns[user]

'''data_itemCate, pass test?'''
# itemCate = {item:cate,...}
def get_item_cate(item, itemCate):
    if item not in itemCate:
        return None
    return itemCate[item]
'''data_cateItem, pass test?'''
# cateItem = {cate:[item,...],...}
def get_rank(item, cate, cateItem):
    if item not in cateItem[cate]:
        return -1
    k = 0
    while item != cateItem[cate][k]:
        k += 1
    return k+1
    
'''data_rec_log_train'''
# rec_log_train = {user:{item:sign,...},...}
def get_log_train(user, rec_log_train):
    if user not in rec_log_train:
        return None
    data = {}
    for item in rec_log_train[user]:
        if rec_log_train[user][item] < 0:
            data[item] = -1
        else:
            data[item] = 1
    return data
# get all training set
def get_train(rec_log_train):
    data = {}
    # data = {user:{item:result(1/-1),...},...}
    for user in rec_log_train:
        data[user] = get_log_train(user, rec_log_train)
    return data
    
'''data_itemKey'''
# itemKey = {item:{key:weight}}
def get_item_key(item, itemKey):
    if item not in itemKey:
        return None
    return itemKey[item]
def get_key_item(key, itemKey):
    data = []
    for item in itemKey:
        if key in itemKey[item]:
            data.append(item)
    return data

'''data_user_key_word'''
# user_key_word = {user:{key:weight}}
def get_user_key(user, user_key_word):
    if user not in user_key_word:
        return None
    return user_key_word[user]
def get_key_user(key, user_key_word):
    data = []
    for user in user_key_word:
        if key in user_key_word[user]:
            data.append(user)
    return data
    
'''data_keyword_class'''
# keyword_class = [[key,...],...]
def get_class(key, keyword_class):
    classSet = []
    for CLASS in range(len(keyword_class)):
        if key in keyword_class[CLASS]:
            classSet.append(CLASS)
    if len(classSet) != 0:
	return classSet
    return -1
# find classes of a keyword group
def get_classes(keys, keyword_class):
    data = set()
    for key in keys:
        data.update(get_class(key, keyword_class))
    return list(data)
    
'''data_user_accepted_cate'''
# user_accepted_cate = {user:[cate,...],...}
def get_accepted(user, user_accepted_cate):
    if user not in user_accepted_cate:
        return None
    return user_accepted_cate[user]
def is_accepted(user, item, user_accepted_item):
    return item in user_accepted_item[user]
    
'''data_class_cate'''
# class_cate = {class:{cate:weight,...},...}

'''data_reg_log_test'''
# reg_log_test = {user:[item,...],...}
def get_log_test(user, rec_log_test):
    if user not in rec_log_test:
        return None
    return rec_log_test[user]
