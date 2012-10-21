#/usr/bin/python
#-*-coding:utf-8-*-

from user_key_class import *

def get_user_class(userID, keyClassDict):
    """keyClassDict indicates the user_key_class:{user:{class1:w1,class2:2,...},...}"""
    if userID not in keyClassDict:
        return None
    return keyClassDict[userID]


def get_all_cate(keyClassDict, cateClassDict):
    """get the category of user from user_key_class and class_cate"""
    #keyClassDict = {class:weight,...}
    #keyClasses = get_user_class(userID, keyClassDict)
    #if keyClasses != None:
    #    keyClasses = keyClasses.keys()
    #else:
    #    return {}
    resultSet = set()
    for keyClass in keyClassDict:
	if keyClass in cateClassDict:
	    resultSet = resultSet.union(set(cateClassDict[keyClass].keys()))
    return resultSet

def get_cate_weight(keyClassDict, cateClassDict):
    """compute the weight of category to userreturn an dict object ResultDict, and ResultDict[catei]=weight_for_cate_i_to_user"""
    #keyClassDict = {class:weight,...}
    #cateClassDict: {class1:{cate1:w1,...},...}
    
    #keyClassesWithWeight = get_user_class(userID, keyClassDict)
    #if keyClassesWithWeight != None:
    #    keyClasses = keyClassesWithWeight.keys()
    #else:
    #    return {}
    cateSet = get_all_cate(keyClassDict, cateClassDict)
    resultDict = dict()
    for cate in cateSet:
	resultDict[cate] = 0
        for keyClass in keyClassDict:
            keyClassWeight = keyClassDict[keyClass]
	    cateWeight = 0
	    if keyClass in cateClassDict:
	    	if cate in cateClassDict[keyClass]:
                    cateWeight = cateClassDict[keyClass][cate]
            resultDict[cate] += keyClassWeight * cateWeight
    return resultDict
            
