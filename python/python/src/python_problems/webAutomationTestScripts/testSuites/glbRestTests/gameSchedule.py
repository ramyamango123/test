#Change Game Schedule (POST)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.30.09.

import sys
import string
import random
import re

from testSuiteBase import TestSuiteBase

class GameSchedule(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
        
    def test_validInfo(self):
        '''Valid info'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        #this is the same as sending ''
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.assertTrue("success" in result, "No success in result: " + str(result))
        self.assertTrue("value" in result['success'], "No value in success: " + str(result))
        self.assertTrue(result['success']['value'] == 'TRUE', "value != TRUE: " + str(result))
        self.assertFalse("errors" in result, "Errors appeared in result: " + str(result))
        self.toolBox.scriptOutput("gameSchedule valid account", {"childUserId": childUserId, "allowedPlayTimes sent": allowedPlayTimes})
        
        
    def test_invalidInterval(self):
        '''invalid interval XML (no begin/end)'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes><interval><weekday>1</weekday></interval></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["The Play Schedule XML is invalid", "12003"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        self.toolBox.scriptOutput("gameSchedule valid interval", {"childUserId": childUserId, "allowedPlayTimes sent": allowedPlayTimes})
        
        
    def test_invalidWeekday(self):
        '''invalid weekday'''
        #weekdays must be 1-7 for ISO standards.  Tests that 0 is not accepted
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes><interval><weekday>0</weekday><begin>0</begin><end>30</end></interval></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["A weekday provided in the XML is not valid", "12004"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_validBeginEnd(self):
        '''Valid begin/end range in the interval XML'''
        #using weekday 7 to ensure ISO standards
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes><interval><weekday>7</weekday><begin>10</begin><end>1200</end></interval></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.assertTrue("success" in result, "No success in result: " + str(result))
        self.assertTrue("value" in result['success'], "No value in success: " + str(result))
        self.assertTrue(result['success']['value'] == 'TRUE', "value != TRUE: " + str(result))
        self.assertFalse("errors" in result, "Errors appeared in result: " + str(result))   
        self.toolBox.scriptOutput("gameSchedule interval account", {"childUserId": childUserId, "allowedPlayTimes sent": allowedPlayTimes})        
        
        
    def test_missingInfo(self):
        '''Missing parameters'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('gameSchedule')
        
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_invalidCode(self):
        '''Invalid title code'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", "17002"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific", 'somejunk')
        
        
    def test_emptyCode(self):
        '''Empty title code'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", "4003"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific", '')
        
        
    def test_missingCode(self):
        '''Without a title code'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific", None)
        
        
    def test_missingUserId(self):
        '''Without a userId'''
        _, parentDict = self.toolBox.registerNewParent()
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.blankPost('gameSchedule', {'childUserId': childUserId, 'allowedPlayTimes': allowedPlayTimes, 'timeZone': "US/Pacific"})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingChildUserId(self):
        '''Without a childUserId'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.blankPost('gameSchedule', {'userId': userId, 'allowedPlayTimes': allowedPlayTimes, 'timeZone': "US/Pacific"})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingPlayTimes(self):
        '''Without allowedPlayTimes'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        result = self.toolBox.blankPost('gameSchedule', {'userId': userId, 'childUserId': childUserId, 'timeZone': "US/Pacific"})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Not enough parameters to satisfy request", "4000"])
        
        
    def test_missingTimeZone(self):
        '''Without timeZone'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.blankPost('gameSchedule', {'userId': userId, 'childUserId': childUserId, 'allowedPlayTimes': allowedPlayTimes})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_invalidParentId(self):
        '''Unregistered parent id'''
        _, childDict = self.toolBox.registerNewUsername()
        childUserId = childDict['user']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule('99999999999999999', childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, '99999999999999999', childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_invalidChildId(self):
        '''Unregistered child id'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, '9999999999999999999', allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Child Id does not match any records', '17004'])
        self.infoFailCheck(result, userId, '9999999999999999999', allowedPlayTimes, "US/Pacific")
        
        
    def test_invalidAllowedPlayTimes(self):
        '''Invalid allowedPlayTimes'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "asdf"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['The Play Schedule XML is invalid', '12003'])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_invalidTimeZone(self):
        '''Invalid timeZone'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "asdf")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['The Timezone is invalid', '12007'])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "asdf")
        
        
    def test_parentChildNotAssociated(self):
        '''Unassociated parent and child'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        _, childDict = self.toolBox.registerNewUsername()
        childUserId = childDict['user']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parent Id and Child Id are not associated', '17005'])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_emptyUserId(self):
        '''Empty userId'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = ''
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", "4003"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_emptyChildUserId(self):
        '''Empty childUserId'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = ''
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", "4003"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "US/Pacific")
        
        
    def test_emptyTimeZone(self):
        '''Empty timeZone'''
        _, parentDict = self.toolBox.registerNewParent()
        userId = parentDict['user']['id']
        childUserId = parentDict['user']['childAccounts']['userBrief']['id']
        allowedPlayTimes = "<allowedPlayTimes></allowedPlayTimes>"
        result = self.toolBox.gameSchedule(userId, childUserId, allowedPlayTimes, "")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", "4003"])
        self.infoFailCheck(result, userId, childUserId, allowedPlayTimes, "")
        
        
    def failureCheck(self, result, expected) :
        '''Checks for failure cases in the XML structure'''
        # Check for error xml structure
        innerResult = result['errors']['error']
        self.assertTrue('code' in innerResult, "XML structure failed, no code")
        self.assertTrue('message' in innerResult, "XML structure failed, no message")
        self.assertTrue('parameters' in innerResult, "XML structure failed, parameters")
        self.assertFalse('user' in innerResult, "Fail case returned a success structure")
        self.assertEqual(innerResult['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(innerResult['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, userId, childUserId, allowedPlayTimes, timeZone, titleCode='KFPW'):
        '''Checks the information in the parameters return is accurate'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs: " + str(result))
        self.assertTrue(parameters['userId'] == userId, "userId returned not equal to userId given: " + userId + " " + str(parameters))
        self.assertTrue(parameters['childUserId'] == childUserId, "childUserId returned not equal to childUserId given: " + childUserId + " " + str(parameters))
        self.assertTrue(parameters['allowedPlayTimes'] == allowedPlayTimes, "allowedPlayTimes returned not equal to allowedPlayTimes given: " + allowedPlayTimes + " " + str(parameters))
        self.assertTrue(parameters['timeZone'] == timeZone, "timeZone returned not equal to timeZone given: " + timeZone + " " + str(parameters))
        self.assertTrue(parameters['service'] == "gameSchedule", "Service returned not equal to service called: gameSchedule " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))