'''Finish Paypal plan Testsuite
Passes Billing Account Id(type - int) and paypal token
to service Finish Paypal Plan using Selenium to agree paypal
This WS finishes an Paypal billing agreement
Created by Sharmila Janardhanan Date on 01/08/2010'''

from testSuiteBase import TestSuiteBase
from selenium import selenium

import random, string, time

PLANID = "10003938"
BILLINGTYPE = "11"
FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "address1"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIP = "94087"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"

class FinishPaypalPlan(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=/")
        self.selenium.start()
        self.selenium.window_maximize()
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()
        
                                    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function'''
        resultDict = self.toolBox.blankPost('finishPaypalPlan')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&service=finishPaypalPlan', \
                                    'Finish Paypal plan Parameters not matching')
        
    
    def test_emptyBillingId(self):
        '''Pass empty billing Id to the service'''
        billingId = paypalToken = ""
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, paypalToken)
        
    
    def test_invalidBillingId(self):
        '''Pass invalid billing Id to the service'''
        billingId = paypalToken = "invalid"
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, paypalToken)
        
    
    def test_invalidPaypalToken(self):
        '''Pass invalid Paypal token with valid billing Id to the service'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingType = "11"
        billingResult = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        paypalToken = "0000000000000"
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16028', 'Unable to set billing payment information. Please try again later.')
        self.parameterValuesCheck(resultDict, billingId, paypalToken)   
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "object does not exist: token", "No extra info tag found")
        
        
    def test_printPaypalTokenToTestExpiration(self):
        '''Print paypal token for expired token(expiration time 3 hours - manual test)'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        masterBillingType = "11"
        billingResult = self.toolBox.createBillingAcct(id, gameId, masterBillingType, CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.toolBox.scriptOutput("finish Paypal - Expired Paypal Token", {"Billing Id": billingId, "Paypal URL with Token": URL})
        
        
    def test_validFinishPaypalPlanForChild(self):
        '''Pass valid billing id to finish paypal plan created (for child)'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingType = "11"
        billingResult = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID)  
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.successCheck(resultDict)
                
            
    def test_validFinishPaypalPlanForVerifiedAdult(self):
        '''Pass valid billing id to finish paypal plan created (for verified adult)'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        billingType = "11"
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS, PLANID)  
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.successCheck(resultDict)
         
        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code'''
        billingId = "1040861"
        paypalToken = "EC-4YF57226LH555882H"
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, billingId, paypalToken, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code'''
        billingId = "1040861"
        paypalToken = "EC-4YF57226LH555882H"
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, paypalToken, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service'''
        billingId = "1040861"
        paypalToken = "EC-4YF57226LH555882H"
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'billingAcctId=' + billingId + '&service=finishPaypalPlan' +
                                    '&paypalToken=' + paypalToken, 'Finish paypal plan Parameters not matching')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    ########################
    ###            Helper Methods          ###
    ########################
    
    def acceptPaypalAgreementUsingSelenium(self, URL):
        #using selenium to agree paypalPlan
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
        #self.assertEqual("Review your information - PayPal", sel.get_title())
        sel.click("continue")
        sel.wait_for_page_to_load("30000")
        #elf.assertEqual("Paypal Callback", sel.get_title())
        time.sleep(1)
        
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
    def parameterValuesCheck(self, resultDict, billingId, paypalToken, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&billingAcctId=' + billingId + 
                                    '&service=finishPaypalPlan' + '&paypalToken=' + paypalToken, \
                                    'Finish Paypal plan Parameters not matched')                              

    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not found")
        self.assertTrue("value" in resultDict['success'], "Value tag not found")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')