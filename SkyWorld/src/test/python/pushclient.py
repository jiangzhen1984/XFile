#!/usr/bin/env python
#coding=utf8

import httplib
import time
import sys

httpClient = None

print "host:" , sys.argv[1]
print "port:" , sys.argv[2]
print "get auth:" , sys.argv[3]


while (True) :
	try:
	    print " start to new request "
	    headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "application/json", "Authorization" : sys.argv[3]}  
	    httpClient = httplib.HTTPConnection(sys.argv[1], int(sys.argv[2]), timeout=30)
	    httpClient.request('GET', '/SkyWorld/push', '', headers)

	    #response是HTTPResponse对象
	    response = httpClient.getresponse()
	    if (response.status == 200):
	        print response.read()
            else:
                print response.status
	except Exception, e:
	    print e
	finally:
	    if httpClient:
		httpClient.close()
