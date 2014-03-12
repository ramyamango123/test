#Get Billing Account Info (GET)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.21.09.

import sys
from selenium import selenium
import time

from testSuiteBase import TestSuiteBase

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


class GetBillingAcctInfo(TestSuiteBase):


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
        billingId = self.validAccountCreation()
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, child=0)
        self.toolBox.scriptOutput("getBillingAcctInfo valid master account", {"billingId": billingId})
        
        
    def test_validParentInfo(self):
        '''Valid parent information -- TC2'''
        billingId = self.validParentAccountCreation()
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, child=0)
        self.toolBox.scriptOutput("getBillingAcctInfo valid master account", {"billingId": billingId})
  
    def test_validPaypalInfo(self):
        '''Valid PayPal information -- TC5'''
        #Failing this testcase since Paypal flow is still inactive
        self.fail()
        billingId = self.validPaypalAccountCreation()
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, method='Paypal', child=0)
        self.toolBox.scriptOutput("getBillingAcctInfo valid Paypal account", {"billingId": billingId})
        
        
    def test_validParentPaypalInfo(self):
        '''Valid parent PayPal information -- TC6'''
       #Failing this testcase since Paypal flow is still inactive
        self.fail()
        billingId = self.validParentPaypalAccountCreation()
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, method='Paypal', child=0)
        self.toolBox.scriptOutput("getBillingAcctInfo valid Paypal account", {"billingId": billingId})
 
    def test_missingParams(self):
        '''Missing information -- TC7'''
        result = self.toolBox.blankGet('getBillingAcctInfo')

        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values -- TC8'''
        result = self.toolBox.getBillingAcctInfo('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
    
    
    def test_invalidInfo(self):
        '''Invalid account id -- TC9'''
        result = self.toolBox.getBillingAcctInfo('00000000000000000')

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['No billing account exists for this user', '16032'])
        self.infoFailCheck(result, '00000000000000000')
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC10'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, billingId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC11'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getBillingAcctInfo(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, billingId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
    
    def test_missingTitleCode(self):
        '''Without a Title Code -- TC12'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getBillingAcctInfo(billingId)

        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, billingId, None)
        self.toolBox.setTitleCodeParam('KFPW')
    
    # Helper Methods #
    
    def validAccountCreation(self):
        '''Registers an account for the valid info test '''
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
        masterBillingAcctId = result['account']['accountId']
        return result['account']['accountId']

        
    def validParentAccountCreation(self):
        '''Registers a parent account for the valid info test'''
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
        masterBillingAcctId = result['account']['accountId']
        return result['account']['accountId']    
        
        
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
        return result['account']['accountId']
        
        
    def validParentPaypalAccountCreation(self):
        '''Registers a parent paypal account for the valid info test'''
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
        return result['account']['accountId']
        
        
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
        
    
    def successCheck(self, result, level=1) :
        '''Checks for success XML structure'''
        self.assertTrue('billingAcct' in result, "XML structure failed, no billingAcct")
        self.assertTrue('planName' in result['billingAcct'], "XML structure failed, no planName")
        self.assertTrue('planTerm' in result['billingAcct'], "XML structure failed, no planTerm")
        self.assertTrue('planId' in result['billingAcct'], "XML structure failed, no planId")
        self.assertTrue('accountStatus' in result['billingAcct'], "XML structure failed, no accountStatus")
        self.assertTrue('billDate' in result['billingAcct'], "XML structure failed, no billDate")
        self.assertTrue('creationDate' in result['billingAcct'], "XML structure failed, no creationDate")
        self.assertFalse('errors' in result, "Errors tag found in result XML")
        if level==1:
            self.assertTrue('ccId' in result['billingAcct'], "XML structure failed, no ccId")
            self.assertTrue('ccNum' in result['billingAcct'], "XML structure failed, no ccNum")
            self.assertTrue('ccYear' in result['billingAcct'], "XML structure failed, no ccYear")
            self.assertTrue('ccMonth' in result['billingAcct'], "XML structure failed, no ccMonth")
            self.assertTrue('payMethod' in result['billingAcct'], "XML structure failed, no payMethod")
            self.assertTrue('paymentType' in result['billingAcct'], "XML structure failed, no paymentType")
    
    
    def infoSuccessCheck(self, result, method="Credit Card", level=1, child=1) :
        '''checks for success XML information returned'''
        self.assertTrue(result['billingAcct']['planTerm'] == '1', "planTerm does not match the default value: " + str(result))
        self.assertTrue(result['billingAcct']['accountStatus'] == 'ACTIVE', "accountStatus does not match the default value: " + str(result))
        self.assertTrue(result['billingAcct']['planId'] == PLANID, "planId does not match the default value: " + str(result))
        self.assertTrue(result['billingAcct']['planName'] == 'KFPW Monthy Recurring Plan', "planName did not match the default value: " + str(result))
        
        if method == "Credit Card":
            if level == 1:
                self.assertTrue(result['billingAcct']['ccId'] == '1', "ccId does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['ccNum'] == '1111', "ccNum does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['payMethod'] == 'Credit Card', "payMethod does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['ccYear'] == '2012', "ccYear does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['ccMonth'] == '1', "planTerm does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['paymentType'] == '1', "paymentType does not match the default value: " + str(result))
            
        else:
            if level == 1:
                self.assertTrue(result['billingAcct']['paymentType'] == '11', "paymentType does not match the default value: " + str(result))
                self.assertTrue(result['billingAcct']['payMethod'] == 'PayPal Express Checkout', "payMethod does not match the default value: " + str(result))
        #not testing billDate and creationDate; too likely to change
        
    
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('billingAcct' in result, "XML structure failed, success in fail case")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, billingAcctId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['billingAcctId'] == billingAcctId, "BillingAcctId returned not equal to userId given: " + billingAcctId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getBillingAcctInfo", "Service returned not equal to service called: getBillingAcctInfo " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))