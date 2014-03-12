'''Validate Session Testsuite(GET)
Dependency on Login and StartGameSession 
Includes both positive and negative test cases.
Created by Ramya Nagendra on 04.07.2010.'''


import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import time
import MySQLdb


class ValidateSession(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", \
		                         "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()


    def test_ValidateWithValidGameAcctId (self):
        ''' Pass valid  game account id  without subscription(Positive TC 1) - TC1 '''
        username, gameAcctId = self.ValidChildAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        
        freeplayStatusMessage = "CAN_PLAY_FREE"
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('status' in resultDict['session'],\
                        'Expected "status" tag not present')
        self.assertTrue('session' in resultDict,\
                        'Expected "session" tag not present')
        self.assertEqual(resultDict['session']['status'], freeplayStatusMessage , \
                        'Free play Status message not matched')  
        
         
    def test_validateSubscribedUser(self):
        '''Pass valid game account id along with subscription (Positive TC 2) - TC2'''
        username, gameAcctId = self.validBillingAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        
        SubcribedUserStatusMessage = "CAN_PLAY"      
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('session' in resultDict,\
                        'Expected "session" tag not present')
        self.assertTrue('status' in resultDict['session'],\
                        'Expected "status" tag not present')
        self.assertEqual(resultDict['session']['status'], SubcribedUserStatusMessage, \
                        'Subscribed User Status message not matched') 
        

    def test_validateWithValidCouponCodeTimeOnly(self):
        '''Validate game session by redeeming coupon code (time only - justtime),Positive TC3 - TC3'''
        username, gameAcctId = self.ValidChildAccountCreation()
        couponCode = "justtime"
        
        resultDict = self.toolBox.redeemCode(gameAcctId, couponCode)
        self.assertEqual(resultDict['session']['type'], 'justtime', 'Key type not matched')
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        CodeRedeemStatusMessage = "CAN_PLAY"
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        #structure check
        self.assertTrue('status' in resultDict['session'],\
                        'Expected "status" tag not present')
        self.assertTrue('session' in resultDict,\
                        'Expected "session" tag not present')
        self.assertEqual(resultDict['session']['status'], CodeRedeemStatusMessage , \
                        'Coupon redeem Status message not matched')  
        
        
    def test_expiredSession(self):
        '''Pass user which has expired play session - TC4'''
        username, gameAcctId = self.ValidChildAccountCreation()
        self.getExpiredSessionGameAccountIdFromDb(gameAcctId)
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.errorXMLStructureCodeCheck(resultDict, '12002', \
                                  		'The Session Manager indicates that there is no playtime available')
        self.parameterValuesCheck(resultDict, gameAcctId)
        
                        
    def test_validateMultipleTimesWithoutGameSessionActive(self):
        ''' Validate session multiple times without starting GameSession service - TC5'''
        username, gameAcctId = self.ValidChildAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        CodeRedeemStatusMessage = "CAN_PLAY_FREE"
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertTrue(resultDict.httpStatus() == 200,\
                        "http status code: " + str(resultDict.httpStatus()))    
        #structure check
        self.assertTrue('status' in resultDict['session'],\
                        'Expected "status" tag not present')
        self.assertTrue('session' in resultDict,\
                        'Expected "session" tag not present')
        self.assertEqual(resultDict['session']['status'], CodeRedeemStatusMessage, \
                        'Coupon redeem Status message not matched')  
        CodeRedeemStatusMessage1 = "CAN_PLAY_SPONSORED"
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertTrue('status' in resultDict['session'],\
                        'Expected "status" tag not present')
        self.assertTrue('session' in resultDict,\
                        'Expected "session" tag not present')
        self.assertEqual(resultDict['session']['status'], CodeRedeemStatusMessage1, \
                        'Coupon redeem Status message not matched') 
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.errorXMLStructureCodeCheck(resultDict, '12002', \
                                  		'The Session Manager indicates that there is no playtime available')
        self.parameterValuesCheck(resultDict, gameAcctId)
        
        
    def test_validateWithoutLoginAndGameSession(self):
        ''' Validating session without logging in and without starting GameSession service - TC6 '''
        username, gameAcctId = self.ValidChildAccountCreation()
		
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.errorXMLStructureCodeCheck(resultDict, '12002', \
		                                'The Session Manager indicates that there is no playtime available')
        self.parameterValuesCheck(resultDict, gameAcctId)
        
   
    def test_passInvalidUserId(self):
        '''Pass invalid userid to the service - TC7'''
        gameAcctId = 'Invalid'  
        resultDict = self.toolBox.validateSession(gameAcctId)

        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '17003', \
		                                'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameAcctId)
        
     
    def test_passEmptyValues(self):
        ''' Pass all empty values to the service - TC8 '''
        gameAcctId = ''  
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, gameAcctId)
       
       
    def test_validateBannedUserId(self):
        '''Validating banned user - TC9'''
        _, parentResult = self.toolBox.registerNewParent()
        self.assertTrue('user' in parentResult, "XML from register does not contain user")
        parentId = parentResult['user']['id']
        childId = parentResult['user']['childAccounts']['userBrief']['id']
        childUsername = parentResult['user']['childAccounts']['userBrief']['username']
        gameAcctId = self.toolBox.getGameIdFromUser(childUsername)
                       
        loginResult = self.toolBox.login(childUsername, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        banResult = self.toolBox.banChildAccount(parentId, childId)
        self.assertTrue('success' in banResult, "XML from banChildAccount did not contain success")
        
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))  
        self.errorXMLStructureCodeCheck(resultDict, '11004', \
                                  		'Login Success - Account is currently Banned')
        self.parameterValuesCheck(resultDict, gameAcctId)
        
        
              
    def test_noParametersPassed(self):
        ''' No parameters passed to the Web Services function - TC10 '''
        resultDict = self.toolBox.blankGet('validateSession')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=validateSession', \
                                    'Validate Session Parameters not matching')
 
    def test_notMatchingTitleCode(self):
        ''' Pass not matching title code - TC11 '''
        username, gameAcctId = self.ValidChildAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
		
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        titleCode = "junkvalue"
        self.toolBox.setTitleCodeParam(titleCode) 
		
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, gameAcctId, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC12'''
        username, gameAcctId = self.ValidChildAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
        
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)   
		
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict,gameAcctId, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC13'''
        username, gameAcctId = self.ValidChildAccountCreation()
        loginResult = self.toolBox.login(username, 'password')
        self.assertFalse("errors" in loginResult, "Login failed")
		
        result = self.toolBox.startGameSession(gameAcctId)
        self.assertTrue('session' in result, "Session not found")
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
		
        resultDict = self.toolBox.validateSession(gameAcctId)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'userId=' + gameAcctId + '&service=validateSession', \
                                    'Validate Session Parameters not matching')
   
        self.toolBox.setTitleCodeParam('KFPW') 
		
       # Helper methods ##
		
    def ValidChildAccountCreation(self):
        '''Registers a valid child account'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        return username, gameAcctId
    
           
    def validBillingAccountCreation(self):
        '''Registers an account with Subscription'''
        username, regResultDict = self.toolBox.registerNewUsername()
        self.assertTrue('user' in regResultDict, "XML from register does not contain user")
        childId = regResultDict['user']['id']
        childGameId = regResultDict['user']['gameUserId']
        firstName = 'Tester'
        lastName = 'Dummy'
        billingType = '1'
        address1 = '123 Fake Street'
        city = 'San Mateo'
        state = 'CA'
        country = 'US'
        zipCode = '94403'
        planId = '10003939'
        gameUrl = 'http://gazillion.com'
        clientIpAddress = '192.168.1.1'
        result = self.toolBox.createBillingAcct(childId, childGameId, billingType, clientIpAddress, planId, firstName=firstName, lastName=lastName,
                                                      address1=address1, city=city, state=state, country=country, zipCode=zipCode, gameUrl=gameUrl)
        self.assertTrue('account' in result, result)
        sessionId = result['account']['inSessionID']
        flowId = result['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        return username, childGameId
        
     
        
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
        sel.click("submitButton")
        sel.wait_for_page_to_load("90000")
    
             
    def errorXMLStructureCodeCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
        
        
    def parameterValuesCheck(self, resultDict, gameId, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode +  '&userId=' + gameId + '&service=validateSession',
                                    'Validate Session Parameters not matching') 
     
        
    def getExpiredSessionGameAccountIdFromDb(self, gameAcctId):
        '''return Game Acct Id for the expired session user'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''UPDATE play_session
                          SET expires = '2010-01-01 00:00:00'
                          WHERE account_title_id = %s'''%(gameAcctId))
           
        
        
 