# -*- coding: utf-8 -*-
"""
Created on Wed May 16 05:58:33 2012
phase 3
@author: YINGZHENLI KDD@SYSU
"""
import GET
import math
import PHASE2
'''
STEP 1: GRADING
optional: using gradient methods to train the other parameters
'''

def GRADE(userID, itemID, weight, fweight, DATA, userClass):
    # weight = w1
    # fweight = [fw1,fw2]
    #partial = [0,0]
    W = 0
    INFO = {}
    INFO['GRADE'] = 0
    INFO['WEIGHT'] = 0
    INFO['h - s'] = 0
    #INFO['partial'] = partial
    
    '''SPECIFY EVERY DATASET'''
    # itemCate = DATA['itemCate']
    # user_accepted_cate = DATA['user_accepted_cate']
    # user_action = DATA['user_action']
    # user_sns = DATA['user_sns']
    # cateItem = DATA['cateItem']
    # user_key_word = DATA['user_key_word']
    # keyword_class = DATA['keyword_class']
    # class_cate = {class:{cate:weight,...},...}

    '''GET CATEGORY OF THE ITEM'''
    # function test OK
    itemCate = GET.get_item_cate(itemID, DATA['itemCate'])
    if itemCate == None:
        print 'None in ITEMCATE at PHASE1' + ', userID: ' + userID + ', itemID: ' + itemID
    
    '''MATCH THE USER CATEGORY IN PHASE 1 AND RETURN WEIGHT 1'''
    # get userCate in phase 1
    # userCate = [cate,...], funtion test OK
    userCate = GET.get_accepted(userID, DATA['user_accepted_cate'])
    if userCate == None:
        print 'None in USERCATE at PHASE1' + ', userID: ' + userID + ', itemID: ' + itemID
    else:
        if itemCate in userCate:
            W = 1
    
    '''NO MATCH: MATCH THE USER CATEGORY IN PHASE 2 AND RETURN WEIGHT'''
    if W == 0:
	print 'START PHASE 2,', 'user', userID, 'item', itemID
        # user_key_class = {class:{key:weight,...},...}, function test DANTE
        user_key_class = PHASE2.get_user_key_class(userID, DATA['user_key_word'], DATA['keyword_class'])
        if user_key_class == None:
            print 'None in USER_KEY_CLASS in PHASE2' + ', userID: ' + userID + ', itemID: ' + itemID
        else:
            # userCate = {cate:weight,...}, function test DANTE
            userCate = PHASE2.get_cate_weight(user_key_class, DATA['class_cate'])
	    #print userID, user_key_class
	    #print userCate
            if userCate == None:
                print 'None in USERCATE at PHASE2' + ', userID: ' + userID + ', itemID: ' + itemID
            elif itemCate in userCate and W == 0: 
                W = userCate[itemCate]
        
    '''NO MATCH AGAIN: MATCH THE USER CATEGORY IN PHASE 3 AND RETURN WEIGHT'''
    if W == 0:
	print 'START PHASE 3,', 'user', userID, 'item', itemID
        # get followee
        # Neighbor = [followee,...], function test OK
        Neighbor = GET.get_followee(userID, DATA['user_sns'])
        if Neighbor == None:
            print 'None in NEIGHBOR at PHASE3' + ', userID: ' + userID + ', itemID: ' + itemID
            return INFO
        # get followee's category in phase 1(WRONG) and 2
        NeighborNum = len(Neighbor)     # average
        for followeeID in Neighbor:
            # get followeeCate in phase1(WRONG) and phase 2, function test OK
            followeeCate1 = GET.get_accepted(followeeID, DATA['user_accepted_cate'])
            # user_key_class = {class:{key:weight,...},...}, function test DANTE
            user_key_class = PHASE2.get_user_key_class(userID, DATA['user_key_word'], DATA['keyword_class'])
            # userCate = {cate:weight,...}, function test DANTE
            followeeCate2 = PHASE2.get_cate_weight(user_key_class, DATA['class_cate'])
            # weight return with caculation of familiarity, using fweight
            if followeeCate2 == None and followeeCate1 == None:
                print 'None in FOLLOWEECATE at PHASE3' + ', userID: ' + userID + ', itemID: ' + itemID
                return INFO
            if followeeCate1 != None and itemCate in followeeCate1:
                wPhase2 = 1   # tbd: get weight in phase 1 and 2
            elif followeeCate2 != None and itemCate in followeeCate2:
                wPhase2 = followeeCate2[itemCate]
            else:
                print 'None ITEMCATE in FOLLOWEECATE at PHASE3' + ', userID: ' + userID + ', itemID: ' + itemID
                return INFO
            # function test OK
            temp = GET.get_action(userID, followeeID, DATA['user_action'])
            if temp == None:
                W = wPhase2       # tbd
            else: 
                at, retweet, comment = temp
                W += (fweight[0] * sigmoid(at) + fweight[1] * sigmoid(retweet) + fweight[2] * sigmoid(comment)) * wPhase2 / NeighborNum
                #partial[0] += (sigmoid(at) - sigmoid(comment)) * wPhase2 / NeighborNum
                #partial[1] += (sigmoid(retweet) - sigmoid(comment)) * wPhase2 / NeighborNum
        
    '''TO BE SPECIFIED: PHASE2 AND PHASE3 EXCLUSIVE?'''
    if itemCate in userCate and W != 1:
        if userCate[itemCate] >= W:
            W = userCate[itemCate]
            #partial = [0,0]

    '''IF MATCH: RETURN THE HOT GRADE AND SIMILARITY'''
    # get hot rank
    # return the hot rank of item in category, function test OK
    hot = get_hot_rank(itemID, DATA['itemCate'], DATA['cateItem'])
    # get similarity
    similarity = get_similarity(userID, itemID, DATA) # function test OK

    '''GRADING: NO MATCH RETURN -1, ELSE GRADE = 2*W(w1*h+(1-w1)*s)-1'''
    if userClass >= 0:
    	INFO['GRADE'] = 2 * zoom(W, 100) * (weight * hot + (1 - weight) * similarity) - 1  # no time info
    	INFO['WEIGHT'] = zoom(W, 100)
    	INFO['h - s'] = hot - similarity
    	#print 'user', userID, 'item', itemID, 'hot', hot, 'similarity', similarity, 'WEIGHT', INFO['WEIGHT'], 'W', W, 'GRADE', INFO['GRADE']
    	#INFO['partial'] = partial
    else:
	INFO['GRADE'] = (1 + zoom(W, 100)) * hot - 1
    return INFO
    
