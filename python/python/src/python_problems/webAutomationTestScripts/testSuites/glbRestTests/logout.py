from testSuiteBase import TestSuiteBase

class Logout(TestSuiteBase):
    '''Suite of tests pertaining to the logout service'''

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_logoutWithoutParameters(self):
        '''Logout without parameters using blank post'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('logout')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Session was ended with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', None, None)
    
    
    def test_logoutInvalidTitleCode(self):
        '''Logout with an invalid titleCode'''
        userId = self.userId()
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.logout(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('success' in result,\
                        'Session was ended with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', 'NSFW', userId)
    
    
    def test_logoutBlankTitleCode(self):
        '''Logout with a blank titleCode'''
        userId = self.userId()
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.logout(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Session was ended with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', '', userId)
    
    
    def test_logoutWithoutTitleCode(self):
        '''Logout without titleCode'''
        userId = self.userId()
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.logout(userId)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Session was ended with no titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', None, userId)
    
    
    def test_logoutBlankUserId(self):
        '''Logout with blank userId'''
        userId = ''
        result = self.toolBox.logout(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('session' in result,\
                        'Session was ended with a blank userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', 'KFPW', userId)
    
    
    def test_logoutInvalidUserId(self):
        '''Logout with invalid userId'''
        userId = "invalidUserId"
        result = self.toolBox.logout(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17003', 'Title Account does not match any records')
        self.assertFalse('success' in result,\
                        'Session was ended with an invalid userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'logout', 'KFPW', userId)
    
    
    def test_logoutValidCredentials(self):
        '''Logout with valid userId'''
        userId = self.userId()
        result = self.toolBox.logout(userId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" message was not recieved')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" message was not received')
        
        
    def userId(self):
        '''Helper function that registers an account and returns the userId'''
        _, account = self.toolBox.registerNewUsername()
        userId = account['user']['id']
        return userId
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, userId):
        '''Helper function that checks the error parameters against provided values'''
        errorParameters = self.toolBox.httpParamToDict(errorParameters)
        self.assertTrue(service in errorParameters['service'],\
                        'Service returned as ' + errorParameters['service'] + ' instead of ' + service)
        if titleCode != None:
            self.assertTrue(titleCode in errorParameters['titleCode'],\
                        'TitleCode returned as ' + errorParameters['titleCode'] + ' instead of ' + titleCode)
        if userId != None:
            self.assertTrue(userId in errorParameters['userId'],\
                        'UserId returned as ' + errorParameters['userId'] + ' instead of ' + userId)