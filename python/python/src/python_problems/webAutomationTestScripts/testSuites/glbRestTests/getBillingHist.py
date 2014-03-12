#Get Billing Hist (GET)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 1.6.10.

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
PLANID = '10003936'

class GetBillingHist(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()
        
    def test_validInfo(self):
        '''Valid information - TC1'''
        billingId = self.validAccountCreation()
        result = self.toolBox.getBillingHist(billingId)
        
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getBillingHist valid account", {"billingId": billingId})

        
    def test_validParentInfo(self):
        '''Valid parent information -- TC2'''
        billingId = self.validParentAccountCreation()
        result = self.toolBox.getBillingHist(billingId)
        
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getBillingHist valid parent account", {"billingId": billingId})
        
            
    
    def test_validPaypalInfo(self):
        '''Valid paypal information -- TC3'''
        #Failing this testcase since Paypal flow is still inactive
        self.fail()
        billingId = self.validPaypalAccountCreation()
        result = self.toolBox.getBillingHist(billingId)
        
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getBillingHist valid Paypal account", {"billingId": billingId})
        
        
    def test_validParentPaypalInfo(self):
        '''Valid parent paypal information -- TC4'''
        #Failing this testcase since Paypal flow is still inactive
        self.fail()
        billingId = self.validParentPaypalAccountCreation()
        result = self.toolBox.getBillingHist(billingId)
        
        self.successCheck(result)
        self.infoSuccessCheck(result)
        self.toolBox.scriptOutput("getBillingHist valid parent Paypal account", {"billingId": billingId})
    
           
          
    def test_noBillingHistory(self):
        '''Valid information with no billing history -- TC5'''
        billingId = self.validAccountCreationNoChild()
        result = self.toolBox.getBillingHist(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["No billing transaction history found", '16031'])
        self.infoFailCheck(result, billingId)
        self.toolBox.scriptOutput("getBillingHist no history account", {"billingId": billingId})
        
        
    def test_invalidInfo(self):
        '''Invalid information -- TC6'''
        result = self.toolBox.getBillingHist('0000000000000000')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["No billing account exists for this user", '16032'])
        self.infoFailCheck(result, '0000000000000000')
        

    def test_missingParams(self):
        '''Missing information -- TC7'''
        result = self.toolBox.blankGet('getBillingHist')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values -- TC8'''
        result = self.toolBox.getBillingHist('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC9'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getBillingHist(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, billingId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC10'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getBillingHist(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, billingId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
    
    def test_withoutTitleCode(self):
        '''Without a Title Code -- TC11'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getBillingHist(billingId)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, billingId, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
    # Helper Methods #
    
    def validAccountCreation(self):
        '''Registers an account for the valid info test'''
        username, userResult = self.toolBox.registerNewUsername()
        self.assertTrue('user' in userResult, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = userResult['user']['id']
        billingType = '1'
        masterResult = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in masterResult, "createMasterBilling failed: " + str(masterResult))
        masterBillingAcctId = masterResult['account']['accountId']
        sessionId = masterResult['account']['inSessionID']
        flowId = masterResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return masterBillingAcctId
        
        
    def validParentAccountCreation(self):
        '''Registers a parent account for the valid info test'''
        username, parentResult = self.toolBox.registerNewParent()
        self.assertTrue('user' in parentResult, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = parentResult['user']['id']
        billingType = '1'
        masterResult = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in masterResult, "createMasterBilling failed: " + str(masterResult))
        masterBillingAcctId = masterResult['account']['accountId']
        sessionId = masterResult['account']['inSessionID']
        flowId = masterResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return masterBillingAcctId
        
        
    def validAccountCreationNoChild(self):
        '''Registers an account for the no billing hist test'''
        username, userResult = self.toolBox.registerNewUsername()
        self.assertTrue('user' in userResult, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = userResult['user']['id']
        billingType = '1'
        masterResult = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in masterResult, "createMasterBilling failed: " + str(masterResult))
        masterBillingAcctId = masterResult['account']['accountId']
        return masterBillingAcctId

        
    def validPaypalAccountCreation(self):
        '''Registers a paypal account for the valid info test'''
        username, userResult = self.toolBox.registerNewUsername()
        self.assertTrue('user' in userResult, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = userResult['user']['id']
        billingType = '11'
        masterResult = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE)
        self.assertTrue('account' in masterResult, "createMasterBilling failed: " + str(masterResult))
        masterBillingAcctId = masterResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(masterBillingAcctId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(masterBillingAcctId, paypalToken)
        return masterBillingAcctId
        
        
    def validParentPaypalAccountCreation(self):
        '''Registers a parent paypal account for the valid info test'''
        username, parentResult = self.toolBox.registerNewParent()
        self.assertTrue('user' in parentResult, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = parentResult['user']['id']
        billingType = '11'
        masterResult = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE)
        self.assertTrue('account' in masterResult, "createMasterBilling failed: " + str(masterResult))
        masterBillingAcctId = masterResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(masterBillingAcctId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(masterBillingAcctId, paypalToken)
        return masterBillingAcctId
        
        
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
        
        
    def successCheck(self, result) :
        '''Checks for success case XML structure'''
        self.assertTrue('listActivity' in result, "listActivity not found in result: " + str(result))
        self.assertTrue('date' in result['listActivity'], "date not found in result: " + str(result))
        self.assertTrue('price' in result['listActivity'], "price not found in result: " + str(result))
        self.assertTrue('activityName' in result['listActivity'], "date not found in result: " + str(result))
        self.assertFalse('errors' in result, "XML structure failed, errors tag found in result: " + str(result))
    
    
    def infoSuccessCheck(self, result) :
        '''Checks for success case XML information returned'''
        self.assertTrue(result['listActivity']['date'][:2] == "20", "Year returned does not start with 20: " + str(result))
        self.assertTrue(result['listActivity']['date'][2:4] >= '10', "Year returned is not greater than 2009: " + str(result))
        self.assertTrue(result['listActivity']['date'][5:7] >= '01', "Month returned less than 1: " + str(result))
        self.assertTrue(result['listActivity']['date'][5:7] <= '12', "Month returned greater than 12: " + str(result))
        self.assertTrue(result['listActivity']['date'][8:10] >= '01', "Day returned less than 1: " + str(result))
        self.assertTrue(result['listActivity']['date'][8:10] <= '31', "Day returned greater than 31: " + str(result))
        self.assertTrue(result['listActivity']['price'] == '5.95', "Price returned was not 1: " + str(result))
        self.assertTrue(result['listActivity']['activityName'][:7] == 'Invoice', "ActivityName did not start with Invoice: " + str(result))
        
    
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('listActivity' in result, "XML structure failed, listActivity found in error XML")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, billingAcctId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['billingAcctId'] == billingAcctId, "BillingAcctId returned not equal to billingAcctId given: " + billingAcctId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getBillingHist", "Service returned not equal to service called: getBillingHist " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))