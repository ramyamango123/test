'''Change Password test suite
Passes parent username + token or 
parent userid + old password along with 
new password to the service change password
This WS changes the old password to new password
Created by Sharmila Janardhanan on 12/24/2009'''

from testSuiteBase import TestSuiteBase
import random, string

NEWPASSWORD = "newpassword"

class ChangePassword(TestSuiteBase):
    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function -- TC1'''
        resultDict = self.toolBox.blankPost('changePassword')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=changePassword', \
                                    'Change password parameters not matching')
        

    def test_usernameTokenEmptyValues(self):
        '''Pass first set parameters username and token as empty values -- TC2'''
        username = token = ''
        resultDict = self.toolBox.changePassword(NEWPASSWORD, username, token)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck2(resultDict, username, token, NEWPASSWORD)
        
    
    def test_useridPasswordEmptyValues(self):
        '''Pass second set parameters userid and oldpassword as emtpy values -- TC3'''
        userId = oldPassword = ''
        resultDict = self.toolBox.changePassword(NEWPASSWORD, userId = userId, password = oldPassword)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck1(resultDict, userId, oldPassword, NEWPASSWORD)
    
    
    def test_invalidUsernameToken(self):
        '''Pass invalid username and token to the service -- TC4'''
        username = token = "00000000000000"
        resultDict = self.toolBox.changePassword(NEWPASSWORD, username = username, tokenCode = token)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17001', 'Username does not match any records')
        self.parameterValuesCheck2(resultDict, username, token)
        
    
    def test_invalidUserIdOldPassword(self):
        '''Pass invalid userid and old password to the service -- TC5'''
        userId = oldPassword = "invalid"
        resultDict = self.toolBox.changePassword(NEWPASSWORD, userId = userId, password = oldPassword)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck1(resultDict, userId, oldPassword)
        
    
    def test_validUserIdInvalidPassword(self):
        '''Pass valid user Id and invalid password -- TC6'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        parentId = parentRegResultDict['user']['id']
        oldPassword = "invalid"
        resultDict = self.toolBox.changePassword(NEWPASSWORD, userId = parentId, password = oldPassword)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17011', 'The password doesn\'t match.')
        self.parameterValuesCheck1(resultDict, parentId, oldPassword)
        
        
    def test_validUsernameInvalidToken(self):
        '''Pass valid username and invalid token to the service -- TC7'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        token = "00000000000000000000000"
        resultDict = self.toolBox.changePassword(NEWPASSWORD, parentUsername, token)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17006', 'The Token is not valid')
        self.parameterValuesCheck2(resultDict, parentUsername, token)
        
        
    def test_validUsernameOtherActionToken(self):
        '''Pass valid username and valid other action token to the service -- TC8'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        childId = parentRegResultDict['user']['childAccounts']['userBrief']['id']
        token = self.toolBox.getDeactivateTokenFromDb(childId)
        resultDict = self.toolBox.changePassword(NEWPASSWORD, parentUsername, token)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17006', 'The Token is not valid')
        self.parameterValuesCheck2(resultDict, parentUsername, str(token))
        
        
    def test_consumedPasswordToken(self):
        '''Consumed password token -- TC9'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        resetPasswordResult = self.toolBox.resetPassword(parentUsername)
        parentId = parentRegResult['user']['id']
        token = self.toolBox.getPasswordTokenFromDb(parentId)
        resultDict = self.toolBox.changePassword(NEWPASSWORD, parentUsername, token)
        self.successCheck(resultDict)
        resultDict2 = self.toolBox.changePassword(NEWPASSWORD, parentUsername, token)
        self.assertEqual(resultDict2.httpStatus(), 499, "Http code: " + str(resultDict2.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict2, '17006', 'The Token is not valid')
        self.parameterValuesCheck2(resultDict2, parentUsername, str(token))
    
    
    def test_validUsernameToken(self):
        '''Pass valid username and token with new password - parent and child -- TC10'''
        #registers parent and runs resetPassword service, then gets token from db for the resetPassword user
        parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        parentId = parentRegResult['user']['id']
        childUsername = parentRegResult['user']['childAccounts']['userBrief']['username']
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        parentToken = self.getToken(parentUsername, parentId)
        childToken = self.getToken(childUsername, childId)
        resultDict = self.toolBox.changePassword(NEWPASSWORD, parentUsername, parentToken)
        self.successCheck(resultDict)
        resultDict = self.toolBox.changePassword(NEWPASSWORD, childUsername, childToken)
        self.successCheck(resultDict)
        
    
    def test_validUserIdOldPassword(self):
        '''Pass valid userid and old password with new password - parent and child -- TC11'''
        parentUsername, parentRegResultDict = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        parentId = parentRegResultDict['user']['id']
        childId = parentRegResultDict['user']['childAccounts']['userBrief']['id']
        parentResultDict = self.toolBox.changePassword(NEWPASSWORD, userId = parentId, password = "password")
        self.successCheck(parentResultDict)
        childResultDict = self.toolBox.changePassword(NEWPASSWORD, userId = childId, password = "password")
        self.successCheck(childResultDict)

        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code -- TC12'''
        userId = self.toolBox.getLatestPlatformIdFromDb()
        password = "password"
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.changePassword(NEWPASSWORD, userId = userId, password = password)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck1(resultDict, str(userId), password, titleCode = titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code -- TC13'''
        userId = self.toolBox.getLatestPlatformIdFromDb()
        password = "password"
        titleCode = ""
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.changePassword(NEWPASSWORD, userId = userId, password = password)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck1(resultDict, str(userId), password, titleCode = titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service -- TC14'''
        username = self.toolBox.getLatestUsernameFromDb()
        token = "fe28a7a374671424b51b1400819f8bc4"
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.changePassword(NEWPASSWORD, username, token)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'username=' + username + '&newPassword=' + NEWPASSWORD + '&tokenCode=' + token +
                                   '&service=changePassword', 'Change password parameters values not matching')
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
                                    
    def parameterValuesCheck1(self, resultDict, userId, password, newPassword = NEWPASSWORD, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&newPassword=' + NEWPASSWORD + 
                                    '&password=' + password + '&userId=' + userId +
                                    '&service=changePassword', 'Change password parameters not matching')
                                    
    def parameterValuesCheck2(self, resultDict, username, token, newPassword = NEWPASSWORD, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'username=' + username + '&titleCode=' + titleCode + '&newPassword=' + NEWPASSWORD + 
                                    '&tokenCode=' + token + '&service=changePassword', 'Change password parameters not matching')
        
    def getToken(self, username, id):
        resetPasswordResult = self.toolBox.resetPassword(username)
        token = self.toolBox.getPasswordTokenFromDb(id)
        return token
        
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not found")
        self.assertTrue("value" in resultDict['success'], "Value tag not found")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')
