from testSuiteBase import TestSuiteBase

                                
class BanChildAccount(TestSuiteBase):
    
    
    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
        pass
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_banChildAccountWithoutParameters(self):
        '''Ban child account with no parameters using blank post -- TC1'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('banChildAccount')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Child account was banned with no parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', None)
    
    
    def test_banChildAccountInvalidTitleCode(self):
        '''Ban child account with invalid titleCode -- TC2'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('success' in result,\
                        'Child account was banned with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'NSFW', parentId=parentId, childId=childId)
        
        
    def test_banChildAccountBlankTitleCode(self):
        '''Ban child account with blank titleCode -- TC3'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Child account was banned with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', '', parentId=parentId, childId=childId)
        
        
    def test_banChildAccountNoTitleCode(self):
        '''Ban child account without titleCode -- TC4'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('success' in result,\
                        'Child account was banned without titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', None, parentId=parentId, childId=childId)
        
        
    def test_banChildAccountInvalidParentId(self):
        '''Ban child account with invalid parentId -- TC5'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = '0000000'
        childId = newParent['user']['childAccounts']['userBrief']['id']
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17000', 'Id does not match any records')
        self.assertFalse('success' in result,\
                        'Child account was banned with an invalid parentId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
    
    
    def test_banChildAccountInvalidChildId(self):
        '''Ban child account with invalid childId -- TC6'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = '0000000'
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17005', 'Parent Id and Child Id are not associated')
        self.assertFalse('success' in result,\
                        'Child account was banned with an invalid childId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
    
    
    def test_banChildAccountInvalidParentAndChildId(self):
        '''Ban child account with invalid parentId and childId -- TC7'''
        parentId = '0000000'
        childId = '0000000'
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17000', 'Id does not match any records')
        self.assertFalse('success' in result,\
                        'Child account was banned with an invalid childId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
    
    
    def test_banChildAccountBlankParentId(self):
        '''Ban child account with blank parentId -- TC8'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = ''
        childId = newParent['user']['childAccounts']['userBrief']['id']
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Child account was banned with a blank parentId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
    
    
    def test_banChildAccountBlankChildId(self):
        '''Ban child account with blank childId -- TC9'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = ''
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Child account was banned with a blank childId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
    
    
    def test_banChildAccountBlankParentAndChildId(self):
        '''Ban child account with blank parentId and childId -- TC10'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = ''
        childId = ''
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Child account was banned with a blank childId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', parentId=parentId, childId=childId)
        
        
    def test_banChildAccountInvalidToken(self):
        '''Ban child account with invalid token -- TC11'''
        token = '00000000000000000000000000000000'
        result = self.toolBox.banChildAccount(token=token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '13021', 'Validation Token does not exist')
        self.assertFalse('success' in result,\
                        'Child account was banned with an invalid token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', token=token)
        
        
    def test_banChildAccountBlankToken(self):
        '''Ban child account with blank token -- TC12'''
        token = ''
        result = self.toolBox.banChildAccount(token=token)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('success' in result,\
                        'Child account was banned with a blank token')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'banChildAccount', 'KFPW', token=token)
    
    
    def test_banChildAccountValidToken(self):
        '''Ban child account with valid token -- TC13'''
        _, newParent = self.toolBox.registerNewParent()
        childId = newParent['user']['childAccounts']['userBrief']['id']
        token = self.toolBox.getDeactivateTokenFromDb(childId)
        result = self.toolBox.banChildAccount(token=token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" message was not recieved')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" message was not received')
        self.toolBox.scriptOutput("Ban child account using token", {"childId": childId})
    
    
    def test_banChildAccountValidExtraParameters(self):
        '''Ban child account with both valid IDs and token -- TC14'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        token = self.toolBox.getDeactivateTokenFromDb(childId)
        result = self.toolBox.banChildAccount(parentId, childId, token)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" message was not recieved')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" message was not received')
    
    
    def test_banChildAccountValidParameters(self):
        '''Ban child account with all valid non-token parameters -- TC15'''
        _, newParent = self.toolBox.registerNewParent()
        parentId = newParent['user']['id']
        childId = newParent['user']['childAccounts']['userBrief']['id']
        result = self.toolBox.banChildAccount(parentId, childId)
        self.assertEqual(200, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        self.assertFalse('errors' in result,\
                        result)
        self.assertTrue('success' in result,\
                        'Expected "success" message was not recieved')
        self.assertTrue('TRUE' in result['success']['value'],\
                        'Expected "TRUE" message was not received')
        self.toolBox.scriptOutput("Ban child account without using token", {"childId": childId})
    
    
    def errorCheck(self, result, errorCode, errorMessage):
        '''Helper function that checks the error message and code against provided values'''
        self.assertEqual(errorCode, result['errors']['error']['code'],\
                        'Error code returned as ' + result['errors']['error']['code'] + ' instead of ' + errorCode)
        self.assertTrue(errorMessage in result['errors']['error']['message'],\
                        'Error message returned as ' + result['errors']['error']['message'] + ' instead of ' + errorMessage)
    
    
    def errorParameterCheck(self, errorParameters, service, titleCode, parentId=None, childId=None, token=None):
        '''Helper function that checks the error parameters against provided values'''
        errorParameters = self.toolBox.httpParamToDict(errorParameters)
        self.assertTrue(service in errorParameters['service'],\
                        'Service returned as ' + errorParameters['service'] + ' instead of ' + service)
        if titleCode != None:
            self.assertTrue(titleCode in errorParameters['titleCode'],\
                        'TitleCode returned as ' + errorParameters['titleCode'] + ' instead of ' + titleCode)
        if parentId != None:
            self.assertTrue(parentId in errorParameters['parentId'],\
                        'ParentId returned as ' + errorParameters['parentId'] + ' instead of ' + parentId)
        if childId != None:
            self.assertTrue(childId in errorParameters['childId'],\
                        'ChildId returned as ' + errorParameters['childId'] + ' instead of ' + childId)
        if token != None:
            self.assertTrue(token in errorParameters['token'],\
                        'Token returned as ' + errorParameters['token'] + ' instead of ' + token)