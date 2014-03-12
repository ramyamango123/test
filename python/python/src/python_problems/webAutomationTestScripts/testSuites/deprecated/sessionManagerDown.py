#Session Manager Down
#Includes all web services with the "Session Manager Unavailable" error code.
#Created by Tarja Rechsteiner on 2.10.10.

import sys
import string
import random
import re

from testSuiteBase import TestSuiteBase

#UserId for an account logged in before the Session Manager was taken down.  Needed for startGameSession.
STATICUSERID = '000'

class SessionManagerDown(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
    def test_gameSchedule(self):
        '''Test for gameSchedule'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        parameters = {'userId':userId, 'childUserId':childUserId, 'allowedPlayTimes':allowedPlayTimes, 'timeZone':"US/Pacific", 'service':'gameSchedule', 'titleCode':'KFPW'}
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_login(self):
        '''Test for login'''
        username, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.login(username, 'password')
        parameters = {'username':username, 'password':'password', 'service':'login', 'titleCode':'KFPW'}
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_parentLogin(self):
        '''Test for parentLogin'''
        username, _ = self.toolBox.registerNewParent()
        result = self.toolBox.parentLogin(username, 'password')
        parameters = {'username':username, 'password':'password', 'service':'parentLogin', 'titleCode':'KFPW'}
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_logout(self):
        '''Test for logout'''
        _, regResult = self.toolBox.registerNewUsername()
        userId = regResult['user']['id']
        result = self.toolBox.logout(userId)
        parameters = {'userId':userId, 'service':'logout', 'titleCode':'KFPW'}
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_startGameSession(self):
        '''Test for startGameSession'''
        result = self.toolBox.startGameSession(STATICUSERID, '10')
        parameters = {'userId':STATICUSERID, 'service':'startGameSession', 'playTime':'10', 'titleCode':'KFPW'}
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_validateCode(self):
        '''Test for validateCode'''
        self.fail("Passing with Session Manager down.  Bug 844")
        _, regResult = self.toolBox.registerNewUsername()
        userId = regResult['user']['gameUserId']
        result = self.toolBox.validateCode("JUSTTIME", userId)
        parameters = {'userId':userId, 'code':"JUSTTIME", 'service':'validateCode', 'titleCode':'KFPW'}
        print result
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
    
    def test_redeemCode(self):
        '''Test for redeemCode'''
        self.fail("Passing with Session Manager down.  Bug 844")
        _, regResult = self.toolBox.registerNewUsername()
        userId = regResult['user']['gameUserId']
        result = self.toolBox.redeemCode(userId, "JUSTTIME")
        parameters = {'userId':userId, 'code':"JUSTTIME", 'service':'redeemCode', 'titleCode':'KFPW'}
        print result
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The Session Manager service is not available', '12001'], parameters)
        
        
    def test_chatSetting(self):
        '''Test for chatSetting'''
        _, parentResult = self.toolBox.registerNewParent()
        parentId = parentResult['user']['id']
        childId = parentResult['user']['childAccounts']['userBrief']['id']
        result = self.toolBox.chatSetting('FULL', parentId, childUserId = childId)
        parameters = {'userId':parentId, 'childUserId':childId, 'chatType':'FULL', 'service':'chatSetting', 'titleCode':'KFPW'}
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['The mute status could not be set by the session manager', '12006'], parameters)
        
        
    def failureCheck(self, result, expected, expectParams):
        self.assertTrue('errors' in result, "XML structure failed, no errors tag")
        self.assertTrue('error' in result['errors'], "XML strucutre failed, no error tag")
        
        innerResult = result['errors']['error']
        self.assertTrue('code' in innerResult, "XML structure failed, no code")
        self.assertTrue('message' in innerResult, "XML structure failed, no message")
        self.assertTrue('parameters' in innerResult, "XML structure failed, parameters")
        self.assertFalse('user' in innerResult, "Fail case returned a success structure")
        self.assertEqual(innerResult['message'], expected[0], "Expected error message not found.  Found: " + str(innerResult))
        self.assertEqual(innerResult['code'], expected[1], "Expected error code not found.  Found: " + str(innerResult))
        
        resultParams = self.toolBox.httpParamToDict(innerResult['parameters'])
        for key in expectParams:
            self.assertTrue(key in resultParams, "Expected parameter missing: " + str(result))
            self.assertTrue(resultParams[key] == expectParams[key], "Expected parameter not equal to returned parameter: " + str(result))
        for key in resultParams:
            if key in expectParams:
                pass
            else:
                self.fail("Unexpected key returned: " + str(result))