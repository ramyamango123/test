from testSuiteBase import TestSuiteBase

PASSWORD = 'password'

class ParentLogin(TestSuiteBase):
    '''Suite of tests pertaining to the parent login service'''

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_parentLoginWithoutParameters(self):
        '''Parent login without parameters using blank post -- TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('parentLogin')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('session' in result,\
                        'Session was started with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', None, None, None)
    
    
    def test_parentLoginInvalidTitleCode(self):
        '''Parent login with an invalid titleCode -- TC2'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'NSFW', username, PASSWORD)
    
    
    def test_parentLoginBlankTitleCode(self):
        '''Parent login with a blank titleCode  -- TC3'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', '', username, PASSWORD)
    
    
    def test_parentLoginWithoutTitleCode(self):
        '''Parent login without titleCode -- TC4'''
        username, _ = self.toolBox.registerNewUsername()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('session' in result,\
                        'Session was started with no titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', None, username, PASSWORD)
    
    
    def test_parentLoginBlankUser(self):
        '''Parent login with blank username and "valid" password -- TC5'''
        username = ''
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, PASSWORD)
    
    
    def test_parentLoginBlankPass(self):
        '''Parent login with valid username and blank password  -- TC6'''
        username, _ = self.toolBox.registerNewUsername()
        password = ''
        result = self.toolBox.parentLogin(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, password)
    
    
    def test_parentLoginBlankUserPass(self):
        '''Parent login with blank username and blank password  -- TC7'''
        username = ''
        password = ''
        result = self.toolBox.parentLogin(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was started with a blank username and password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, password)
    
    
    def test_parentLoginInvalidUser(self):
        '''Parent login with invalid username and "valid" password  -- TC8'''
        username = "invalidUsername"
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid username')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, PASSWORD)
    
    
    def test_parentLoginInvalidPass(self):
        '''Parent login with valid username and invalid password  -- TC9'''
        username, _ = self.toolBox.registerNewUsername()
        password = "invalidPassword"
        result = self.toolBox.parentLogin(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '11000', 'Login incorrect')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, password)
    
    
    def test_parentLoginInvalidUserPass(self):
        '''Parent login with invalid username and "invalid" password  -- TC10'''
        username = "invalidUsername"
        password = "invalidPassword"
        result = self.toolBox.parentLogin(username, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('session' in result,\
                        'Session was started with an invalid username and password')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'parentLogin', 'KFPW', username, password)
    
    
    def test_parentLoginValidCanPlay(self):
        '''Parent login with valid username and password that can play paid  -- TC11'''
        username, userinfo = self.toolBox.registerNewUsername()
        gameId = userinfo['user']['gameUserId']
        coupon = self.toolBox.redeemCode(gameId, "JUSTTIME")
        result = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidTimeRestricted(self):
        '''Parent login with valid username and password that is time restricted  -- TC12'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        restrict = self.toolBox.gameSchedule(parentId, childId, "<allowedPlayTimes/>", "US/Pacific")
        username = newParent['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidBanned(self):
        '''Parent login with valid username and password that was banned  -- TC13'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        ban = self.toolBox.banChildAccount(parentId, childId)
        username = newParent['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidBannedByParent(self):
        '''Parent login with valid username and password that was banned by parent  -- TC14'''
        username, userdata = self.toolBox.registerNewUsername()
        userId = userdata['user']['id']
        token = self.toolBox.getDeactivateTokenFromDb(userId)
        ban = self.toolBox.banChildAccount(token=token)
        result = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidCanPlaySponsored(self):
        '''Parent login with valid username and password that can play sponsored  -- TC15'''
        username, result = self.toolBox.registerNewParent()
        gameAcctId = result['user']['gameUserId']
        firstLogin = self.toolBox.parentLogin(username, PASSWORD)
        secondLogin = self.toolBox.parentLogin(username, PASSWORD)
        result1 = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(200, result1.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertTrue(username in result1['session']['user']['username'],\
                        'Username returned as ' + result1['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('CAN_PLAY_FREE' in result1['session']['status'],\
                        'Status returned as ' + result1['session']['status'] + ' instead of CAN_PLAY_FREE')
        resultDict = self.toolBox.validateSession(gameAcctId)
        result2 = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidCanPlayFree(self):
        '''Parent login with valid username and password that can play free  -- TC16'''
        username, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.parentLogin(username, PASSWORD)
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
    
    
    def test_parentLoginValidParentAccount(self):
        '''Parent login with valid parent account  -- TC17'''
        username, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.parentLogin(username, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        'Error(s) occurred on login')
        self.assertTrue(username in result['session']['user']['username'],\
                        'Username returned as ' + result['session']['user']['username'] + ' instead of ' + username)
        self.assertTrue('status' in result['session'],\
                        'Expected "status" tag not present')
        self.assertTrue('secretKey' in result['session'],\
                        'Expected "secretKey" tag not present')
        self.userBaseXmlCheck(result['session']['user'])
    
    
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