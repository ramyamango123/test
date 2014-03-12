from testSuiteBase import TestSuiteBase

PASSWORD = 'password'

class Login(TestSuiteBase):
    '''Suite of tests pertaining to the login service'''

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_loginWithoutParameters(self):
        '''Login without parameters using blank post -- TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('login')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('session' in result,\
                        'Session was started with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', None, None, None)
    
    
    def test_loginInvalidTitleCode(self):
        '''Login with an invalid titleCode -- TC2'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'NSFW', username, PASSWORD)
    
    
    def test_loginBlankTitleCode(self):
        '''Login with a blank titleCode -- TC3'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', '', username, PASSWORD)
    
    
    def test_loginWithoutTitleCode(self):
        '''Login without titleCode -- TC4'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('session' in result,\
                        'Session was started with no titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', None, username, PASSWORD)
    
    
    def test_loginBlankUser(self):
        '''Login with blank username and "valid" password -- TC5'''
        username = ''
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, PASSWORD)
    
    
    def test_loginBlankPass(self):
        '''Login with valid username and blank password -- TC6'''
        username, _ = self.toolBox.registerNewUsername()
        password = ''
        result = self.toolBox.login(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, password)
    
    
    def test_loginBlankUserPass(self):
        '''Login with blank username and blank password -- TC7'''
        username = ''
        password = ''
        result = self.toolBox.login(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank username and password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, password)
    
    
    def test_loginInvalidUser(self):
        '''Login with invalid username and "valid" password -- TC8'''
        username = "invalidUsername"
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, PASSWORD)
    
    
    def test_loginInvalidPass(self):
        '''Login with valid username and invalid password -- TC9'''
        username, _ = self.toolBox.registerNewUsername()
        password = "invalidPassword"
        result = self.toolBox.login(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '11000', 'Login incorrect')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, password)
    
    
    def test_loginInvalidUserPass(self):
        '''Login with invalid username and "invalid" password -- TC10'''
        username = "invalidUsername"
        password = "invalidPassword"
        result = self.toolBox.login(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid username and password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'login', 'KFPW', username, password)
    
    
    def test_loginValidCanPlay(self):
        '''Login with valid username and password that can play paid -- TC11'''
        username, userinfo = self.toolBox.registerNewUsername()
        gameId = userinfo['user']['gameUserId']
        coupon = self.toolBox.redeemCode(gameId, "JUSTTIME")
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('CAN_PLAY' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of CAN_PLAY')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
    
    
    def test_loginValidTimeRestricted(self):
        '''Login with valid username and password that is time restricted -- TC12'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        restrict = self.toolBox.gameSchedule(parentId, childId, "<allowedPlayTimes/>", "US/Pacific")
        username = newParent['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('TIME_RESTRICTED' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of TIME_RESTRICTED')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
    
    
    def test_loginValidBanned(self):
        '''Login with valid username and password that was banned -- TC13'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        ban = self.toolBox.banChildAccount(parentId, childId)
        username = newParent['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('BANNED' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of BANNED')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
    
    
    def test_loginValidBannedByParent(self):
        '''Login with valid username and password that was banned by parent -- TC14'''
        username, userdata = self.toolBox.registerNewUsername()
        userId = userdata['user']['id']
        token = self.toolBox.getDeactivateTokenFromDb(userId)
        ban = self.toolBox.banChildAccount(token=token)
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('BANNED_BY_PARENT' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of BANNED_BY_PARENT')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
    
    
    def test_loginValidCanPlaySponsored(self):
        '''Login with valid username and password that can play sponsored -- TC15'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = result['user']['gameUserId']
        firstLogin = self.toolBox.login(username, PASSWORD)
        secondLogin = self.toolBox.login(username, PASSWORD)
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('CAN_PLAY_FREE' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of CAN_PLAY_FREE')
        resultDict = self.toolBox.validateSession(gameAcctId)
        result2 = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result2.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result2,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result2['session']['user']['username'],\
                        'Username returned as ' + result2['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('CAN_PLAY_SPONSORED' in result2['session']['status'],\
                        'Status returned as ' + result2['session']['status'] + ' instead of CAN_PLAY_SPONSORED')
        self.assertTrue('secretKey' in result2['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result2['session']['user'])
    
    def test_loginValidCanPlayFree(self):
        '''Login with valid username and password that can play free -- TC16'''
        username, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('CAN_PLAY_FREE' in result['session']['status'],\
                        'Status returned as ' + result['session']['status'] + ' instead of CAN_PLAY_FREE')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
        
        
    def test_loginValidParented(self):
        '''Login with valid username and password who has a parent -- TC17'''
        _, parentResult = self.toolBox.registerNewParent()
        username = parentResult['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.login(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'], parent=parentResult['user']['id'])
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, username, password):
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
        if password != None:
            self.assertTrue(password in errorParameters['password'],\
                        'Password returned as ' + errorParameters['password'] + ' instead of ' + password)
    
    
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
                        'parented value not equal to parent id')