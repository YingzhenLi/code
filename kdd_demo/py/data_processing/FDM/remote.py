# -*- coding: utf-8 -*-
"""
Created on Sat Apr 14 20:41:25 2012
class remote
@author: Yingzhen Li KDD@SYSU
"""

from itertools import combinations
import cPickle

class remote(object):
    def __init__ (self, data = [], transactions = {}):
        self.data = data     # dataset: {user:{key:weight},...}  
        self.tran = transactions    #transations: {'transaction':support}
        self.global_large = []  # global large set

    '''compute Transaction'''
    def transaction (self, k = 1):        
        # self.global_large == []
        if k == 1:  
            return self.__count_items_in_transactions(self.data, None)
        else:
            # Apriori Algorithm
            large_set = self.__transform(self.global_large)
            return self.__apriori_gen(large_set)
    
    '''transform'''
    @staticmethod
    def __transform(large):
        large_set = []
        # large = [tran,...], tran = key;key;...
        # large_set = [[key,..]...]
        for transaction in large:
            large_set.append(transaction.split(';'))
        return large_set
    
    def local_large(self, candidate_set):       
        # count local supports
        self.tran = self.__count_items_in_transactions(self.data, candidate_set)
        return self.tran
    
    '''count local supports'''
    @staticmethod
    def __count_items_in_transactions(data, candidate_set):
        '''Return dictionary with items as keys and their count as value'''
        counter = dict()
        # scan every line of database (every item)
        if candidate_set == None:
            '''initialize the dictionary if no element in candidate_set'''
            for user in data.keys():   
                for item in data[user]:
                    if str(item) not in counter:
                        counter[str(item)] = data[user][item]
                    else:
                        counter[str(item)] += data[user][item]
        else:
            '''in candidate_set are the unions of items in line
            check if any union in line and count'''
            for user in data.keys():
                for item in candidate_set:
                    if type(item) == str:
                        if item in data[user]:
                            if item not in counter:
                                counter[item] = data[user][item]
                            else:
                                counter[item] += data[user][item]
                    elif any([ x not in data[user].keys() for x in item ]):
                        continue
                    else:
                        temp = []
                        string = ''
                        for x in item:
                            temp.append(data[user][x])
                            string += str(x) + ';'
                        if string[:-1] not in counter:
                            counter[string[:-1]] = min(temp)
                        else:
                            counter[string[:-1]] += min(temp)
        return counter
    
    '''apriori generation'''
    @staticmethod
    def __apriori_gen(large_set):
        '''Return list of candidates for inclusion in next large set'''
        L = []
        n = len(large_set)
        if n == 0:  # no candidate
            return L
        if type(large_set[0]) == str:
            ITER = combinations(large_set,2)
            while 1:
                try:
                    L.append(cPickle.dumps(list(ITER.next())))
                except StopIteration:
                    break
            return L
        for i in range(n):
            for j in range(i+1,n):
                if large_set[i][1:] == large_set[j][:-1]:
                    candidate = large_set[i][:]
                    candidate.append(large_set[j][-1])
                    L.append(candidate)
        return L    
    
    '''polling'''
    @staticmethod
    def polling (SUP, NORM, size, tran):
        # local_large: [[(transaction,support)],...]
        local_large = dict()
        '''initialization'''
        for j in range(size):
            local_large[j] = []
        for transaction in tran:
            if tran[transaction] >= SUP * NORM:
                '''arrange a polling site to item'''
                temp = transaction.split(';')
                j = 0
                for i in temp:
                    j += int(i)
                j %= size
                local_large[j].append([transaction,tran[transaction]])
        return local_large

        
