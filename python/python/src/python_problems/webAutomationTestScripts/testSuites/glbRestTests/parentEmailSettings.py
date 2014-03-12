'''Parent email settings test suite
Passes Parent platform id (type - long) and 
This WS modifies the email settings information of the child
Created by Sharmila Janardhanan Date on 12/18/2009'''

from testSuiteBase import TestSuiteBase
import random, string

class ParentEmailSettings(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
               
                                    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function'''
        resultDict = self.toolBox.blankPost('emailParentSetting')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=emailParentSetting', \
                                    'Parent email settings parameter values not matching')
        

    def test_allEmptyValues(self):
        '''Pass all empty values to the service'''
        id = accountRemainder = newsLetter = ""
        resultDict = self.toolBox.emailParentSetting(id, newsLetter, accountRemainder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, id, newsLetter, accountRemainder)
                                    
        
    def test_invalidPlatformId(self):
        '''Pass invalid platform id to the service'''
        id = "invalid"
        accountRemainder = "true"
        newsLetter = "false"
        resultDict = self.toolBox.emailParentSetting(id, newsLetter, accountRemainder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(resultDict, id, newsLetter, accountRemainder)
        
        
    def test_passChildId(self):
        '''Pass valid child id (non-parent user) to the service'''
        childUsername, childRegResultDict = self.toolBox.registerNewUsername(8, '@brainquake.com', 'password') 
        childId = childRegResultDict['user']['id']
        accountRemainder = newsLetter = "true"
        resultDict = self.toolBox.emailParentSetting(childId, newsLetter, accountRemainder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13017', 'User is not an Adult')
        self.parameterValuesCheck(resultDict, childId, newsLetter, accountRemainder)
        
        
    def test_validPlatformIdInvalidEmailSettings(self):
        '''Pass valid platform id with invalid email setting to the service'''
        parentId = self.toolBox.getLatestParentPlatformIdFromDb()
        result1 = self.toolBox.getUserById(str(parentId))
        accountReminder = newsLetter = "invalid"
        resultDict = self.toolBox.emailParentSetting(parentId, newsLetter, accountReminder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, str(parentId), newsLetter, accountReminder)
        result = self.toolBox.getUserById(str(parentId))
            
        
    def test_validPlatformIdWithOptionalParameters(self):
        '''Pass valid platform id with optional parameters to the service'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        parentId = parentRegResultDict['user']['id']
        childId = parentRegResultDict['user']['childAccounts']['userBrief']['id']
        self.emailSettingsChangeCheck(parentId, "false", "false")
        gameNewsletter = "true"
        accountRemainder = "true"
        resultDict = self.toolBox.emailParentSetting(parentId, gameNewsletter, accountRemainder)
        self.successCheck(resultDict)
        self.emailSettingsChangeCheck(parentId, gameNewsletter, accountRemainder)
        
            
    def test_validPlatformIdWithoutOptionalParameters(self):
        '''Pass valid platform id without optional parameters to the service'''
        #one of the 2 optional parameter is required
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        parentId = parentRegResultDict['user']['id']
        childId = parentRegResultDict['user']['childAccounts']['userBrief']['id']
        self.emailSettingsChangeCheck(parentId, "false", "false")
        accountRemainder = "true"
        resultDict = self.toolBox.emailParentSetting(parentId, accountRemainderEnabled = accountRemainder)
        self.successCheck(resultDict)
        self.emailSettingsChangeCheck(parentId, "false", accountRemainder) 
        
        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code'''
        titleCode = "somejunk"
        id = self.toolBox.getLatestParentPlatformIdFromDb()
        accountRemainder = newsLetter = "true"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.emailParentSetting(id, newsLetter, accountRemainder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, str(id), newsLetter, accountRemainder, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code'''        
        titleCode = ""
        id = self.toolBox.getLatestParentPlatformIdFromDb()
        accountRemainder = newsLetter = "true"
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.emailParentSetting(id, newsLetter, accountRemainder)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, str(id), newsLetter, accountRemainder, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service'''
        id = self.toolBox.getLatestParentPlatformIdFromDb()
        self.toolBox.setTitleCodeParam(None)   
        resultDict = self.toolBox.emailParentSetting(id, "true", "true")
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'accountRemainderEnabled=true' + 
                                    '&userId=' + str(id) + '&service=emailParentSetting' +
                                    '&gameNewsletterEnabled=true', \
                                    'Parent email settings parameter value not matching') 
        self.toolBox.setTitleCodeParam('KFPW')

        
    ########################
    ###            Helper Methods          ###
    ########################
    
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
    def parameterValuesCheck(self, resultDict, id, newsLetter, accountRemainder, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&accountRemainderEnabled=' + accountRemainder +
                                    '&userId=' + id + '&service=emailParentSetting&' +
                                    'gameNewsletterEnabled=' + newsLetter, \
                                    'Parent email settings parameter value not matching')
                                    
    def emailSettingsChangeCheck(self, parentId, newsLetter, accountRemainder):
        result = self.toolBox.getUserById(parentId)
        self.assertEqual(result['user']['gameNewsletterEnabled'], newsLetter, "gameNewsletterEnabled value is not matching")
        self.assertEqual(result['user']['accountReminderEnabled'], accountRemainder, "accountReminderEnabled value is not matching")
                                    
    def successCheck(self, resultDict):
        '''checks success XML structure and value'''
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not matched")
        self.assertTrue("value" in resultDict['success'], "Value tag not matched")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')