from testSuiteBase import TestSuiteBase

PASSWORD = 'password'
                                
class AddChildAccount(TestSuiteBase):
    
    
    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
        pass
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_addChildAccountWithoutParameters(self):
        '''Add child account without parameters using blank post -- TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('addChildAccount')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', None, None, None, None)
    
    
    def test_addChildAccountInvalidTitleCode(self):
        '''Add child account with invalid titleCode -- TC2'''
        userId, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'NSFW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountBlankTitleCode(self):
        '''Add child account with blank titleCode -- TC3'''
        userId, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', '', userId, childUsername, PASSWORD)
        
        
    def test_addChildAccountNoTitleCode(self):
        '''Add child account without titleCode -- TC4'''
        userId, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', None, userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountInvalidUserId(self):
        '''Add child account with invalid userId -- TC5'''
        userId = 'invalidUserId'
        _, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17000', 'Id does not match any records')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with an invalid userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountInvalidChildUsername(self):
        '''Add child account with invalid childUsername -- TC6'''
        userId, parentEmail = self.helperNewParent()
        childUsername = 'invalidChildUsername'
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17001', 'Username does not match any records')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with an invalid childUsername')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountInvalidChildPassword(self):
        '''Add child account with invalid childPassword -- TC7'''
        password = 'invalidPassword'
        userId, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13018', 'Invalid Child - Password does not match child record')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with an invalid childPassword')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, password)
    
    
    def test_addChildAccountBlankUserId(self):
        '''Add child account with blank userId -- TC8'''
        userId = ''
        _, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountBlankChildUsername(self):
        '''Add child account with blank childUsername -- TC9'''
        userId, parentEmail = self.helperNewParent()
        childUsername = ''
        result = self.toolBox.addChildAccount('', childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank childUsername')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', '', childUsername, PASSWORD)
    
    
    def test_addChildAccountBlankChildPassword(self):
        '''Add child account with blank childPassword -- TC10'''
        password = ''
        userId, parentEmail = self.helperNewParent()
        childUsername, _, _ = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, password)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank childPassword')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, password)
        
        
    def test_addChildAccountAssignTwice(self):
        '''Add child account that has already been added -- TC11'''
        _, newParent = self.toolBox.registerNewParent()
        userId = newParent['user']['id']
        childUsername = newParent['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13019', 'Invalid Child - Child has already been assigned to a parent')
        self.assertFalse('userBrief' in result,\
                        'Child account was added but was already added')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountAlreadyAssigned(self):
        '''Add child account that already has a parent -- TC12'''
        userId, parentEmail = self.helperNewParent()
        _, secondSet = self.toolBox.registerNewParent(email=parentEmail)
        childUsername = secondSet['user']['childAccounts']['userBrief']['username']
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13019', 'Invalid Child - Child has already been assigned to a parent')
        self.assertFalse('userBrief' in result,\
                        'Child account was added but already had a parent')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountEmailMismatch(self):
        '''Add child account without the same email -- TC13'''
        userId, _ = self.helperNewParent()
        childUsername, _ = self.toolBox.registerNewUsername()
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13020', "Invalid Child - Parent email does not match child's email")
        self.assertFalse('userBrief' in result,\
                        'Child account was added with mismatched emails')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountToChildAccount(self):
        '''Add child account to another child account -- TC14'''
        _, firstChild = self.toolBox.registerNewUsername()
        userId = firstChild['user']['id']
        parentEmail = firstChild['user']['emailAddress']
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13017', 'User is not an Adult')
        self.assertFalse('userBrief' in result,\
                        'Child account was added to another child account')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountToItself(self):
        '''Add child account to itself -- TC15'''
        _, loneParent = self.toolBox.registerNewParent()
        userId = loneParent['user']['id']
        childUsername = loneParent['user']['username']
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17015', 'The association between accounts in invalid')
        self.assertFalse('userBrief' in result,\
                        'Child account was added to itself')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, childUsername, PASSWORD)
    
    
    def test_addChildAccountBlankToken(self):
        '''Add child account with a blank token -- TC16'''
        userId, parentEmail = self.helperNewParent()
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        token = ''
        result = self.toolBox.addChildAccount(userId, token=token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with a blank token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, None, None, token=token)
    
    
    def test_addChildAccountInvalidToken(self):
        '''Add child account with an invalid token -- TC17'''
        userId, parentEmail = self.helperNewParent()
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        token = '00000000000000000000000000000000'
        result = self.toolBox.addChildAccount(userId, token=token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17006', 'The Token is not valid')
        self.assertFalse('userBrief' in result,\
                        'Child account was added with an invalid token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'addChildAccount', 'KFPW', userId, None, None, token=token)
    
    
    def test_addChildAccountValidToken(self):
        '''Add child account with valid token -- TC18'''
        userId, parentEmail = self.helperNewParent()
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.addChildAccount(userId, token=token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue(childUsername in result['userBrief']['username'],\
                        'Success xml failure: childUsername ' + childUsername + ' not present')
        self.assertTrue(childId in result['userBrief']['id'],\
                        'Success xml failure: childId ' + childId + ' not present')
        self.assertTrue(childGameUserId in result['userBrief']['gameUserId'],\
                        'Success xml failure: childGameUserId ' + childGameUserId + ' not present')
        self.toolBox.scriptOutput("Add child account using token", {"parentId": userId, "childUsername": childUsername})
    
    
    def test_addChildAccountValidExtraParameters(self):
        '''Add child account with valid ids and token -- 19'''
        userId, parentEmail = self.helperNewParent()
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD, token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue(childUsername in result['userBrief']['username'],\
                        'Success xml failure: childUsername ' + childUsername + ' not present')
        self.assertTrue(childId in result['userBrief']['id'],\
                        'Success xml failure: childId ' + childId + ' not present')
        self.assertTrue(childGameUserId in result['userBrief']['gameUserId'],\
                        'Success xml failure: childGameUserId ' + childGameUserId + ' not present')
    
    
    def test_addChildAccountValidIdParameters(self):
        '''Add child account with all valid non-token parameters -- 20'''
        userId, parentEmail = self.helperNewParent()
        childUsername, childId, childGameUserId = self.registerNewChild(parentEmail)
        result = self.toolBox.addChildAccount(userId, childUsername, PASSWORD)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue(childUsername in result['userBrief']['username'],\
                        'Success xml failure: childUsername ' + childUsername + ' not present')
        self.assertTrue(childId in result['userBrief']['id'],\
                        'Success xml failure: childId ' + childId + ' not present')
        self.assertTrue(childGameUserId in result['userBrief']['gameUserId'],\
                        'Success xml failure: childGameUserId ' + childGameUserId + ' not present')
        self.toolBox.scriptOutput("Add child account without using token", {"parentId": userId, "childUsername": childUsername})
    
    
    def helperNewParent(self):
        '''Helper function that registers a new parent account and outputs the ID and email used'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        parentEmail = newParent['user']['emailAddress']
        return parentId, parentEmail
    
    
    def registerNewChild(self, emailAddress):
        '''Helper function that takes in an email, registers a new child account, and outputs the ID'''
        username, newChild = self.toolBox.registerNewUsername(email=emailAddress)
        childId = newChild['user']['id']
        childGameUserId = newChild['user']['gameUserId']
        return username, childId, childGameUserId
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, userId, childUsername, childPassword, token=None):
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
        if childUsername != None:
            self.assertTrue(childUsername in errorParameters['childUsername'],\
                        'ChildUsername returned as ' + errorParameters['userId'] + ' instead of ' + childUsername)
        if childPassword != None:
            self.assertTrue(childPassword in errorParameters['childPassword'],\
                        'ChildPassword returned as ' + errorParameters['childPassword'] + ' instead of ' + childPassword)
        if token:
            self.assertTrue(token in errorParameters['token'],\
                        'Token returned as ' + errorParameters['token'] + ' instead of ' + token)