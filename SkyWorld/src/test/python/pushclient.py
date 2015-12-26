#!/usr/bin/env python
#coding=utf8

import httplib
import time

httpClient = None



while (True) :
	try:
	    headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "application/json", "Authorization" : int(time.time())}  
	    httpClient = httplib.HTTPConnection('121.42.207.185', 80, timeout=30)
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
