#Get Master Plan Billing Account
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.01.09.

import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time

CLIENTIPADDRESS = '127.0.0.1'
FIRSTNAME = 'Tester'
LASTNAME = 'Dummy'
ADDRESS1 = '123 Fake Street'
CITY = 'San Mateo'
STATE = 'CA'
COUNTRY = 'US'
ZIPCODE = '94403'
PHONENUMBER = '555-555-5555'
CREDITCARDNO='378282246310005'
SECURENO='123'
CCMONTH='10'
CCYEAR='2011'
GAMEURL='http://gazillion.com'
PLANID='10003936'

class GetMasterBillingAccount(TestSuiteBase):


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
        userid, billingId = self.validAccountCreation()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('masterAccount' in result, "No masterAccount found")
        self.assertTrue('accountId' in result['masterAccount'], "No accountId found")
        self.assertFalse('errors' in result, "Errors in success XML")
        
        #values check
        self.assertEqual(billingId, result['masterAccount']['accountId'], "values don't match")
        self.toolBox.scriptOutput("getMasterBillingAccount valid info account", {"userid": userid, "billingId": billingId})
        
          
    def test_validParentInfo(self):
        '''Valid parent information -- TC2'''
        userid, billingId = self.validAccountCreationParent()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('masterAccount' in result, "No masterAccount found")
        self.assertTrue('accountId' in result['masterAccount'], "No accountId found")
        self.assertFalse('errors' in result, "Errors in success XML")
        
        #values check
        self.assertEqual(billingId, result['masterAccount']['accountId'], "values don't match")
        self.toolBox.scriptOutput("getMasterBillingAccount valid parent account", {"userid": userid, "billingId": billingId})
              
    
    def test_validPaypalInfo(self):
        '''Valid Paypal information -- TC3'''
        #Failing this testcase since Paypal flow is still inactive
        self.fail()
        userid, billingId = self.validPaypalAccountCreation()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('masterAccount' in result, "No masterAccount found")
        self.assertTrue('accountId' in result['masterAccount'], "No accountId found")
        self.assertFalse('errors' in result, "Errors in success XML")
        
        #values check
        self.assertEqual(billingId, result['masterAccount']['accountId'], "values don't match")
        self.toolBox.scriptOutput("getMasterBillingAccount valid paypal account", {"userid": userid, "billingId": billingId})
        
          
    def test_validParentPaypalInfo(self):
        '''Valid Paypal parent information -- TC4'''
        #Failing this testcase since Paypal flow is still inactive
        self.fail()
        userid, billingId = self.validPaypalAccountCreationParent()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('masterAccount' in result, "No masterAccount found")
        self.assertTrue('accountId' in result['masterAccount'], "No accountId found")
        self.assertFalse('errors' in result, "Errors in success XML")
        
        #values check
        self.assertEqual(billingId, result['masterAccount']['accountId'], "values don't match")
        self.toolBox.scriptOutput("getMasterBillingAccount valid parent paypal account", {"userid": userid, "billingId": billingId})
    

    def test_validInfoNoBilling(self):
        '''Valid information with no billing account attached -- TC5'''
        _, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        userid = result['user']['id']
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))   
        self.failureCheck(result, ['No billing account exists for this user', '16032'])
        self.infoFailCheck(result, userid)
        self.toolBox.scriptOutput("getMasterBillingAccount no billing account", {"userid": userid})
        
        
    def test_unvalidatedInfo(self):
        '''Unvalidated CC user -- TC6'''
        userid, billingId = self.invalidAccountCreation()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['No billing account exists for this user', '16032'])
        self.infoFailCheck(result, userid)
        self.toolBox.scriptOutput("getMasterBillingAccount invalid CC account", {"userid": userid, "billingId": billingId})
        
    
    def test_unvalidatedPaypalInfo(self):
        '''Unvalidated paypal user -- TC7'''
        userid, billingId = self.invalidPaypalAccountCreation()
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['No billing account exists for this user', '16032'])
        self.infoFailCheck(result, userid)
        self.toolBox.scriptOutput("getMasterBillingAccount invalid paypal account", {"userid": userid, "billingId": billingId})

    
    def test_missingParams(self):
        '''Missing information -- TC8'''
        result = self.toolBox.blankGet('getMasterBillingAccount')

        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
    
    
    def test_unexpectedValues(self):
        '''Empty values -- TC9'''
        result = self.toolBox.getMasterBillingAccount('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
    
    
    def test_invalidInfo(self):
        '''Invalid account id -- TC10'''
        result = self.toolBox.getMasterBillingAccount('00000000000000000')

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, '00000000000000000')
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC11'''
        userid, billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, userid, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Blank Title Code -- TC12'''
        userid, billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, userid, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_missingTitleCode(self):
        '''No Title Code -- TC13'''
        userid, billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getMasterBillingAccount(userid)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, userid, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
     # Helper Methods #   
     
    def validAccountCreation(self):
        '''Registers an account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, result)
        sessionId = result['account']['inSessionID']
        flowId = result['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return id, result['account']['accountId']
        
        
    def validAccountCreationParent(self):
        '''Registers an account for the valid parent info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, result)
        sessionId = result['account']['inSessionID']
        flowId = result['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return id, result['account']['accountId']
        
        
    def validPaypalAccountCreation(self):
        '''Registers a paypal account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID)
        self.assertTrue('account' in result, result)
        masterBillingAcctId = result['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(masterBillingAcctId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(masterBillingAcctId, paypalToken)
        return id, masterBillingAcctId
        
        
    def validPaypalAccountCreationParent(self):
        '''Registers a paypal account for the valid parent info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID)
        self.assertTrue('account' in result, result)
        masterBillingAcctId = result['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(masterBillingAcctId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(masterBillingAcctId, paypalToken)
        return id, masterBillingAcctId
        
        
    def invalidAccountCreation(self):
        '''Registers an invalid account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, result)
        return id, result['account']['accountId']
        
        
    def invalidPaypalAccountCreation(self):
        '''Registers an invalid paypal account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID)
        self.assertTrue('account' in result, result)
        masterBillingAcctId = result['account']['accountId']
        return id, masterBillingAcctId
        
        
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
    
    
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertFalse('user' in result, "XML structure returned success XML")
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('masterAccount' in result, "XML structure failed, masterAccount present")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, userId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['accountId'] == userId, "UserId returned not equal to userId given: " + userId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getMasterBillingAccount", "Service returned not equal to service called: getMasterBillingAccount" + str(parameters))
        if titleCode == None :
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))