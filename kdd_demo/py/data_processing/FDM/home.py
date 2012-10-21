# -*- coding: utf-8 -*-
"""
Created on Sat Apr 14 20:43:27 2012
class home
@author: Yingzhen Li
"""

class home:
        
    '''generate Large''' 
    @staticmethod      
    def compute_large (temp = [], temp_supp = [], site = [], size = 1, CONF = 0.7, old_large = {}, old_large_supp = {}):
        large = {}      # large = {transaction1: [site1, site2,...],...}
	large_supp = {}	# large_supp = {transaction1: support1,...}
        for j in range(size):
            for item in temp[j]:
		k = 0
                if item not in large:
		    print item
                    large[item] = site[j][item]
		    large_supp[item] = temp_supp[j][k]/size
                else:
                    large[item] = list(set(large[item]).union(set(site[j][item])))
		    large_supp[item] += temp_supp[j][k]/size
		k += 1
	# first iteration
	if old_large_supp == {}:
	    return large, large_supp

	# check confidence if not first iteration
	for item in large.keys():
	    for old_item in old_large.keys():
		if old_item in item:
		    if large_supp[item] < old_large_supp[old_item] * CONF:
			del large[item]
			del large_supp[item]
			continue
        return large, large_supp
    
    '''divide large into global_set[i], check!'''
    @staticmethod
    def divide_global_set (size, large):
        global_large = {}        # global_large = {[tran1, tran2,...],...}
        for item in large:
            for j in large[item]:
                if j not in global_large.keys():
                    global_large[j] = []
                if item not in global_large[j]:
                    global_large[j].append(item)
        return global_large
 
             
    
