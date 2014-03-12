'''Get Username By Id Testsuite
Passes userId (type - int) to the service getUserById
This WS retrieves user information if valid userId is passed.
Created by Sharmila Janardhanan on 11/12/2009'''

from testSuiteBase import TestSuiteBase
import random, string
REFERRALURL = 'http://www.gaziilion.com'
REFERRALVALUE = '123'

class GetUserById(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()

    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function -- TC1'''
        resultDict = self.toolBox.blankGet('getUserById')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                     'titleCode=KFPW&' + 'service=getUserById', \
                                     'GetUserById parameter value not matched')
    
    
    def test_emptyUserId(self):
        '''Passing empty string as userId -- TC2'''
        userId = ''
        resultDict = self.toolBox.getUserById(userId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, userId)
        
    
    def test_nonExistingUserId(self):
        '''Pass non-existing userId to the service -- TC3'''
        userId = '000'
        resultDict = self.toolBox.getUserById(userId)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(resultDict, userId)
        
    
    def test_validChildUserId(self):
        '''Pass existing valid child userId to the service -- TC4'''
        result, username = self.registerNewUser()
        id = result['user']['id']
        resultDict = self.toolBox.getUserById(id)
        self.successParameterAndValuesCheck(resultDict, id, username)
        # specific parameter for child user
        self.assertEqual(resultDict['user']['verifiedAdult'], "false", "verifiedAdult value not matching")
        self.assertEqual(resultDict['user']['emailAddress'], username + "@brainquake.com", "emailAddress value is not matching")
        self.assertEqual(resultDict['user']['chatType'], "CANNED", "chatType value is not matching")
        
    def test_ChildUserIdReferralValuesCheck(self):
        '''Pass existing valid child userId with valid referal parameters to the service -- TC4'''
        _, result = self.toolBox.registerNewUsername(referralUrl=REFERRALURL, referrer=REFERRALVALUE)
        id = result['user']['id']
        username = result['user']['username']
        resultDict = self.toolBox.getUserById(id)
        self.successParameterAndValuesCheck(resultDict, id, username, "referralParametersCheck")
        # specific parameter for child user
        self.assertEqual(resultDict['user']['verifiedAdult'], "false", "verifiedAdult value not matching")
        self.assertEqual(resultDict['user']['emailAddress'], username + "@brainquake.com", "emailAddress value is not matching")
        self.assertEqual(resultDict['user']['chatType'], "CANNED", "chatType value is not matching")
        self.assertEqual(resultDict['user']['referralUrl'], REFERRALURL, "referralUrl value is not matching")
        self.assertEqual(resultDict['user']['referrer'], REFERRALVALUE, "referrer value is not matching")
        
    def test_validParent(self):
        '''Get user by valid id, parented -- TC5'''
        _, resultDict = self.toolBox.registerNewParent()
        id = resultDict['user']['childAccounts']['userBrief']['id']
        username = resultDict['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.getUserById(id)
        self.successParameterAndValuesCheck(result, id, username)
        #specific parameter for parented user
        self.assertTrue('parentId' in result['user'],\
                        'parented key not found')
        self.assertTrue(resultDict['user']['id'] == result['user']['parentId'],\
                        'parented value not equal to parent game user id')
        
        
    def test_validParentUserId(self):
        '''Pass existing valid parent userId to the service -- TC6'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        parentId = parentRegResult['user']['id']
        resultDict = self.toolBox.getUserById(parentId)
        self.successParameterAndValuesCheck(resultDict, parentId, parentUsername)
        # specific / different parameters for parent user
        self.assertEqual(resultDict['user']['verifiedAdult'], "true", "verifiedAdult value not matching")
        self.assertTrue("childAccounts" in resultDict['user'], "childAccounts tag not found")
        self.assertTrue("userBrief" in resultDict['user']['childAccounts'], "userBrief tag not found")
        self.assertTrue("username" in resultDict['user']['childAccounts']['userBrief'], "child username tag not found")
        self.assertTrue("gameUserId" in resultDict['user']['childAccounts']['userBrief'], "child gameUserId tag not found")
        self.assertTrue("id" in resultDict['user']['childAccounts']['userBrief'], "child Id tag not found")
        self.assertEqual(resultDict['user']['chatType'], "FULL", "chatType value is not matching")
        
       
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code -- TC7'''
        id = self.toolBox.getLatestPlatformIdFromDb()
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.getUserById(str(id))
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, str(id), titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code to the service -- TC8'''
        id = self.toolBox.getLatestPlatformIdFromDb()
        titleCode = ""
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.getUserById(str(id))
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, str(id), titleCode)
        self.toolBox.setTitleCodeParam('KFPW') 
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service -- TC9'''
        id = self.toolBox.getLatestPlatformIdFromDb()
        self.toolBox.setTitleCodeParam(None)   
        resultDict = self.toolBox.getUserById(str(id))
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                     'id=' + str(id) + '&service=getUserById', \
                                     'GetUserById Parameter value not matched')
        self.toolBox.setTitleCodeParam('KFPW')


    ########################
    ###            Helper Methods          ###
    ########################
    
    def registerNewUser(self):
        '''register a new user with 5 random characters plus 4 random digits'''
        flag = "false"
        counter = 0
        randomString = ""
        for i in range(5):
            randomString += random.choice(string.letters)
        while flag == "false" or counter > 100:
            username = randomString + str(random.randint(0, 9999))
            regResultDict = self.toolBox.register(username, 'password', username + '@brainquake.com', )
            if('errors' in regResultDict):
                flag = "false"
                counter = counter + 1
            else:
                flag = "true"
                counter = 100
            return regResultDict, username
            
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "Parameters tag not found")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], \
                                    message, 'Error message not matched')
                                    
    def parameterValuesCheck(self, resultDict, id, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                     'titleCode=' + titleCode +
                                     '&id=' + id + '&service=getUserById', \
                                     'GetUserById Parameter value not matched')
                                     
    def successParameterAndValuesCheck(self, resultDict, id, username, referralvaluescheck=None):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertEqual(resultDict['user']['id'], id, 'UserId not matched')
        self.assertEqual(resultDict['user']['username'], username, 'Username not matching')
        self.assertTrue("friendFinderEnabled" in resultDict['user'], "friendFinderEnabled tag not found")
        self.assertEqual(resultDict['user']['friendFinderEnabled'], "true", "friendFinderEnabled value is not matching")
        self.assertTrue("verifiedAdult" in resultDict['user'], "verifiedAdult tag not found")
        self.assertTrue("chatType" in resultDict['user'], "chatType tag not found")
        self.assertTrue("gameNewsletterEnabled" in resultDict['user'], "gameNewsletterEnabled tag not found")
        self.assertEqual(resultDict['user']['gameNewsletterEnabled'], "false", "gameNewsletterEnabled value is not matching")
        self.assertTrue("emailAddress" in resultDict['user'], "emailAddress tag not found")
        self.assertTrue("gameUserId" in resultDict['user'], "gameUserId tag not found")
        self.assertTrue("accountReminderEnabled" in resultDict['user'], "accountReminderEnabled tag not found")
        self.assertEqual(resultDict['user']['accountReminderEnabled'], "false", "accountReminderEnabled value is not matching")
        self.assertTrue("lastLogin" in resultDict['user'], "lastLogin tag not found")
        self.assertEqual(resultDict['user']['lastLogin'], "", 'Last Login valud not matched')
        self.assertTrue("loginCount" in resultDict['user'], "loginCount tag not found")
        self.assertEqual(resultDict['user']['loginCount'], "0", 'Login count not matched')
        self.assertTrue("remainingFreeLogins" in resultDict['user'], "remainingFreeLogins tag not found")
        self.assertEqual(resultDict['user']['remainingFreeLogins'], "1", 'Remaining free logins not matched: ' + str(resultDict))
        if referralvaluescheck == "referralParametersCheck":
            self.assertTrue('referralUrl' in resultDict['user'], "XML structure failed, no referralUrl" + str(resultDict))
            self.assertTrue('referrer' in resultDict['user'], "XML structure failed, no referrer" + str(resultDict))
        
        
        
        
        
        