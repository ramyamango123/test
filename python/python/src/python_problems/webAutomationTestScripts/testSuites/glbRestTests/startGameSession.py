#Start Game Session Service (Post)
#Dependency on Login
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.01.09.

import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time

class StartGameSession(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()


    def test_validInfo(self):
        '''Valid information'''
        username, userId = self.validAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(userId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('session' in result, "No session found")
        self.assertTrue('status' in result['session'], "No status found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['session']['status'] == 'CAN_PLAY_FREE', "status wasn't True: " + str(result))
        
        
    def test_validInfoSubscription(self):
        '''Valid information with subscription'''
        username, userId = self.validSubscriptionCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(userId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('session' in result, "No session found")
        self.assertTrue('status' in result['session'], "No status found")
        self.assertFalse('errors' in result, "Errors found in valid case")
        
        #values check
        self.assertTrue(result['session']['status'] == 'CAN_PLAY', "status wasn't True: " + str(result))
    
    
    def test_missingParams(self):
        '''Missing information'''
        result = self.toolBox.blankPost('startGameSession')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values'''
        result = self.toolBox.startGameSession('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
        
        
    def test_emptyUserId(self):
        '''Empty userId'''
        result = self.toolBox.startGameSession('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '')
        
        
    def test_invalidUserId(self):
        '''Invalid userId'''
        result = self.toolBox.startGameSession('0')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, '0')
        
        
    def test_bannedUserId(self):
        '''Banned user'''
        _, parentResult = self.toolBox.registerNewParent()
        self.assertTrue('user' in parentResult, "XML from register does not contain user")
        parentId = parentResult['user']['id']
        childId = parentResult['user']['childAccounts']['userBrief']['id']
        childUsername = parentResult['user']['childAccounts']['userBrief']['username']
        banResult = self.toolBox.banChildAccount(parentId, childId)
        self.assertTrue('success' in banResult, "XML from banChildAccount did not contain success")
        gameAcctId = self.toolBox.getGameIdFromUser(childUsername)
        loginResult = self.toolBox.login(childUsername, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(gameAcctId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Login Success - Account is currently Banned', '11004'])
        self.infoFailCheck(result, gameAcctId)
        

    def test_invalidTitleCode(self):
        '''Invalid title code'''
        username, userId = self.validAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        username, userId = self.validAccountCreation()
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.startGameSession(userId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, userId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code'''
        username, userId = self.validAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.startGameSession(userId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, userId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_withoutTitleCode(self):
        '''Without a Title Code'''
        username, userId = self.validAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.startGameSession(userId)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Not enough parameters to satisfy request", '4000'])
        self.infoFailCheck(result, userId, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def validAccountCreation(self):
        '''Registers an account for the valid info test'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        return username, gameAcctId
        
        
    def validSubscriptionCreation(self):
        '''Registers an account with a subscription'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result['user']['id']
        firstName = 'Tester'
        lastName = 'Dummy'
        billingType = '1'
        address1 = '123 Fake Street'
        city = 'San Mateo'
        state = 'CA'
        country = 'US'
        zipCode = '94403'
        gameUrl = 'http://gazillion.com'
        clientIpAddress = '192.168.1.1'
        result = self.toolBox.createBillingAcct(id,gameAcctId,billingType,clientIpAddress,planId='10003936',firstName=firstName,lastName=lastName,
                                                      address1=address1,city=city,state=state,country=country,zipCode=zipCode,gameUrl=gameUrl)
        self.assertTrue('account' in result, result)
        sessionId = result['account']['inSessionID']
        flowId = result['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return username, gameAcctId
        
        
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
    

    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('success' in result, "XML structure failed, success in result")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
    
    
    def infoFailCheck(self, result, userId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like.  Doc is suspect
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['userId'] == userId, "userId returned not equal to userId given: " + userId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "startGameSession", "Service returned not equal to service called: startGameSession " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))