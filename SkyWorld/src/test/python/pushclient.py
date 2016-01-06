#!/usr/bin/env python
#coding=utf8

import httplib
import time
import sys
import json
import urllib
import re
import traceback



class ServiceConfig:

	def __init__(self, host, port, threadFlag = False):
		self.host = host
		self.port = port
		self.threadFlag = threadFlag
		self.token = ''
		self.cellphone = ''
		self.username = ''
		self.pwd = ''

	def setUserName(self, username):
		self.username = username

	def setCellphone(self, cellphone):
		self.cellphone = cellphone

	def setToken(self, token):
		self.token = token

	def setPwd(self, pwd):
		self.pwd = pwd 

	def getUserName(self):
		return self.username

	def getCellphone(self):
		return self.cellphone

	def getToken(self):
		return self.token

	def getPwd(self):
		return self.pwd

	def setThreadFlag(self, flag):
		self.threadFlag = flag

	def getThreadFlag(self):
		return self.threadFlag

	def getHost(self):
		return self.host

	def getPort(self):
		return int(self.port)


class Service:
	
	def __init__(self):
		pass

	def run(self, config):
		pass


class RegisterService(Service):
	
	def __init__(self):
		Service.__init__(self)


	def run(self, config):
		httpClient = ''
		result = ''
		data = dict()
		data['header'] = dict()
		data['header']['action'] = "register"

		data['body'] = dict()
		data['body']['cellphone'] = config.getCellphone()
		data['body']['username'] = config.getUserName()
		data['body']['pwd'] = config.getPwd()
		data['body']['confirm_pwd'] = config.getPwd()

		params = urllib.urlencode({"data" : json.dumps(data)})
		try:
	    		print " start to Register Request ",  config.getHost(), ":" , config.getPort() , " with paramter:" , params
	    		headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "application/json"}  
	    		httpClient = httplib.HTTPConnection(config.getHost(), config.getPort(), timeout=30)
	    		httpClient.request('POST', '/SkyWorld/api/1.0/UserAPI', params, headers)
	    		response = httpClient.getresponse()
	    		if (response.status == 200):
				result = response.read()
				result = re.sub('([{,])([^{:\s"]*):', lambda m: '%s"%s":'%(m.group(1),m.group(2)),result)
				print "[INFO] ===> GET RESULT ", result
				rt = json.loads(result)
				if rt['ret'] != 0:
					print '[ERROR] ====>  Register Request result:', rt['ret']
				else:
					print  '[INFO] ====> get token :', rt['token']
					config.setToken(rt['token'])
				return rt['ret'] == 0
            		else:
				print '[ERROR] ====>  Register Request http status :', response.status
				return False
		except Exception, e:
	    		print "Register Request error " , e
			traceback.print_exc()
		finally:
	    		if httpClient:
				httpClient.close()


class UpgradeService(Service):

	def __init__(self):
		Service.__init__(self)


	def run(self, config):
		httpClient = ''
		result = ''
		data = dict()
		data['header'] = dict()
		data['header']['action'] = "upgrade"
		data['header']['token'] = config.getToken()

		data['body'] = dict()

		params = urllib.urlencode({"data" : json.dumps(data)})
		try:
	    		print " start to Upgrade Request ",  config.getHost(), ":" , config.getPort() , " with paramter:" , params
	    		headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "application/json"}  
	    		httpClient = httplib.HTTPConnection(config.getHost(), config.getPort(), timeout=30)
	    		httpClient.request('POST', '/SkyWorld/api/1.0/UserAPI', params, headers)
	    		response = httpClient.getresponse()
	    		if (response.status == 200):
				result = response.read()
				result = re.sub('([{,])([^{:\s"]*):', lambda m: '%s"%s":'%(m.group(1),m.group(2)),result)
				print "[INFO] ===> GET RESULT ", result
				rt = json.loads(result)
				if rt['ret'] != 0:
					print '[ERROR] ====>  Upgrade Request result:', rt['ret']
				else:
					print  '[INFO] ====> get token :', rt['token']
					config.setToken(rt['token'])
				return rt['ret'] == 0
            		else:
				print '[ERROR] ====>  Upgrade Request http status :', response.status
				return False
		except Exception, e:
	    		print "Upgrade Request error " , e
			traceback.print_exc()
		finally:
	    		if httpClient:
				httpClient.close()

		pass

class QuestionService(Service):

	def __init__(self):
		Service.__init__(self)


	def run(self, config):
		pass

class AnswerService(Service):

	def __init__(self):
		Service.__init__(self)


	def run(self, config):
		pass




class ChainService:

	def __init__(self, chain = [], configs = []):
		self.chain = chain
		self.configs = configs

	def run(self):
		for i in  range(len(self.chain)):
			self.chain[i].run(self.configs[i])




if __name__ == "__main__":

	if (len(sys.argv) < 2):
		print "[ERROR] ====>  RUN COMMMAND python pushclient.py host port"		
		sys.exit(1)
	print len(sys.argv)
	re1config = ServiceConfig(sys.argv[1], sys.argv[2], False)
	re2config = ServiceConfig(sys.argv[1], sys.argv[2], False)

	re1config.setUserName("aaaaa2")
	re1config.setCellphone("13682")
	re1config.setPwd("a")

	re2config.setUserName("aaaa82")
	re2config.setCellphone("13582")
	re2config.setPwd("a")

	chain = []
	configs = []
	chain.append(RegisterService())
	configs.append(re1config)

	chain.append(RegisterService())
	configs.append(re2config)

	chain.append(UpgradeService())
	configs.append(re2config)

	chain.append(AnswerService())
	configs.append(re2config)

	#chain.append(QuestionService())
	#configs.append(re1config)
	

	service = ChainService(chain, configs)
	service.run()
	