def sigmoid(x):	# function test OK
    return 2/(1 + math.exp(-x)) - 1

def zoom(x, k):	# zoom function	
    x = float(x)
    k = float(k)
    b = (1 + math.exp(-k)) / (1 - math.exp(-k))
    a = 2 * b
    return a/(1 + math.exp(-k * x)) - b

def get_similarity(userID, itemID, DATA): # compute sim(u_j, i_k)
    userClass = PHASE2.get_user_key_class(userID, DATA['user_key_word'], DATA['keyword_class'])
    itemClass = PHASE2.get_user_key_class(itemID, DATA['itemKey'], DATA['keyword_class'])
    if userClass == None:
	print 'no class in userClass', userID
	return 1
    if itemClass == None:
	print 'no class in itemClass', itemID
	return 1    
    n = max(len(userClass.keys()), len(itemClass.keys())) +1
    dist = 0
    for CLASS in range(n):
        if CLASS in userClass and CLASS in itemClass:
            dist += (userClass[CLASS] - itemClass[CLASS])**2
        elif CLASS in userClass:
            dist += userClass[CLASS]**2
        elif CLASS in itemClass:
            dist += itemClass[CLASS]**2
    return 2/(1 + math.exp(dist**0.5))

'''GET RANK OF CANDIDATE ITEMS'''
# get_hot_rank(i_k, h_k), GET_HOT_RANK(i_k) already computed when importing data item.txt and rec_log_train.txt
def get_hot_rank(item, itemCate, cateItem):
    if item not in itemCate:
        return -1
    else:
        cate = itemCate[item]
    if item not in cateItem[cate]:
        return -1
    k = 0
    while item != cateItem[cate][k]:
        k += 1
    grade = 2/(1 + math.exp(k))
    return grade
