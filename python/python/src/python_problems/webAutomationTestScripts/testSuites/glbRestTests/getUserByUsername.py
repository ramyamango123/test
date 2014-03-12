from testSuiteBase import TestSuiteBase
REFERRALURL = 'http://www.gaziilion.com'
REFERRALVALUE = '123'

class GetUserByUsername(TestSuiteBase):
    
    
    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_getUserByUsernameWithoutParameters(self):
        '''Get user by username without parameters using a blank get -- TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankGet('getUserByUsername')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('user' in result,\
                        'User was returned without submitting parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', None, None)
    
    
    def test_getUserByUsernameInvalidTitleCode(self):
        '''Get user by username with an invalid titleCode -- TC2'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('user' in result,\
                        'User was returned on an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', 'NSFW', username)
    
    
    def test_getUserByUsernameBlankTitleCode(self):
        '''Get user by username with a blank titleCode -- TC3'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('user' in result,\
                        'User was returned on a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', '', username)
    
    
    def test_getUserByUsernameNoTitleCode(self):
        '''Get user by username without titleCode -- TC4'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('user' in result,\
                        'User was returned without submitting titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', None, username)
    
    
    def test_getUserByUsernameBlankUser(self):
        '''Get user by blank username -- TC5'''
        username = ""
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('user' in result,\
                        'User was returned on a blank username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', 'KFPW', username)
    
    
    def test_getUserByUsernameInvalid(self):
        '''Get user by invalid username -- TC6'''
        username = "invalidUsername"
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('user' in result,\
                        'User was returned on a blank username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'getUserByUsername', 'KFPW', username)
    
    
    def test_getUserByUsernameValidCredentials(self):
        '''Get user by valid username -- TC7'''
        username, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on getUser: ' + username)
        self.assertTrue(username in result['user']['username'],\
                            'Returned username mismatch')
        self.userBaseXmlCheck(result['user'])
        
    def test_getUserByUsernameReferralValuesCheck(self):
        '''Get user by valid username by passing referral values -- TC8'''
        username, result = self.toolBox.registerNewUsername(referralUrl=REFERRALURL, referrer=REFERRALVALUE)
        resultDict = self.toolBox.getUserByUsername(username)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on getUser: ' + username)
        self.assertTrue(username in result['user']['username'],\
                            'Returned username mismatch')
        self.assertTrue('referralUrl' in resultDict['user'], "XML structure failed, no referralUrl" + str(resultDict))
        self.assertTrue('referrer' in resultDict['user'], "XML structure failed, no referrer" + str(resultDict))
        self.assertEqual(resultDict['user']['referralUrl'], REFERRALURL, "referralUrl value is not matching")
        self.assertEqual(resultDict['user']['referrer'], REFERRALVALUE, "referrer value is not matching")
            
    def test_getUserByUsernameValidParent(self):
        '''Get user by valid username, parented -- TC9'''
        _, parentResult = self.toolBox.registerNewParent()
        username = parentResult['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.getUserByUsername(username)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on getUser: ' + username)
        self.assertTrue(username in result['user']['username'],\
                            'Returned username mismatch')
        self.userBaseXmlCheck(result['user'], parent=parentResult['user']['id'])
    
    # Helper methods #
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, username):
        '''Helper function that checks the error parameters against provided values'''
        errorParameters = self.toolBox.httpParamToDict(errorParameters)
        self.assertTrue(service in errorParameters['service'],\
                        'Service returned as ' + errorParameters['service'] + ' instead of ' + service)
        if titleCode != None:
            self.assertTrue(titleCode in errorParameters['titleCode'],\
                        'TitleCode returned as ' + errorParameters['titleCode'] + ' instead of ' + titleCode)
        if username != None:
            self.assertTrue(username in errorParameters['username'],\
                        'Username returned as ' + errorParameters['username'] + ' instead of ' + username)
    
    
    def userBaseXmlCheck(self, userBaseXml, parent=None):
        '''Helper function that verifies the structure of the user XML'''
        self.assertTrue('username' in userBaseXml,\
                        'XML structure unexpected: no username')
        self.assertTrue('friendFinderEnabled' in userBaseXml,\
                        'XML structure unexpected: no friendFinderEnabled')
        self.assertTrue('verifiedAdult' in userBaseXml,\
                        'XML structure unexpected: no verifiedAdult')
        self.assertTrue('chatType' in userBaseXml,\
                        'XML structure unexpected: no chatType')
        self.assertTrue('loginCount' in userBaseXml,\
                        'XML structure unexpected: no loginCount')
        self.assertTrue('gameNewsletterEnabled' in userBaseXml,\
                        'XML structure unexpected: no gameNewsletterEnabled')
        self.assertTrue('emailAddress' in userBaseXml,\
                        'XML structure unexpected: no emailAddress')
        self.assertTrue('lastLogin' in userBaseXml,\
                        'XML structure unexpected: no lastLogin')
        self.assertTrue('remainingFreeLogins' in userBaseXml,\
                        'XML structure unexpected: no remainingFreeLogins')
        self.assertTrue('gameUserId' in userBaseXml,\
                        'XML structure unexpected: no gameUserId')
        self.assertTrue('accountReminderEnabled' in userBaseXml,\
                        'XML structure unexpected: no accountReminderEnabled')
        self.assertTrue('id' in userBaseXml,\
                        'XML structure unexpected: no id')
        if parent != None:
            self.assertTrue('parentId' in userBaseXml,\
                        'parented key not found')
            self.assertTrue(parent == userBaseXml['parentId'],\
                        'parented value not equal to parent game user id')