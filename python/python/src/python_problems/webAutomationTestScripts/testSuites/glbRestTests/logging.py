#Logging (Post)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 1.13.10.

import sys

from testSuiteBase import TestSuiteBase

class Logging(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()


    def test_validInfo(self):
        '''Valid information -- TC1'''
        numOfRowsBefore = self.toolBox.getActivityLogCountFromDb(1208)
        result = self.toolBox.log('1208')
        numOfRowsAfter = self.toolBox.getActivityLogCountFromDb(1208)
        
        self.assertFalse('errors' in result, "Errors found in result: " + str(result))
        self.assertTrue('success' in result, "Success not found in result: " + str(result))
        self.assertTrue('value' in result['success'], "value not found in result: " + str(result))
        self.assertTrue(result['success']['value'], "value returned was not true: " + str(result))
        self.assertTrue(numOfRowsAfter == numOfRowsBefore + 1, "New row not found in DB, Difference: " + str(numOfRowsAfter - numOfRowsBefore))
        
        
    def test_validInfoWithCustomData(self):
        '''Valid Info with customData -- TC2'''
        activityLogDataBefore = self.toolBox.getActivityLogDataCountFromDb('titleAccountId', 14)
        activityLogBefore = self.toolBox.getActivityLogCountFromDb(1208)
        result = self.toolBox.log('1208', customData={'customData': {'titleAccountId':'14'}})
        activityLogAfter = self.toolBox.getActivityLogCountFromDb(1208)
        activityLogDataAfter = self.toolBox.getActivityLogDataCountFromDb('titleAccountId', 14)

        self.assertFalse('errors' in result, "Errors found in result: " + str(result))
        self.assertTrue('success' in result, "Success not found in result: " + str(result))
        self.assertTrue('value' in result['success'], "value not found in result: " + str(result))
        self.assertTrue(result['success']['value'], "value returned was not true: " + str(result))
        self.assertTrue(activityLogAfter == activityLogBefore + 1, "New row not found in activity_log, Difference: " + str(activityLogAfter - activityLogBefore))
        self.assertTrue(activityLogDataAfter == activityLogDataBefore + 1, "New row not found in activity_log_data, Difference: " + str(activityLogDataAfter - activityLogDataBefore))
        
        
    def test_validInfoWithAccountId(self):
        '''Valid info with accountId -- TC3'''
        _, userResult = self.toolBox.registerNewUsername()
        accountId = userResult['user']['id']
        activityLogBefore = self.toolBox.getActivityLogFromDb(accountId, 1208)
        result = self.toolBox.log('1208', accountId=accountId)
        activityLogAfter = self.toolBox.getActivityLogFromDb(accountId, 1208)
        
        self.assertFalse('errors' in result, "Errors found in result: " + str(result))
        self.assertTrue('success' in result, "Success not found in result: " + str(result))
        self.assertTrue('value' in result['success'], "value not found in result: " + str(result))
        self.assertTrue(result['success']['value'], "value returned was not true: " + str(result))
        self.assertTrue(activityLogAfter == activityLogBefore + 1, "New row not found in activity_log, Difference: " + str(activityLogAfter - activityLogBefore))
        
        
    def test_validInfoWithAllOptionals(self):
        '''Valid Info with accountId and customData -- TC4'''
        _, userResult = self.toolBox.registerNewUsername()
        accountId = userResult['user']['id']
        activityLogDataBefore = self.toolBox.getActivityLogDataCountFromDb('titleAccountId', 14)
        activityLogBefore = self.toolBox.getActivityLogFromDb(accountId, 1208)
        result = self.toolBox.log('1208', accountId=accountId, customData={'customData': {'titleAccountId':'14'}})
        activityLogAfter = self.toolBox.getActivityLogFromDb(accountId, 1208)
        activityLogDataAfter = self.toolBox.getActivityLogDataCountFromDb('titleAccountId', 14)

        self.assertFalse('errors' in result, "Errors found in result: " + str(result))
        self.assertTrue('success' in result, "Success not found in result: " + str(result))
        self.assertTrue('value' in result['success'], "value not found in result: " + str(result))
        self.assertTrue(result['success']['value'], "value returned was not true: " + str(result))
        self.assertTrue(activityLogAfter == activityLogBefore + 1, "New row not found in activity_log, Difference: " + str(activityLogAfter - activityLogBefore))
        self.assertTrue(activityLogDataAfter == activityLogDataBefore + 1, "New row not found in activity_log_data, Difference: " + str(activityLogDataAfter - activityLogDataBefore))
        
        
    def test_missingInformation(self):
        '''no parameters passed -- TC5 (Bug 759)'''
        self.fail("Bug 759")
        print self.toolBox.blankPost('log')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingTitleCode(self):
        '''missing titleCode -- TC6 (Bug 768)'''
        self.fail("Bug 768")
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.log('1208')
        print result
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, '1208', titleCode=None)
        
        
    def test_invalidTypeId(self):
        '''Invalid typeId -- TC7'''
        result = self.toolBox.log('a')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        self.infoFailCheck(result, 'a')
        
        
    def test_invalidAccountId(self):
        '''Invalid accountId -- TC8'''
        result = self.toolBox.log('1208', accountId='asdf')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        self.infoFailCheck(result, '1208', accountId='asdf')
        
        
    def test_invalidCustomData(self):
        '''Invalid customData -- TC9 (Bug 760)'''
        #missing internal customData tag
        self.fail("Bug 760")
        result = self.toolBox.log('1208', customData={'titleAccountId':'14'})
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        #Might need to look at the customData parameter here; might need to be passed in another format
        self.infoFailCheck(result, '1208', customData={'titleAccountId':'14'})
        
        
    def test_invalidTitleCode(self):
        '''Invalid title code -- TC10 ("Bug 768")'''
        self.fail("Bug 768")
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.log('1208')
        print result
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        self.infoFailCheck(result, '1208', titleCode='somejunk')
        
        
    def test_emptyTitleCode(self):
        '''Empty title code -- TC11 ("Bug 768")'''
        self.fail("Bug 768")
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.log('1208')
        print result
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        self.infoFailCheck(result, '1208', titleCode='')
        
        
    def test_emptyTypeId(self):
        '''Empty typeId -- TC12'''
        result = self.toolBox.log('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Unexpected values to complete the request", '4002'])
        self.infoFailCheck(result, '')
        
    
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
    
    
    def infoFailCheck(self, result, typeId, accountId=None, customData={}, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like.  Doc is suspect
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['service'] == "log", "Service returned not equal to service called: log " + str(parameters))
        self.assertTrue(parameters['typeId'] == typeId, "typeId returned not equal to typeId sent: " + typeId + " " + str(parameters))
        if accountId == None :
            self.assertFalse('accountId' in parameters, "accountId not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['accountId'] == accountId, "accountId returned not not equal to accountId passed: " + accountId + " " + str(parameters))
        if customData == {}:
            self.assertFalse('customData' in parameters, "customData not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['customData'] == customData, "accountId returned not not equal to customData passed: " + customData + " " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))