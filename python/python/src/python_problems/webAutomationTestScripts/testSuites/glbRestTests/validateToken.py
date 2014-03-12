from testSuiteBase import TestSuiteBase

class ValidateToken(TestSuiteBase):
    

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_validateTokenWithoutParameters(self):
        '''Validate token with no parameters using blank get --TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankGet('validateToken')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('token' in result,\
                        'Token was validated with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', None, None)
    
    
    def test_validateTokenWithoutTitleCode(self):
        '''Validate token with no titleCode --TC2'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(id)
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.validateToken(token)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('token' in result,\
                        'Token was validated with no titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', None, token)
    
    
    def test_validateTokenBlankTitleCode(self):
        '''Validate token with blank titleCode --TC3'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(id)
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.validateToken(token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('token' in result,\
                        'Token was validated with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', '', token)
    
    
    def test_validateTokenInvalidTitleCode(self):
        '''Validate token with invalid titleCode --TC4'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(id)
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.validateToken(token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('token' in result,\
                        'Token was validated with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', 'NSFW', token)
    
    
    def test_validateTokenBlankToken(self):
        '''Validate token with blank token --TC5'''
        token = ''
        result = self.toolBox.validateToken(token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('token' in result,\
                        'Token was validated on a blank token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', 'KFPW', '')
    
    
    def test_validateTokenInvalidToken(self):
        '''Validate token with invalid token --TC6'''
        token = '00000000000000000000000000000000'
        result = self.toolBox.validateToken(token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17006', 'The Token is not valid')
        self.assertFalse('token' in result,\
                        'Token was validated on an invalid token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'validateToken', 'KFPW', token)
    
    
    def test_validatePasswordToken(self):
        '''Validate password token --TC7'''
        childUsername, account = self.toolBox.registerNewUsername()
        reset = self.toolBox.resetPassword(childUsername)
        id = account['user']['id']
        token = self.toolBox.getPasswordTokenFromDb(id)
        result = self.toolBox.validateToken(token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('token' in result,\
                        'Expected "token" missing from result')
        self.assertTrue('/account/resetpassword/' in result['token']['action'],\
                        'Expected "/account/resetpassword/" missing from result')
        self.assertTrue(id in result['token']['user']['id'],\
                        'Expected id: ' + id + ' missing from result')
        self.userBaseXmlCheck(result['token']['user'])
    
    
    def test_validateDeactivationToken(self):
        '''Validate deactivation token --TC8'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getDeactivateTokenFromDb(id)
        result = self.toolBox.validateToken(token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('token' in result,\
                        'Expected "token" missing from result')
        self.assertTrue('/account/deactivate' in result['token']['action'],\
                        'Expected "/account/deactivate" missing from result')
        self.assertTrue(id in result['token']['user']['id'],\
                        'Expected id: ' + id + ' missing from result')
        self.userBaseXmlCheck(result['token']['user'])
    
    
    def test_validateChatToken(self):
        '''Validate chat token --TC9'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getChatTokenFromDb(id)
        result = self.toolBox.validateToken(token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('token' in result,\
                        'Expected "token" missing from result')
        self.assertTrue('/account/settings/chat' in result['token']['action'],\
                        'Expected "/account/settings/chat" missing from result')
        self.assertTrue(id in result['token']['user']['id'],\
                        'Expected id: ' + id + ' missing from result')
        self.userBaseXmlCheck(result['token']['user'])
    
    
    def test_validateRegisterToken(self):
        '''Validate register token --TC10'''
        _, account = self.toolBox.registerNewUsername()
        id = account['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(id)
        result = self.toolBox.validateToken(token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('token' in result,\
                        'Expected "token" missing from result')
        self.assertTrue('/register' in result['token']['action'],\
                        'Expected "/register" missing from result')
        self.assertTrue(id in result['token']['user']['id'],\
                        'Expected id: ' + id + ' missing from result')
        self.userBaseXmlCheck(result['token']['user'])
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, token):
        '''Helper function that checks the error parameters against provided values'''
        errorParameters = self.toolBox.httpParamToDict(errorParameters)
        self.assertTrue(service in errorParameters['service'],\
                        'Service returned as ' + errorParameters['service'] + ' instead of ' + service)
        if titleCode != None:
            self.assertTrue(titleCode in errorParameters['titleCode'],\
                        'TitleCode returned as ' + errorParameters['titleCode'] + ' instead of ' + titleCode)
        if token != None:
            self.assertTrue(token in errorParameters['token'],\
                        'Token returned as ' + errorParameters['token'] + ' instead of ' + token)
    
    
    def userBaseXmlCheck(self, userBaseXml):
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