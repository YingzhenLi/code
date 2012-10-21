#/usr/bin/python
#-*-coding:utf-8-*-
# author: Ye Zhang KDD@SYSU

def get_keyword_number(userID, user_key_word):
    if user_key_word[userID] is None:
        return 0
    return len(user_key_word[userID].keys())

def get_ave(userIDlist,userData,getDataFun):
    """average"""
    # getDataFun：function name of getting selected data, example: get_keyword_number
    # userData is correspondent to getDataFun
    Num = len(userIDlist)
    return float(sum([getDataFun(userID, userData) for userID in userIDlist]))/Num

def get_square_error(userIDList, userData, getDataFun):
    """variant"""
    mean = get_ave(userIDList, userData, getDataFun)
    return float(sum([(getDataFun(user, userData) - mean) **2 for user in userIDList]))/len(userIDList)

def sta_key_and_user(userIDList, user_key_word):
	"""统计有n个关键词的用户有多少"""
	result = [0 for ii in range(1,150)] # list of keywords）
	for userID in userIDList:
			result[get_keyword_number(userID, user_key_word)] += 1
	return result

def get_followee_number(userID,user_sns):
	if user_sns[userID] is None:
			return 0
	return len(user_sns[userID])

def sta_followee_and_user(userIDList, user_sns):
	"""统计有n个followee的user有几个"""
	result = [0 for ii in range(1,300)] # list of followees
	for userID in userIDList:
			result[get_followee_number(userID, user_sns)] += 1
	return result
	
def get_tweet_number(userID, user_profile):
	if user_profile[userID] is None:
			return 0
	return user_profile[userID][2]

def sta_tweet_and_user2(userIDList, user_profile):
	"""统计发了n条tweet的人有多少个"""
	result = dict()
	for userID in userIDList:
			result[get_tweet_number(userID, user_profile)] = 0 
	for userID in userIDList:
			result[get_tweet_number(userID, user_profile)] += 1

	return result

def sta_tweet_and_user(userIDList, user_profile):
	result = dict()
	temp = dict()
	for userID in userIDList:
			temp[userID] = get_tweet_number(userID, user_profile)
	Num_of_tweet = temp.values()
	M = max(Num_of_tweet)
	m = min(Num_of_tweet)
	
	for ii in range(m,M+1):
			#result[ii] = len([N for N in Num_of_tweet if N == ii])
			result[ii] = len(filter(lambda n:n==ii, Num_of_tweet))
	#print M,m
	return result

def percentage(results):	# not used in our test
	total_Number = float(sum(results.values()))
	new_results = dict()
	for key in results:
			new_results[key] = results[key]/total_Number
	return new_results

def DivideLine(results, percent):	# not used in our test
	m = min(results.keys())
	M = max(results.keys())
	per = results[m]
	ii = m
	while ii <= M and per <= percent:
			ii += 1
			per += results[ii]

	return ii - 1

def Divide_user(userID, user_profile, results, percent):
	"""user_classification, not used in our test"""
	num_of_tweet = get_tweet_number(userID, user_profile)
	if num_of_tweet == 0:
			return -1
	line = DivideLine(results,percent)
	if num_of_tweet <= line:
			return 0 #在分界线左边
	else:
			return 1 #在分界线右边

