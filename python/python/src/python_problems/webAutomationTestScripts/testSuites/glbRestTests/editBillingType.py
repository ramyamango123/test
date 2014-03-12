'''Edit Billing Type Testsuite
Passes Billing account Id (type - int) obtained via
create master billing account and
Billing type CC-1 and payPal-11 (type - int) to service EditBillingType
This WS edits an existing billing account type (CC or paypal)
For editing CC type optional parameters are required
Created by Sharmila Janardhanan Date on 12/01/2009'''

from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, time

FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "Address1"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIP = "94087"
PLANID = "10003937"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"

class EditBillingType(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()
        
    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function'''
        resultDict = self.toolBox.blankPost('editBillingType')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=editBillingType', \
                                    'Edit billing type parameter not matching')
        

    def test_allEmptyValues(self):
        '''Pass all empty values to the service'''
        billingId = billingType = gameUrl = ""
        resultDict = self.toolBox.editBillingType(billingId, billingType, gameUrl)  
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, billingType, gameUrl)
        
        
    def test_allInvalidInformation(self):
        '''Pass all invalid information to the service'''
        billingId = billingType = gameUrl = "invalid"
        resultDict = self.toolBox.editBillingType(billingId, billingType, gameUrl)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, billingType, gameUrl)
        
        
    def test_mixEmptyInvalidValues(self):
        '''Pass mixture of invalid and empty values'''
        billingId = billingType = ""
        gameUrl = "invalid"
        resultDict = self.toolBox.editBillingType(billingId, billingType, gameUrl)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, billingType, gameUrl)
    
    
    def test_mixEmptyValidValues(self):
        '''Pass mixture of empty and valid values'''
        billingId = billingType = ""
        resultDict = self.toolBox.editBillingType(billingId, billingType, GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, billingType, GAMEURL)
        
        
    def test_validBillingIdInvalidBillingType(self):
        '''Pass valid billing id and invalid billing type'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingResult = self.toolBox.createBillingAcct(id, gameId, 11, CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        billingType = "100"
        resultDict = self.toolBox.editBillingType(billingId, billingType, GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16030', 'The billing type specified does not exist')
        self.parameterValuesCheck(resultDict, billingId, billingType, GAMEURL)
            
        
    def test_invalidBillingIdValidBillingType(self):
        '''Pass invalid billing id and valid billing type'''
        billingId = "000000000"
        billingType = "1"
        resultDict = self.toolBox.editBillingType(billingId, billingType, GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, billingType, GAMEURL)
                                               

    def test_invalidGameUrlWithValidInfo(self):
        '''Pass all valid information with invalid game Url'''
        billingId = self.toolBox.getLatestBillingIdFromDb()
        billingType = "1" 
        gameUrl = "invalid"
        resultDict = self.toolBox.editBillingType(billingId, billingType, gameUrl)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '15002', 'The URL is invalid')
        self.parameterValuesCheck(resultDict, billingId, billingType, gameUrl)
            
            
    def test_editBillingTypeForCancelledBilling(self):
        '''Edit billing type for cancelled billing'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingResult = self.toolBox.createBillingAcct(id, gameId, "1", CLIENTIPADDRESS, PLANID, FIRSTNAME, 
                                                             LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        cancelResult = self.toolBox.cancelPlan(billingId)
        resultDict = self.toolBox.editBillingType(billingId, "11", GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16043', 'The status of the billing account is not valid')
        self.parameterValuesCheck(resultDict, billingId, "11", GAMEURL)
        
   
    def test_validEditBillingTypeForPaypal(self):
        '''Change billing type (child info) from paypal to creditCard'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingResult = self.toolBox.createBillingAcct(id, gameId, 11, CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        self.toolBox.scriptOutput("editBillingType - Paypal to CC (Master Billing - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingType(billingId, "1", GAMEURL)
        self.successCheck(resultDict)
        self.assertTrue("flowID" in resultDict['account'], "Flow Id tag not found")
        self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        self.assertTrue("ariaUrl" in resultDict['account'], "Validation Url tag not found")
        self.assertEqual(resultDict['account']['ariaUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        sessionId = resultDict['account']['flowID']
        flowId = resultDict['account']['inSessionID']
        self.ariaHostedPage(sessionId, flowId)
        
    
    def test_validEditBillingTypeForCreditCard(self):
        '''Change billing type (child info) from creditCard to paypal'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingResult = self.toolBox.createBillingAcct(id, gameId, 1, CLIENTIPADDRESS, PLANID, FIRSTNAME, 
                                                             LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl=GAMEURL)
        billingId = billingResult['account']['accountId']
        self.toolBox.scriptOutput("editBillingType - CC to Paypal (Billing - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingType(billingId, "11")
        self.successCheck(resultDict)
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypayResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        
        
    def test_validEditBillingTypeForPaypalWithParentInfo(self):
        '''Change billing type (parent info) from paypal to creditCard'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, "11", CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypayResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.toolBox.scriptOutput("editBillingType - Paypal to CC (Billing - parent info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingType(billingId, "1", GAMEURL)
        self.successCheck(resultDict)
        sessionId = resultDict['account']['flowID']
        flowId = resultDict['account']['inSessionID']
        self.ariaHostedPage(sessionId, flowId)
        
        
    def test_validEditBillingTypeForCreditCardWithParentInfo(self):
        '''Change billing type (parent info) from creditCard to paypal'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, "1", CLIENTIPADDRESS, PLANID, FIRSTNAME, 
                                                             LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("editBillingType - CC to Paypal (Billing - parent info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingType(billingId, "11")
        self.successCheck(resultDict)
            
            
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code'''
        titleCode = "someJunk"
        billingId = self.toolBox.getLatestBillingIdFromDb()
        billingType = "11"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.editBillingType(billingId, billingType, GAMEURL)  
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, billingId, billingType, GAMEURL, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code'''
        titleCode = ""
        billingId = self.toolBox.getLatestBillingIdFromDb()
        billingType = "11"
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.editBillingType(billingId, billingType, GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, billingType, GAMEURL, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service'''
        self.toolBox.setTitleCodeParam(None)  
        billingId = self.toolBox.getLatestBillingIdFromDb()       
        resultDict = self.toolBox.editBillingType(billingId, "1")
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'billingAcctId=' + billingId + '&billingType=1&service=editBillingType', \
                                    'Edit billing type parameter not matching') 
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    ########################
    ###            Helper Methods          ###
    ########################
    
    def ariaHostedPage(self, sessionId, flowId):
        #using selenium to enter credit card information
        sel = self.selenium
        sel.open(r"file://///hq-fs01/dept/Dev/QA/Web/KungFuPandaWorld/Web_Services/DB/Web%20Services%20Test.html")
        sel.select("wsUrl", "label=" + str(self.toolBox.webHost))
        sel.click("//input[@value='set environment']")
        sel.wait_for_page_to_load("30000")
        sel.is_text_present("Current Environment: " + str(self.toolBox.webHost))
        sel.type("ahp_inSessionID", sessionId)
        sel.type("ahp_flowID", flowId)
        sel.click("ahp_submit")
        sel.wait_for_page_to_load("120000")
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
        sel.wait_for_page_to_load("90000")
        self.assertEqual("Gazillion Entertainment", sel.get_title())
        
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
        sel.wait_for_page_to_load("60000")
        #self.assertEqual("Review your information - PayPal", sel.get_title())
        sel.click("continue")
        sel.wait_for_page_to_load("30000")
        #self.assertEqual("Paypal Callback", sel.get_title())
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
                                    
    def parameterValuesCheck(self, resultDict, billingId, billingType, gameUrl, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&billingAcctId=' + billingId + 
                                    '&billingType=' + billingType + '&service=editBillingType' + 
                                    '&gameUrl=' + gameUrl, \
                                    'Edit billing type parameter not matching')
    
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertFalse("errors" in resultDict, "Error XML displayed")
        self.assertTrue("account" in resultDict, "Account tag not found")
        self.assertTrue("accountId" in resultDict['account'], "Account Id tag not found")