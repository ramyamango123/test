'''Start Paypal plan Testsuite
Passes Billing Account Id(type - int) to service Start Paypal Plan
This WS starts an Paypal billing agreement
Created by Sharmila Janardhanan Date on 01/07/2010'''

from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, string, time

PLANID = "10003937"
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

class StartPaypalPlan(TestSuiteBase):

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
        resultDict = self.toolBox.blankPost('startPaypalPlan')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=startPaypalPlan', \
                                    'Create Billing Parameters not matched')
        
    
    def test_emptyBillingId(self):
        '''Pass empty billing Id to the service'''
        billingId = ""
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId)
        
    
    def test_invalidBillingId(self):
        '''Pass invalid billing Id to the service'''
        billingId = "invalid"
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId)
                                              
           
    def test_validDefaultStartPaypalPlanForChild(self):
        '''Pass valid billing id to create default paypal plan (for child)'''
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingType = "11"
        billingResult = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID)  
        billingId = billingResult['account']['accountId']
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.successCheck(resultDict)
            
           
    def test_validDefaultStartPaypalPlanForVerifiedAdult(self):
        '''Pass valid billing id to create default paypal plan (for verified adult)'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        billingType = "11"
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS, PLANID)  
        billingId = billingResult['account']['accountId']
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.successCheck(resultDict)
        
        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code'''
        billingId = "1040861"
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, billingId, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code'''
        billingId = "1040861"
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service'''
        billingId = "1040861"
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.startPaypalPlan(billingId)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'billingAcctId=' + billingId + '&service=startPaypalPlan', \
                                    'Start paypal plan Parameters not matching')
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
        sel.wait_for_page_to_load("90000")
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
        sel.click("//a[@onclick='document.forms.Action.submit();']")
        sel.wait_for_page_to_load("90000")
        self.assertEqual("Gazillion Entertainment", sel.get_title())
        
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
    def parameterValuesCheck(self, resultDict, billingId, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&billingAcctId=' + billingId + 
                                    '&service=startPaypalPlan',  'Start Paypal plan Parameters not matched')                              

    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("paypal" in resultDict, "paypal tag not found")
        self.assertEqual(resultDict['paypal']['returnUrl'], "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=", \
                                                "URL address not found")
        self.assertTrue("paypalToken" in resultDict['paypal'])