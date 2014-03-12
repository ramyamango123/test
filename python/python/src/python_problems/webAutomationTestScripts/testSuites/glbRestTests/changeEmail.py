'''Change Email Testsuite(POST)
This webservice is used to update the users Email address
Includes both positive and negative test cases.
Created by Ramya Nagendra on 05.11.2010.'''


import sys,random,string,re
import time
import MySQLdb

from testSuiteBase import TestSuiteBase



class ChangeEmail(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
   
    def test_changeEmailIdWithChildGameId (self):
        ''' Register a child account and pass a new valid email address - TC1 '''
        username, gameAcctId,result = self.validChildAccountCreation()
        self.assertTrue('user' in result, "child account not created")
        newEmail = 'test@brainquake.com'
        #print username, newEmail
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 200, "Http code: " + str(newEmailDict.httpStatus()))
        self.assertTrue('success' in newEmailDict, "Success tag not returned")
        self.assertTrue('value' in newEmailDict['success'], "value tag not returned")
        self.assertEqual(newEmailDict['success']['value'], 'TRUE', 'result not matched.')
        changedEmail = self.getUpdatedEmailAddressFromDb(gameAcctId)
        self.assertEqual(changedEmail[0], newEmail, "New Email not updated")
        self.toolBox.scriptOutput("changeEmail - child username and updated email Id", {"Child Username": username, "Updated Email":newEmail})
       
     
    def test_changeEmailIdWithParentGameId (self):
        ''' Register a parent account and pass a new valid email address - TC2 '''
        ParentUsername, parentgameAcctId, result = self.validParentAccountCreation()
        self.assertTrue('user' in result, "parent account not created")
        newEmail = 'test@brainquake.com'
        newEmailDict = self.toolBox.changeEmail(parentgameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 200, "Http code: " + str(newEmailDict.httpStatus()))
        self.assertTrue('success' in newEmailDict, "Success tag not returned")
        self.assertTrue('value' in newEmailDict['success'], "value tag not returned")
        self.assertEqual(newEmailDict['success']['value'], 'TRUE', 'result not matched.')
        changedEmail = self.getUpdatedEmailAddressFromDb(parentgameAcctId)
        self.assertEqual(changedEmail[0], newEmail, "New Email not updated")
           
   
    def test_changeEmailIdWithoutEmailChildGameId (self):
        ''' Register a child account without email address & pass a new email address - TC3 '''
        username, result = self.toolBox.registerNewUsername(email = None)
        self.assertTrue('user' in result, "child account not created")
        gameAcctId = result['user']['gameUserId']
        newEmail = 'test@brainquake.com'
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 200, "Http code: " + str(newEmailDict.httpStatus()))
        self.assertTrue('success' in newEmailDict, "Success tag not returned")
        self.assertTrue('value' in newEmailDict['success'], "value tag not returned")
        self.assertEqual(newEmailDict['success']['value'], 'TRUE', 'result not matched.')
        changedEmail = self.getUpdatedEmailAddressFromDb(gameAcctId)
        self.assertEqual(changedEmail[0], newEmail, "New Email not updated")   
        
        
    def test_changeEmailForAlreadyUpdated (self):
        ''' Change user email again for already updated email - TC4 '''
        username, gameAcctId,result = self.validChildAccountCreation()
        self.assertTrue('user' in result, "child account not created")
        newEmail = 'test@brainquake.com'
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 200, "Http code: " + str(newEmailDict.httpStatus()))
        self.assertTrue('success' in newEmailDict, "Success tag not returned")
        self.assertTrue('value' in newEmailDict['success'], "value tag not returned")
        self.assertEqual(newEmailDict['success']['value'], 'TRUE', 'result not matched.')
        newEmail2 = 'test2@brainquake.com'
        newEmailDict1 = self.toolBox.changeEmail(gameAcctId,newEmail2)
        self.assertEqual(newEmailDict.httpStatus(), 200, "Http code: " + str(newEmailDict1.httpStatus()))
        self.assertTrue('value' in newEmailDict1['success'], "value tag not returned")
        self.assertEqual(newEmailDict1['success']['value'], 'TRUE', 'result not matched.')
        changedEmail = self.getUpdatedEmailAddressFromDb(gameAcctId)
        self.assertEqual(changedEmail[0], newEmail2, "New Email not updated")
             
        
    def test_noParametersPassed(self):
        ''' No parameters passed to the Web Services function - TC5 '''
        resultDict = self.toolBox.blankPost('changeEmail')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=changeEmail', \
                                    'Change Email Parameters not matching')
 
                   
    def test_passAllEmptyValues(self):
        ''' Pass all empty values to the Web Service - TC6 '''
        gameAcctId = newEmail = ''
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
        
 
    def test_passEmptyNewEmail(self):
        ''' Pass empty new email to Web Service - TC7 '''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmail = ''
        newEmailDict = self.toolBox.changeEmail(gameAcctId, newEmail)
        
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
        
    def test_passEmptyGameId(self):
        ''' Pass empty Game Id to Web Service - TC8 '''
        username, _,result = self.validChildAccountCreation()
        gameAcctId = ''
        newEmail = 'test@brainquake.com'
        newEmailDict = self.toolBox.changeEmail(gameAcctId, newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
                   
    def test_passAllInvalidValues(self):
        ''' Pass all invalid values to the service - TC9'''
        gameAcctId = newEmail = 'invalid'
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
 
                       
    def test_passInvalidNewEmailId(self):
        ''' Pass invalid newEmail with valid game Acct Id- TC10'''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmail = 'invalidEmail'
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '15001', 'Email address is invalid')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
 
                    
    def test_passInvalidGameAccId(self):
        ''' Pass invalid game id with valid newEmail - TC11 '''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmail = 'test@brainquake.com'
        gameAcctId = "invalid"
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail)
        
                             
    def test_notmMatchingTitleCode(self):
        ''' Pass not matching title code - TC12'''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmail = 'test@brainquake.com'
        titleCode = "junkcode"
        self.toolBox.setTitleCodeParam(titleCode)
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')   
  
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC13'''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmail = 'test@brainquake.com'
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode) 
        
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmail)
        self.assertEqual(newEmailDict.httpStatus(), 499, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(newEmailDict, gameAcctId, newEmail, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC14'''
        username, gameAcctId, result = self.validChildAccountCreation()
        newEmailId = 'test@brainquake.com'
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode) 
        newEmailDict = self.toolBox.changeEmail(gameAcctId,newEmailId)
        self.assertEqual(newEmailDict.httpStatus(), 400, "Http code: " + str(newEmailDict.httpStatus()))
        self.errorXMLStructureCodeCheck(newEmailDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(newEmailDict['errors']['error']['parameters'], \
                                    'userId=' + gameAcctId + '&service=changeEmail' + '&newEmail=' + newEmailId,
                                    'Change Email Parameters are not matching')
        self.toolBox.setTitleCodeParam('KFPW') 
	 
    # Helper methonds
        	
    def validChildAccountCreation(self):
        '''Registers a valid child account'''
        username, result = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        return username, gameAcctId, result
        
        
    def validParentAccountCreation(self):
        '''Registers a valid parent account'''
        ParentUsername, result = self.toolBox.registerNewParent()
        self.assertTrue('user' in result, "XML from register does not contain user")
        parentgameAcctId = self.toolBox.getGameIdFromUser(ParentUsername)
        return ParentUsername, parentgameAcctId, result
        
    def errorXMLStructureCodeCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
        
        
    def parameterValuesCheck(self, resultDict, gameId, newEmailId, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&userId=' + gameId + '&service=changeEmail' + '&newEmail=' + newEmailId,
                                    'Change Email Parameters are not matching' + ' and returning ' + resultDict['errors']['error']['parameters'] ) 
    

     
    def getUpdatedEmailAddressFromDb(self, gameAcctId):
        '''return User Updated Email by means of Game Id'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_email.acct_email_address, account_title.title_account_id,account.account_id
                          FROM account_title, account, account_email
                          WHERE account_title.account_id = account.account_id
                          AND account_email.account_id = account_title.account_id
                          AND title_account_id = "%s"'''%(gameAcctId))
        result = cursor.fetchall()
        return result[0]    