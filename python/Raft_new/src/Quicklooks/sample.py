#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
Contact: Christopher Eichhorn / iWork Tools Team
"""

no_raft = True


# This is a Raft test. For more information see http://raft.apple.com
testDescription  = ""                         # Add a brief description of test functionality
testVersion      = "2013.05.15 v1.0.2"        # Used to differentiate between results for different versions of the test

if no_raft:
    pass
else:
    testState        = ProductionState            # Possible values: DevelopmentState, ProductionState

from selenium import webdriver
    
class Test:
    def __init__(self): # setUp
        self.driver = webdriver.Firefox()
        self.driver.get("http://www.google.com")
        
        if no_raft:
            return
        
        bs_common.setConfigurationData()
        bs_common.writeConfigurationDataToLogFile()
        bs_common.writeResultsHeaderDataToLogFile()
        bs_common.writeOSXProfileToFile()
        
        self.myResultsData = bs_common.getResultsData()
        
        bs_common.writeItemToCsvFile("Pi Automation Test Suite started on: "+ time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime()))
        bs_common.writeItemToCsvFile("OS: OS X Browser: Safari / "+ myResultsData["configurations"]["browserVersion"])
        
        #self.target1 = target.processes()["Safari"].windows()["iCloud - Keynote"]
        #time.sleep(5)
    
    def __del__(self): # tearDown
        driver = self.driver
        driver.quit()
        
        if no_raft:
            return
        
        bs_icloud.iCloudSignOut("xxxxxxxx")
        bs_common.quitSafari()
        bs_common.fileDeleteFromOSX("Budget.numbers")
        bs_common.resetConfigurationData()    
        logPass()

    def test1(self):
        driver = self.driver
        #self.target1.click()
        #driver.click()
        print "Test1 pass"

    
    def test2(self):
        print "Test2 pass"

# def setUp(target):
#     ' In ff we have to initiate as webdriver.Firefox()'
#     self.driver = webdriver.Firefox()
#     self.driver.implicitly_wait(20) 

def runTest(params):
    ###################################################################
    sys.path.append('../Libraries')
    import bs_common, bs_icloud, bs_gilligan
    ###################################################################
    
    Test().test1()
    Test().test2()
    

if __name__ == '__main__':
    # The following code allows this test to be invoked outside the harness and should be left unchanged
    import os, sys
    args = [os.path.realpath(os.path.expanduser("/usr/local/bin/raft")), "-f"] + sys.argv
    
    if no_raft:
        runTest(None)
    else:
        os.execv(args[0], args)
    