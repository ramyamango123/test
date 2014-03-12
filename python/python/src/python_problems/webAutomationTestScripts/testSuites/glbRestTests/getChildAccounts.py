#Get Child Accounts(GET)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.22.09.

import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time

CLIENTIPADDRESS = '192.168.1.1'
FIRSTNAME = 'Tester'
LASTNAME = 'Dummy'
ADDRESS1 = '123 Fake Street'
CITY = 'San Mateo'
STATE = 'CA'
COUNTRY = 'US'
ZIPCODE = '94403'
GAMEURL = 'http://gazillion.com'
PLANID = '10003936'

class GetChildAccounts(TestSuiteBase):


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
        #Legacy information - cannot create new accounts.  Accounts taken from 2010-05-17 test run.
        billingId = '2924576'
        childAccountId = '2924579'
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, childAccountId)
        self.toolBox.scriptOutput("getChildAccounts valid test account", {"billingId": billingId, "childAccountId": childAccountId})
        
        
    def test_validInfoLevel1(self):
        '''Valid Level 1 information -- TC2'''
        #Legacy information - cannot create new accounts.  Accounts taken from 2010-05-17 test run.
        billingId = '2924608'
        childAccountId = '2924609'
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, childAccountId, level=1)
        self.toolBox.scriptOutput("getChildAccounts valid level 1 account", {"billingId": billingId, "childAccountId": childAccountId})
        
        
    def test_validPaypalInfo(self):
        '''Valid Paypal information -- TC3'''
        #Legacy information - cannot create new accounts.  Accounts taken from 2010-05-17 test run.
        billingId = '2924589'
        childAccountId = '2924593'
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, childAccountId)
        self.toolBox.scriptOutput("getChildAccounts valid paypal account", {"billingId": billingId, "childAccountId": childAccountId})
        
    # Legacy account creation is no longer supported, so no need to run this test    
    # def test_validPaypalInfoLevel1(self):
        # '''Valid Level 1 Paypal information -- TC4'''
        # #Legacy information - cannot create new accounts.  Accounts taken from 2010-05-17 test run.
        # billingId = '2924594'
        # childAccountId = '2924598'
        # result = self.toolBox.getChildAccounts(billingId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # self.successCheck(result)
        # self.infoSuccessCheck(result, childAccountId, level=1)
        # self.toolBox.scriptOutput("getChildAccounts valid paypal level 1 account", {"billingId": billingId, "childAccountId": childAccountId})
        
        
    def test_validInfoNoAccount(self):
        '''Valid information, no childAccounts to get -- TC5'''
        billingId = self.validAccountNoChildBilling()
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))   
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, billingId)           
        self.toolBox.scriptOutput("getChildAccounts no child account", {"billingId": billingId})
    
    
    def test_missingParams(self):
        '''Missing information -- TC6'''
        result = self.toolBox.blankGet('getChildAccounts')

        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values -- TC7'''
        result = self.toolBox.getChildAccounts('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
    
    
    def test_invalidInfo(self):
        '''Invalid account id -- TC8'''
        result = self.toolBox.getChildAccounts('00000000000000000')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, '00000000000000000')
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC9'''
        billingId = '2924589'
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, billingId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC10'''
        billingId = '2924589'
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, billingId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
    
    def test_withoutTitleCode(self):
        '''Without a Title Code -- TC11'''
        billingId = '2924589'
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getChildAccounts(billingId)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, billingId, None)
        self.toolBox.setTitleCodeParam('KFPW')

        
    def validAccountNoChildBilling(self):
        '''Registers a master billing account for the no child test'''
        username, parentResult = self.toolBox.registerNewParent()
        self.assertTrue('user' in parentResult, "XML from register does not contain user")
        id = parentResult['user']['id']
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANID,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, "createMasterBilling failed: " + str(result))
        masterBillingAcctId = result['account']['accountId']
        sessionId = result['account']['inSessionID']
        flowId = result['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
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
        self.assertTrue('listChild' in result, "XML structure failed, no listChild")
        self.assertTrue('child' in result['listChild'], "XML structure failed, no child in listChild")
        self.assertTrue('acctId' in result['listChild']['child'], "XML structure failed, no acctId in individual child tag")
        self.assertTrue('acctStatus' in result['listChild']['child'], "XML structure failed, no acctStatus in individual child tag")
        self.assertTrue('acctLevel' in result['listChild']['child'], "XML structure failed, no acctLevel in individual child tag")
        self.assertFalse('errors' in result, "Errors tag found in result XML")
    
    
    def infoSuccessCheck(self, result, childAccountId, level=2) :
        '''Checks for success case XML information returned'''
        self.assertTrue(childAccountId == result['listChild']['child']['acctId'], "Child Account Id returned not child account Id given: " + str(result))
        self.assertTrue(result['listChild']['child']['acctStatus'] == '1', "Account status returned was not 1: " + str(result))
        if level == 2:
            self.assertTrue(result['listChild']['child']['acctLevel'] == '2', "Account level returned was not 2: " + str(result))
        else:
            self.assertTrue(result['listChild']['child']['acctLevel'] == '1', "Account level returned was not 1: " + str(result))
        
    
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('user' in result, "XML structure failed, user found in error XML")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, billingAcctId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['masterBillingAcctId'] == billingAcctId, "BillingAcctId returned not equal to billingAcctId given: " + billingAcctId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getChildAccounts", "Service returned not equal to service called: getChildAccounts " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))