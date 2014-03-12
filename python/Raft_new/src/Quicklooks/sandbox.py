#!/usr/bin/python
# -*- coding: utf-8 -*-
if __name__ == '__main__':
	# The following code allows this test to be invoked outside the harness and should be left unchanged
	import os, sys
	args = [os.path.realpath(os.path.expanduser("/usr/local/bin/raft")), "-f"] + sys.argv
	os.execv(args[0], args)
"""
Contact: Christopher Eichhorn / iWork Tools Team
"""
# This is a Raft test. For more information see http://raft.apple.com
testDescription  = ""                 		# Add a brief description of test functionality
testVersion      = "2013.05.15 v1.0.2"		# Used to differentiate between results for different versions of the test
testState        = ProductionState			# Possible values: DevelopmentState, ProductionState

from selenium import webdriver

def test1(driver):
	pass

def test2(driver):
	pass

def test3(driver):
	pass

# def setUp(target):
# 	' In ff we have to initiate as webdriver.Firefox()'
# 	self.driver = webdriver.Firefox()
# 	self.driver.implicitly_wait(20) 

def runTest(params):
	###################################################################
	sys.path.append('../Libraries')
	import bs_common, bs_icloud, bs_gilligan
	###################################################################

	driver = webdriver.Firefox()
	time.sleep(10)
	driver.get("http://www.google.com")
	
	target.processes()["Safari"].windows()["iCloud - Keynote"].click()
	time.sleep(5)
	#target.processes()["Firefox"].mainWindow().click()
	
	test1(driver)
	test2(driver)
	test3(driver)	
        
        




	
