from testSuiteBase import TestSuiteBase

from selenium import selenium
import time

PLANID = '10003936'
CLIENTIPADDRESS = '192.168.1.0'
FIRSTNAME = 'firstName'
LASTNAME = 'lastName'
ADDRESS1 = '123addressSt'
CITY = 'cityTown'
STATE = 'CA'
COUNTRY = 'US'
ZIPCODE = '55555'
ADDRESS2 = None
GAMEURL = 'http://gazillion.com'


class CancelPlan(TestSuiteBase):
    

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        self.selenium.close()
        self.selenium.stop()
        
        
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
    
    
    def processPaypal(self, billingAccountId):
        '''Helper function that uses selenium to validate payPal information'''
        startPaypalResult = self.toolBox.startPaypalPlan(billingAccountId)
        paypalToken = startPaypalResult['paypal']['paypalToken']
        startPaypalUrl = startPaypalResult['paypal']['returnUrl'] + paypalToken
        
        #Use selenium to agree to paypalPlan
        sel = self.selenium
        sel.open("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=/")
        sel.click("link=PayPal Sandbox")
        sel.wait_for_page_to_load("30000")
        
        #Log into paypal
        sel.type("login_email", "sharmila.janardhanan@slipg8.com")
        sel.type("login_password", "password")
        sel.click("submit")
        sel.wait_for_page_to_load("30000")
        time.sleep(6)
        sel.open(startPaypalUrl)
        sel.wait_for_page_to_load("30000")
        time.sleep(2)
        
        #Log into sandbox test account
        sel.type("login_email", "sharmi_1263862208_per@slipg8.com")
        sel.type("login_password", "gazillion")
        sel.click("login.x")
        sel.wait_for_page_to_load("30000")
        self.assertEqual("Review your information - PayPal", sel.get_title())
        sel.click("continue")
        sel.wait_for_page_to_load("30000")
        #self.assertEqual("Paypal Callback", sel.get_title())
        time.sleep(1)
        
        finishPaypalResult = self.toolBox.finishPaypalPlan(billingAccountId, paypalToken)
    
    
    def test_cancelPlanWithoutParameters(self):
        '''Cancel plan without parameters using a blank get'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('cancelPlan')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000','Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Billing was cancelled without submitting parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', None, None)
    
    
    def test_cancelPlanInvalidTitleCode(self):
        '''Cancel plan with an invalid titleCode'''
        billingAccountId, inSessionId, flowId = self.createMasterCreditBilling()
        self.ariaHostedPage(inSessionId, flowId)
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('success' in result,\
                        'Billing was cancelled with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', 'NSFW', billingAccountId)
    
    
    def test_cancelPlanBlankTitleCode(self):
        '''Cancel plan with a blank titleCode'''
        billingAccountId, inSessionId, flowId = self.createMasterCreditBilling()
        self.ariaHostedPage(inSessionId, flowId)
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Billing was cancelled with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', '', billingAccountId)
    
    
    def test_cancelPlanWithoutTitleCode(self):
        '''Cancel plan without titleCode'''
        billingAccountId, inSessionId, flowId = self.createMasterCreditBilling()
        self.ariaHostedPage(inSessionId, flowId)
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4000','Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Billing was cancelled without a titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', None, billingAccountId)
    
    
    def test_cancelPlanBlankBillingId(self):
        '''Cancel plan with a blank billingAccountId'''
        result = self.toolBox.cancelPlan('')
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003','Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Billing was cancelled on a blank billingAccountId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', 'KFPW', '')
    
    
    def test_cancelPlanInvalidBillingId(self):
        '''Cancel plan with an invalid billingAccountId'''
        billingAccountId = '0000000'
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '16032','No billing account exists for this user')
        self.assertFalse('success' in result,\
                        'Billing was cancelled on an invalid billingAccountId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'cancelPlan', 'KFPW', billingAccountId)
    
    
    def test_cancelPlanUnverifiedCreditBilling(self):
        '''Cancel plan with an unverified credit billingAccount'''
        billingAccountId, _, _ = self.createMasterCreditBilling()
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
    
    
    def test_cancelPlanUnverifiedPaypalBilling(self):
        '''Cancel plan with an unverified paypal billingAccount'''
        billingAccountId = self.createMasterPaypalBilling()
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
    
    
    def test_cancelPlanValidMasterCreditBillingId(self):
        '''Cancel plan with a valid master credit billingAccountId'''
        billingAccountId, inSessionId, flowId = self.createMasterCreditBilling()
        self.ariaHostedPage(inSessionId, flowId)
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
    
    
    def test_cancelPlanValidMasterPaypalBillingId(self):
        '''Cancel plan with a valid master paypal billingAccountId'''
        billingAccountId = self.createMasterPaypalBilling()
        self.processPaypal(billingAccountId)
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
    
    
    def test_cancelPlanValidChildCreditBillingId(self):
        '''Cancel plan with a valid child credit billingAccountId'''
        billingAccountId, inSessionId, flowId = self.createChildCreditBilling()
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
        self.toolBox.scriptOutput("Cancel plan on a child credit billingAccountId", {"billingAccountId": billingAccountId})
    
    
    def test_cancelPlanValidChildPaypalBillingId(self):
        '''Cancel plan with a valid child paypal billingAccountId'''
        billingAccountId = self.createChildPaypalBilling()
        result = self.toolBox.cancelPlan(billingAccountId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" missing from result')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" missing from value')
        self.toolBox.scriptOutput("Cancel plan on a child paypal billingAccountId", {"billingAccountId": billingAccountId})
    
    
    def createMasterCreditBilling(self):
        '''Helper function that registers, creates master credit billing, and returns info'''
        userName, setupAccount = self.toolBox.registerNewParent()
        
        if 'user' not in setupAccount:
            self.fail('registerNewParent failure!')
        
        gameId = self.toolBox.getGameIdFromUser(userName)
        accountId = setupAccount['user']['id']
        gameAcctId = gameId
        billingType = '1'
        
        setupBilling = self.toolBox.createBillingAcct(accountId, gameAcctId, billingType,\
            CLIENTIPADDRESS, PLANID, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,\
            ADDRESS2, GAMEURL)
        
        if 'account' not in setupBilling:
            print setupBilling
            self.fail('createBillingAcct failure!')
        
        billingAccountId = setupBilling['account']['accountId']
        inSessionId = setupBilling['account']['inSessionID']
        flowId = setupBilling['account']['flowID']
        
        return billingAccountId, inSessionId, flowId
    
    
    def createMasterPaypalBilling(self):
        '''Helper function that registers, creates master paypal billing, and returns info'''
        userName, setupAccount = self.toolBox.registerNewParent()
        
        if 'user' not in setupAccount:
            self.fail('registerNewUsername failure!')
        
        gameId = self.toolBox.getGameIdFromUser(userName)
        accountId = setupAccount['user']['id']
        billingType = '11'
        
        setupBilling = self.toolBox.createBillingAcct(accountId, gameId, billingType,\
            CLIENTIPADDRESS, PLANID)
        
        if 'account' not in setupBilling:
            print setupBilling
            self.fail('createBillingAcct failure!')
        
        billingAccountId = setupBilling['account']['accountId']
        return billingAccountId
    
    
    def createChildCreditBilling(self):
        '''Helper function that registers, creates child credit billing, and returns info'''
        userName, setupAccount = self.toolBox.registerNewUsername()
        
        if 'user' not in setupAccount:
            self.fail('registerNewParent failure!')
        
        gameId = self.toolBox.getGameIdFromUser(userName)
        accountId = setupAccount['user']['id']
        gameAcctId = gameId
        billingType = '1'
        
        setupBilling = self.toolBox.createBillingAcct(accountId, gameAcctId, billingType,\
            CLIENTIPADDRESS, PLANID, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,\
            ADDRESS2, GAMEURL)
        
        if 'account' not in setupBilling:
            print setupBilling
            self.fail('createBillingAcct failure!')
        
        billingAccountId = setupBilling['account']['accountId']
        inSessionId = setupBilling['account']['inSessionID']
        flowId = setupBilling['account']['flowID']
        
        return billingAccountId, inSessionId, flowId
    
    
    def createChildPaypalBilling(self):
        '''Helper function that registers, creates child paypal billing, and returns info'''
        userName, setupAccount = self.toolBox.registerNewUsername()
        
        if 'user' not in setupAccount:
            self.fail('registerNewUsername failure!')
        
        gameId = self.toolBox.getGameIdFromUser(userName)
        accountId = setupAccount['user']['id']
        billingType = '11'
        
        setupBilling = self.toolBox.createBillingAcct(accountId, gameId, billingType,\
            CLIENTIPADDRESS, PLANID)
        
        if 'account' not in setupBilling:
            print setupBilling
            self.fail('createBillingAcct failure!')
        
        billingAccountId = setupBilling['account']['accountId']
        return billingAccountId
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, billingAccountId):
        '''Helper function that checks the error parameters against provided values'''
        errorParameters = self.toolBox.httpParamToDict(errorParameters)
        self.assertTrue(service in errorParameters['service'],\
                        'Service returned as ' + errorParameters['service'] + ' instead of ' + service)
        if titleCode != None:
            self.assertTrue(titleCode in errorParameters['titleCode'],\
                        'TitleCode returned as ' + errorParameters['titleCode'] + ' instead of ' + titleCode)
        if billingAccountId != None:
            self.assertTrue(billingAccountId in errorParameters['billingAccountId'],\
                        'billingAccountId returned as ' + errorParameters['billingAccountId'] + ' instead of ' + billingAccountId)