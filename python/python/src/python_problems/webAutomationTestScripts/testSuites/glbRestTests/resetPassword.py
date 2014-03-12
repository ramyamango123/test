'''Reset password email test suite
Passes parent username (type - string) and 
title code to service resetPassword
This WS send a link to the parent email so the
password can be reset for that user.
Created by Sharmila Janardhanan on 12/23/2009'''

from testSuiteBase import TestSuiteBase
import random, string

class ResetPassword(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function - TC1'''
        resultDict = self.toolBox.blankGet('resetPassword')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=resetPassword', \
                                    'Reset password parameter not matching')
        

    def test_emptyUsername(self):
        '''Pass empty username to the service - TC2'''
        username = ''
        resultDict = self.toolBox.resetPassword(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, username)
        
        
    def test_invalidUsername(self):
        '''Pass invalid username to the service - TC3'''
        username = "trgfghfhf"
        resultDict = self.toolBox.resetPassword(username)
        print resultDict
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17001', 'Username does not match any records')
        self.parameterValuesCheck(resultDict, username)
        
        
    def test_validParentUsername(self):
        '''Pass valid parent username to reset the password - TC4'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        resultDict = self.toolBox.resetPassword(parentUsername)
        self.successCheck(resultDict)
        
    def test_emailValidationWithChildUsername(self):
        ''' Register a child account without email Id and check if resetPassword WS returns a error message - TC5'''
        username, result = self.toolBox.registerNewUsername(email = None)
        resultDict = self.toolBox.resetPassword(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17018', 'The platform account does not have a registered email address')
        self.parameterValuesCheck(resultDict, username)
         
    def test_emailValidationSuccessUsingChangeEmailWS(self):
        ''' Check for success message after creating a new email through changeEmail WS - TC6'''
        username, result = self.toolBox.registerNewUsername(email = None)
        gameAcctId = result['user']['gameUserId']
        newEmail = 'test@brainquake.com'
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertTrue('success' in newEmailDict, "Success tag not returned")
        resultDict = self.toolBox.resetPassword(username)
        self.successCheck(resultDict)
        
    def test_validChildUsername(self):
        '''Pass valid child username to reset the password - TC7'''
        childUsername, childRegResultDict = self.toolBox.registerNewUsername(8, '@brainquake.com', 'password')
        resultDict = self.toolBox.resetPassword(childUsername)
        self.successCheck(resultDict)

          
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC8'''
        username = self.toolBox.getLatestUsernameFromDb()
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.resetPassword(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, username, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC9'''
        username = self.toolBox.getLatestUsernameFromDb()
        titleCode = ""
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.resetPassword(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, username, titleCode) 
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC10'''
        username = self.toolBox.getLatestUsernameFromDb()
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.resetPassword(username)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'username=' + username + '&service=resetPassword', \
                                    'Reset Password Parameter not matching')
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
                                    
    def parameterValuesCheck(self, resultDict, username, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&username=' + username +
                                    '&service=resetPassword' , 'Reset password parameter not matching')
    
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not matched")
        self.assertTrue("value" in resultDict['success'], "Value tag not matched")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')