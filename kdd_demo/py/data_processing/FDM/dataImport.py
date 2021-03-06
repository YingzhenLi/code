# -*- coding: utf-8 -*-
'''
import data using module cPickle
specified for FDM
@author Yingzhen Li KDD@SYSU
'''
import cPickle
import os

'''import'''
def importData(fileName):
    path = os.getcwd() [:-3]+ '/data/'
    f = open(path + fileName, 'r')
    '''convert string to integer and double'''
    data = []
    i = 0
    for line in f:
        data.append(dict())
        temp = line.split('\t')[1][:-1].split(';')
        for keyAndW in temp:
            data[i][keyAndW.split(':')[0]]=float(keyAndW.split(':')[1])
        i += 1
        #if i >= NORM:
            #break
    f.close()
    save = cPickle.dumps(data)
    f = open('Data'+fileName,'w')
    f.write(save)
    f.close()
    return data

'''read'''
def loadData(filename):
    f = open(filename,'r')
    load = ''.join(f.readlines())
    f.close()
    return cPickle.loads(load)

def saveData(data, filename):
    f = open(filename,'w')
    # data = [tran1, tran2,...]
    save = []
    for transaction in data:
        # transaction = 'k1;k2;k3;...'
        save.append(transaction.split(';'))
    save = cPickle.dumps(save)
    print 'cPickle Done!'
    flag = f.write(save)
    f.close()
    print 'write Done!'
    return flag
