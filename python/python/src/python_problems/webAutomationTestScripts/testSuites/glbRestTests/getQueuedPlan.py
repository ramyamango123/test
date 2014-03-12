#Get Queued Plan
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.22.09.

import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time

FIRSTNAME = 'Tester'
LASTNAME = 'Dummy'
ADDRESS1 = '123 Fake Street'
CITY = 'San Mateo'
STATE = 'CA'
COUNTRY = 'US'
ZIPCODE = '94403'
CLIENTIPADDRESS = '192.168.1.1'
GAMEURL = 'http://gazillion.com'
PLANID = '10003937'
PLANIDREGISTERED = '10003936'

class GetQueuedPlan(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()


    def test_validInfo(self):
        '''Valid information -- TC1'''
        userid, accountId = self.validAccountCreation()
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getQueuedPlan valid account", {"billingId": accountId, "plan id": PLANID})
        
        
    def test_validParentInfo(self):
        '''Valid Parent information -- TC2'''
        userid, accountId = self.validParentAccountCreation()
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getQueuedPlan valid account", {"billingId": accountId, "plan id": PLANID})
        
     # Legacy account creation is no longer supported.So commenting tcs 3, 4
    # def test_validLegacyChildInfo(self):
        # '''Valid Legacy child information -- TC3'''
        # #Using account information from 2010-05-17.  Legacy, so hardcoding since new accounts cannot be made.  These plans will start failing in 2010-12 or so.
        # accountId = '2924506'
        # result = self.toolBox.getQueuedPlan(accountId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))
        # self.successCheck(result)
        # self.infoSuccessCheck(result)
        # self.toolBox.scriptOutput("getQueuedPlan valid child account", {"billingId": accountId, "plan id": PLANID})
        
    
    # def test_validchildInfoLevel1(self):
        # '''Valid child information level 1 -- TC4'''
        # #Using account information from 2010-05-17.  Legacy, so hardcoding since new accounts cannot be made.
        # accountId = '2924510'
        # result = self.toolBox.getQueuedPlan(accountId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))
        # self.successCheck(result)
        # self.infoSuccessCheck(result)
        # self.toolBox.scriptOutput("getQueuedPlan valid level 1 child account", {"billingId": accountId, "plan id": PLANID})
        
        
    def test_validPaypalInfo(self):
        '''Valid Paypal information -- TC5'''
        userid, accountId = self.validPaypalAccountCreation()
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getQueuedPlan valid paypal account", {"billingId": accountId, "plan id": PLANID})
        
        
    def test_validParentPaypalInfo(self):
        '''Valid Parent Paypal information -- TC6'''
        userid, accountId = self.validParentPaypalAccountCreation()
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getQueuedPlan valid paypal account", {"billingId": accountId, "plan id": PLANID})
        
     # Legacy account creation is no longer supported.So commenting tcs  7, 8    
    # def test_validChildPaypalInfo(self):
        # '''Valid Paypal child information -- TC7'''
        # #Using account information from 2010-05-17.  Legacy, so hardcoding since new accounts cannot be made.
        # accountId = '2924491'
        # result = self.toolBox.getQueuedPlan(accountId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))
        # self.successCheck(result)
        # self.infoSuccessCheck(result)
        # self.toolBox.scriptOutput("getQueuedPlan valid paypal child account", {"billingId": accountId, "plan id": PLANID})
        
        
    # def test_validChildPaypalInfoLevel1(self):
        # '''Valid Paypal level 1 child information -- TC8'''
        # #Using account information from 2010-05-17.  Legacy, so hardcoding since new accounts cannot be made.
        # accountId = '2924499'
        # result = self.toolBox.getQueuedPlan(accountId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))
        # self.successCheck(result)
        # self.infoSuccessCheck(result)
        # self.toolBox.scriptOutput("getQueuedPlan valid paypal child level 1 account", {"billingId": accountId, "plan id": PLANID})
        
        
    def test_validAccountNoQueued(self):
        '''Valid Information, no queued plan -- TC9'''
        accountId = self.validAccountCreationNoQueuedPlan()
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['No queued plans exist for this user', '16033'])
        self.infoFailCheck(result, accountId)
        self.toolBox.scriptOutput("getQueuedPlan no queued plan account", {"billingId": accountId})
        
        
    def test_missingParams(self):
        '''Missing information -- TC10'''
        result = self.toolBox.blankGet('getQueuedPlan')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values -- TC11'''
        result = self.toolBox.getQueuedPlan('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
    
    
    def test_invalidInfo(self):
        '''Invalid account id -- TC12'''
        result = self.toolBox.getQueuedPlan('-1')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['No queued plans exist for this user', '16033'])
        self.infoFailCheck(result, '-1')
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC13'''
        userid, accountId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, accountId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC14'''
        userid, accountId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, accountId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_missingTitleCode(self):
        '''Missing Title Code -- TC15'''
        userid, accountId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getQueuedPlan(accountId)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, accountId, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def validAccountCreation(self):
        '''Registers an account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result1 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result1, str(result1))
        sessionId = result1['account']['inSessionID']
        flowId = result1['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        masterBillingAcctId = result1['account']['accountId']
        result2 = self.toolBox.changePlan(masterBillingAcctId, PLANID)
        self.assertTrue('success' in result2, str(result2))
        return id, masterBillingAcctId
        
        
    def validParentAccountCreation(self):
        '''Registers a parent account for the valid info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result1 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result1, str(result1))
        sessionId = result1['account']['inSessionID']
        flowId = result1['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        masterBillingAcctId = result1['account']['accountId']
        result2 = self.toolBox.changePlan(masterBillingAcctId, PLANID)
        self.assertTrue('success' in result2, str(result2))
        return id, masterBillingAcctId
        
        
    def validPaypalAccountCreation(self):
        '''Registers a Paypal account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result1 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED)
        self.assertTrue('account' in result1, str(result1))
        masterBillingAcctId = result1['account']['accountId']
        result2 = self.toolBox.changePlan(masterBillingAcctId, PLANID)
        self.assertTrue('success' in result2, str(result2))
        return id, masterBillingAcctId
        
        
    def validParentPaypalAccountCreation(self):
        '''Registers a parent Paypal account for the valid info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result1 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED)
        self.assertTrue('account' in result1, str(result1))
        masterBillingAcctId = result1['account']['accountId']
        result2 = self.toolBox.changePlan(masterBillingAcctId, PLANID)
        self.assertTrue('success' in result2, str(result2))
        return id, masterBillingAcctId
        
        
    def validAccountCreationNoQueuedPlan(self):
        '''Registers an account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result1 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result1, str(result1))
        masterBillingAcctId = result1['account']['accountId']
        return id
        
        
    def ariaHostedPage(self, sessionId, flowId):
        '''Entering credit card information through selenium'''
        sel = self.selenium
        sel.open(r"file://///hq-fs01/dept/Dev/QA/Web/KungFuPandaWorld/Web_Services/DB/Web%20Services%20Test.html")
        sel.select("wsUrl", "label=" + str(self.toolBox.webHost))
        sel.click("//input[@value='set environment']")
        sel.wait_for_page_to_load("30000")
        sel.is_text_present("Current Environment: " + str(self.toolBox.webHost))
        sel.type("ahp_inSessionID", sessionId)
        sel.type("ahp_flowID", flowId)
        sel.click("ahp_submit")
        sel.wait_for_page_to_load("30000")
        time.sleep(2)
        sel.type("cc_number", "4111111111111111")
        sel.click("cc_expire_mm")
        sel.select("cc_expire_mm", "label=January")
        sel.click("//option[@value='1']")
        sel.click("cc_expire_yyyy")
        sel.select("cc_expire_yyyy", "label=2012")
        sel.click("//option[@value='2012']")
        sel.click("cvv")
        sel.type("cvv", "123")
        sel.click("submitButton")
        sel.wait_for_page_to_load("30000")
        
        
    def acceptPaypalAgreementUsingSelenium(self, URL):
        '''Entering paypal information through selenium'''
        sel = self.selenium
        sel.open("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=/")
        sel.click("link=PayPal Sandbox")
        sel.wait_for_page_to_load("30000")
        #login to paypal
        sel.type("login_email", "sharmila.janardhanan@slipg8.com")
        sel.type("login_password", "password")
        sel.click("submit")
        sel.wait_for_page_to_load("30000")
        time.sleep(6)
        sel.open(URL)
        sel.wait_for_page_to_load("30000")
        time.sleep(2)
        #login to sandbox test account
        sel.type("login_email", "sharmi_1263862208_per@slipg8.com")
        sel.type("login_password", "gazillion")
        sel.click("login.x")
        sel.wait_for_page_to_load("30000")
        sel.click("continue")
        sel.wait_for_page_to_load("30000")
        self.assertEqual("Paypal Callback", sel.get_title())
        time.sleep(1)
        
        
    def successCheck(self, result):
        '''Determines whether the XML structure returned appropriately'''
        self.assertTrue('queuedPlan' in result, "XML structure failed, no queuedPlan")
        self.assertTrue('planId' in result['queuedPlan'], "XML structure failed, no planId")
        self.assertTrue('planName' in result['queuedPlan'], "XML structure failed, no planName")
        self.assertTrue('planTerm' in result['queuedPlan'], "XML structure failed, no planTerm")
        self.assertFalse('errors' in result, "XML structure failed, errors in result")
        
        
    def infoSuccessCheck(self, result):
        '''Determines whether the values returned are appropriate for the values passed'''
        self.assertTrue(result['queuedPlan']['planId'] == PLANID, "PlanId returned not equal to PlanId given: "+ PLANID + " " + str(result))
        self.assertTrue(result['queuedPlan']['planName'] == 'KFPW 6-Month Recurring Plan', "planName failed:" + str(result))
        self.assertTrue(result['queuedPlan']['planTerm'] == '6', "planTerm was not 6: " + str(result))
        
    
    def failureCheck(self, result, expected) :
        '''Determines whether the XML structure returned appropriately'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('queuedPlan' in result, "XML structure failed, success XML present")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, accountId, titleCode = 'KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['billingAcctId'] == accountId, "accountId returned not equal to accountId given: " + accountId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getQueuedPlan", "Service returned not equal to service called: getQueuedPlan" + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))