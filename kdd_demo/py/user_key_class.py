#!/usr/bin/python
#-*-coding:utf-8-*-
# author: Ye Zhang KDD@SYSU

def get_user_key(userID, dictData):
    """find corresponding keyword:weight in dictData,
    dictData = 'data_user_key_word'"""
    if userID in dictData:
        return dictData[userID]
    else:
        return None

def get_class(key, listData):
    """find the No. of keys in listData = 'data_keyword_class'"""
    N = len(listData)
    for ii in range(0, N-1):
        if key in listData[ii]:
            return ii
    else:
        return None  # key's not in listData

def get_user_key_class(userID, dictData, listData):
    """find all the keywords of user"""
    keysDict = get_user_key(userID, dictData)
    ResultDict = dict()
    if keysDict is None:
        return None   # no keyword in user_key_word
    TempDict = dict()
    for key in keysDict.keys():
        index = get_class(key, listData) 
        if index is None:
            pass
        else:
            TempDict[key] = index
    
    for index in TempDict.values():
        ResultDict[index] = {} 
        # initial the number of keyword classes and let class = {}
    
    for key in TempDict:
        # dict has no attribute 'add'
        # ResultDict[TempDict[key]].add(key)
        # revise
        ResultDict[TempDict[key]][key] = 0.0

    # get all weights of classID        
    keyDict = get_user_key(userID, dictData)    
    for classID in ResultDict:
        for key in ResultDict[classID]:
            ResultDict[classID][key] += keyDict[key]
	ResultDict[classID] = sum(ResultDict[classID].values())
    return ResultDict 
    '''REVISE: return {class:{key:weight,...},...}'''    
    
    # ResultDict := {classNumber:{key1,key2,...},...}；
    # if return ResultDict.values() := {key1,key2,...}

def get_class_weight(userID, dictData, listData, classID):
    """sum up the weights of keywords in intersect(user's keyword, keyword_class(classID))"""
    keyClasses = get_user_key_class(userID, dictData, listData)
    if keyClasses is None: # no keyword in keyword class classID
        return 0
    if classID not in keyClasses.keys():
        return 0   # no keyword of user in keyword class classID
    keyDict = get_user_key(userID, dictData)
    class_weight = 0.0
    for key in keyClasses[classID]:
        class_weight += keyDict[key]
    return class_weight

