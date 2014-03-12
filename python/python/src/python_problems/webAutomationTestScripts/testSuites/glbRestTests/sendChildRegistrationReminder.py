from testSuiteBase import TestSuiteBase

import MySQLdb

class SendChildRegistrationReminder(TestSuiteBase):
    

    def setUp(self):
        '''Substitute GlbToolbox before each and every test'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass
    
    
    def test_sendChildRegistrationReminderWithoutParameters(self):
        '''Send child registration reminder with no parameters using blank post'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.blankPost('sendChildRegistrationReminder')
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent without parameters')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', None, None)
    
    
    def test_sendChildRegistrationReminderWithoutTitleCode(self):
        '''Send child registration reminder with no titleCode'''
        _, accountXml = self.toolBox.registerNewUsername()
        userId = accountXml['user']['id']
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(400, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 400')
        self.errorCheck(result, '4000', 'Not enough parameters to satisfy request')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent without titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', None, userId)
    
    
    def test_sendChildRegistrationReminderInvalidTitleCode(self):
        '''Send child registration reminder with invalid titleCode'''
        _, accountXml = self.toolBox.registerNewUsername()
        userId = accountXml['user']['id']
        self.toolBox.setTitleCodeParam('NSFW')
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17002', 'Title code does not match any records')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent with an invalid titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', 'NSFW', userId)
    
    
    def test_sendChildRegistrationReminderBlankTitleCode(self):
        '''Send child registration reminder with blank titleCode'''
        _, accountXml = self.toolBox.registerNewUsername()
        userId = accountXml['user']['id']
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent with a blank titleCode')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', '', userId)
    
    
    def test_sendChildRegistrationReminderBlankId(self):
        '''Send child registration reminder with blank userId'''
        userId = ''
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '4003', 'Parameter values are empty for the request')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent with a blank userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', 'KFPW', userId)
    
    
    def test_sendChildRegistrationReminderInvalidId(self):
        '''Send child registration reminder with invalid userId'''
        userId = '0000000'
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '17000', 'Id does not match any records')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent with an invalid userId')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', 'KFPW', userId)
    
    
    def test_sendChildRegistrationReminderAlreadySent(self):
        '''Send child registration reminder that has already been sent'''
        _, accountXml = self.toolBox.registerNewUsername()
        userId = accountXml['user']['id']
        result = self.toolBox.sendChildRegistrationReminder(userId)
        result = self.toolBox.sendChildRegistrationReminder(userId)
        self.assertEqual(499, result.httpStatus(),\
                        'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 499')
        self.errorCheck(result, '19000', 'The email has already been sent within the defined throttling period')
        self.assertFalse('Success' in result,\
                        'Registration reminder was sent twice in rapid succession')
        self.errorParameterCheck(result['errors']['error']['parameters'], 'sendChildRegistrationReminder', 'KFPW', userId)
    
    #VALID CASES COMMENTED OUT UNTIL MORE EFFICIENT SQL CAN BE ACQUIRED
    # def test_sendChildRegistrationReminderValidUnparented(self):
        # '''Send child registration reminder with valid unparented parameters'''
        # userId = self.getUnthrottledUnparentedAccountId()
        # result = self.toolBox.sendChildRegistrationReminder(userId)
        # self.assertEqual(200, result.httpStatus(),\
                        # 'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        # self.assertFalse('errors' in result,\
                        # result)
        # self.assertTrue('Success' in result,\
                        # 'Expected "Success" message was not recieved')
        # self.assertTrue('TRUE' in result['Success']['value'],\
                        # 'Expected "TRUE" message was not received')
        # self.toolBox.scriptOutput("Send child registration reminder unparented", {"userId": str(userId)})
    
    
    # def test_sendChildRegistrationReminderValidParented(self):
        # '''Send child registration reminder with valid parented parameters'''
        # userId = self.getUnthrottledParentedAccountId()
        # result = self.toolBox.sendChildRegistrationReminder(userId)
        # self.assertEqual(200, result.httpStatus(),\
                        # 'Http error code was returned as: ' + str(result.httpStatus()) + ' instead of 200')
        # self.assertFalse('errors' in result,\
                        # result)
        # self.assertTrue('Success' in result,\
                        # 'Expected "Success" message was not recieved')
        # self.assertTrue('TRUE' in result['Success']['value'],\
                        # 'Expected "TRUE" message was not received')
        # self.toolBox.scriptOutput("Send child registration reminder parented", {"userId": str(userId)})
    
    
    def getUnthrottledParentedAccountId(self):
        '''Helper function that returns a parented account ID that won't be throttled'''
        dbConnection = MySQLdb.connect(host=self.toolBox.sqlDb, user=self.toolBox.sqlUsername, passwd=self.toolBox.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('SELECT activity_log.account_id FROM activity_log INNER JOIN account ON activity_log.account_id = account.account_id '+\
                        'INNER JOIN account_child_parent ON activity_log.account_id = account_child_parent.child_account_id '+\
                        'WHERE activity_type_id = 1101 AND account.acct_date_created < DATE_SUB(CURDATE(), INTERVAL 12 HOUR) '+\
                        'AND activity_log.account_id NOT IN (SELECT account_id FROM activity_log '+\
                        'WHERE activity_date > DATE_SUB(CURDATE(), INTERVAL 12 HOUR) AND activity_type_id = 1101)')
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            print 'Failed to find unthrottled account'
            return None
    
    
    def getUnthrottledUnparentedAccountId(self):
        '''Helper function that returns a parented account ID that won't be throttled'''
        dbConnection = MySQLdb.connect(host=self.toolBox.sqlDb, user=self.toolBox.sqlUsername, passwd=self.toolBox.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('SELECT activity_log.account_id FROM activity_log INNER JOIN account ON activity_log.account_id = account.account_id '+\
                        'LEFT OUTER JOIN account_child_parent ON activity_log.account_id = account_child_parent.child_account_id '+\
                        'WHERE activity_type_id = 1101 AND account.acct_date_created < DATE_SUB(CURDATE(), INTERVAL 12 HOUR) '+\
                        'AND account_child_parent.child_account_id IS NULL AND activity_log.account_id NOT IN '+\
                        '(SELECT account_id FROM activity_log WHERE activity_date > DATE_SUB(CURDATE(), INTERVAL 12 HOUR) AND activity_type_id = 1101)')
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            print 'Failed to find unthrottled account'
            return None
    
    
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
                        'userId returned as ' + errorParameters['userId'] + ' instead of ' + userId)