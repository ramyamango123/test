#Change Plan (Post)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.03.09.

import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time

STATICPLANID = '10003936'
PLANIDREGISTERED = '10003937'
YEARPLANID = '10003938'
MASTERPLANID = '10003939'
FIRSTNAME = 'Tester'
LASTNAME = 'Dummy'
ADDRESS1 = '123 Fake Street'
CITY = 'San Mateo'
STATE = 'CA'
COUNTRY = 'US'
ZIPCODE = '94403'
CLIENTIPADDRESS = '192.168.1.1'
GAMEURL = 'http://gazillion.com'

class ChangePlan(TestSuiteBase):


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
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('success' in result, "No success found")
        self.assertTrue('value' in result['success'], "No value found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['success']['value'], "value wasn't True")
        self.toolBox.scriptOutput("changePlan valid account", {"billingId": billingId, "plan id": STATICPLANID})
        
        
    def test_validParentInfo(self):
        '''Valid Parent information -- TC2'''
        billingId = self.validParentAccountCreation()
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('success' in result, "No success found")
        self.assertTrue('value' in result['success'], "No value found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['success']['value'], "value wasn't True")
        self.toolBox.scriptOutput("changePlan valid account", {"billingId": billingId, "plan id": STATICPLANID})
        
        
       
    def test_validPaypalInfo(self):
        '''Valid Paypal information -- TC3'''
        billingId = self.validPaypalAccountCreation()
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('success' in result, "No success found")
        self.assertTrue('value' in result['success'], "No value found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['success']['value'], "value wasn't True")
        self.toolBox.scriptOutput("changePlan valid paypal account", {"billingId": billingId, "plan id": STATICPLANID})
        
        
    def test_validParentPaypalInfo(self):
        '''Valid Parent Paypal information -- TC4'''
        billingId = self.validParentPaypalAccountCreation()
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('success' in result, "No success found")
        self.assertTrue('value' in result['success'], "No value found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['success']['value'], "value wasn't True")
        self.toolBox.scriptOutput("changePlan valid paypal account", {"billingId": billingId, "plan id": STATICPLANID})
        
    def test_missingParams(self):
        '''Missing information -- TC5'''
        result = self.toolBox.blankPost('changePlan')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingBillingId(self):
        '''Missing information - billingId -- TC6'''
        result = self.toolBox.blankPost('changePlan', {'planId': STATICPLANID})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingPlanId(self):
        '''Missing information - planId -- TC7'''
        billingId = self.validAccountCreation()
        result = self.toolBox.blankPost('changePlan', {'billingId': billingId})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyAccountId(self):
        '''Empty AccountId -- TC8'''
        result = self.toolBox.changePlan('', STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', STATICPLANID)
        
        
    def test_emptyPlanId(self):
        '''Empty planId -- TC9'''
        billingId = self.validAccountCreation()
        result = self.toolBox.changePlan(billingId, '')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, billingId, '')
        
        
    def test_invalidAccountId(self):
        '''Invalid AccountId -- TC10'''
        result = self.toolBox.changePlan('asdfasdfasdf', STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['No billing account exists for this user', '16032'])
        self.infoFailCheck(result, 'asdfasdfasdf', STATICPLANID)
        
        
    def test_invalidPlanId(self):
        '''Invalid planId -- TC11'''
        billingId = self.validAccountCreation()
        result = self.toolBox.changePlan(billingId, '-1')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, billingId, '-1')
        
    
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC12'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, billingId, STATICPLANID, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC13'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, billingId, STATICPLANID, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_withoutTitleCode(self):
        '''Without a Title Code -- TC14'''
        billingId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.changePlan(billingId, STATICPLANID)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Not enough parameters to satisfy request", '4000'])
        self.infoFailCheck(result, billingId, STATICPLANID, None)
        self.toolBox.setTitleCodeParam('KFPW')
    
       
    def validAccountCreation(self):
        '''Registers an account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, result)
        return result['account']['accountId']
        
        
    def validParentAccountCreation(self):
        '''Registers a parent account for the valid info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED,firstName=FIRSTNAME,lastName=LASTNAME,
                                                      address1=ADDRESS1,city=CITY,state=STATE,country=COUNTRY,zipCode=ZIPCODE,gameUrl=GAMEURL)
        self.assertTrue('account' in result, result)
        return result['account']['accountId']
        
    
    def validPaypalAccountCreation(self):
        '''Registers a paypal account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED)
        self.assertTrue('account' in result, result)
        return result['account']['accountId']
        
        
    def validParentPaypalAccountCreation(self):
        '''Registers a parent paypal account for the valid info test'''
        username, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        billingType = '11'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,CLIENTIPADDRESS,PLANIDREGISTERED)
        self.assertTrue('account' in result, result)
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
        sel.click("//a[@onclick='document.forms.Action.submit();']")
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
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('success' in result, "XML structure failed, success in error return")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
    
    
    def infoFailCheck(self, result, billingAcctId, planId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['billingAcctId'] == billingAcctId, "billingAcctId returned not equal to billingAcctId given: " + billingAcctId + " " + str(parameters))
        self.assertTrue(parameters['planId'] == planId, "planId returned not equal to planId given: " + planId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "changePlan", "Service returned not equal to service called: changePlan " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))