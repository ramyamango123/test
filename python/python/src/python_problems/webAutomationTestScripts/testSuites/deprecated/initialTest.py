import sys
import random
import string

from testSuiteBase import TestSuiteBase

PASSWORD = 'password'
EMAIL = 'email@brainquake.com'
STATICUSERNAME = "t00111"

class InitialTest(TestSuiteBase):
    '''Class level documentation'''

    def setUp(self):
        self.toolBox = self.getGlbToolbox()

    def test_loginWithIncorrectInfo(self):
        '''Loging with an incorrect useraname and password'''
        username = 'not_a_valid_username'
        password = 'not_a_valid_password'
        result = self.toolBox.login(username,password)

        self.assertEqual(result['errors']['error']['message'], 'Username does not match any records', result)


    def test_loginWithCorrectInfo(self):
        '''Generate and register a new username then login with it'''
        username = self.registerNewUser()

        result = self.toolBox.login(username,PASSWORD)
        self.assertFalse('error' in result)
        self.assertTrue('session' in result, result)
        _ = result['session']['secretKey']


    def test_validateInvalidUsername(self):
        '''Attempt to validate an invalid username '''
        username = '.'
        result = self.toolBox.validateUsername(username)

        self.assertTrue('errors' in result, result)
        self.assertTrue('message' in result['errors']['error'], result)
        self.assert_(result['errors']['error']['message'] == 'Invalid Username - Incorrect length Used', 'Incorrect message retruned'+str(result))


    def test_validateExistingUsername(self):
        '''Validate an exiting username'''
        username = self.registerStaticUser()

        result = self.toolBox.validateUsername(username)
        self.assertTrue('errors' in result, result)
        self.assertTrue('message' in result['errors']['error'], result)
        self.assert_(result['errors']['error']['message'] == 'Invalid Username - Username Not Available', "Incorrect error message " +str(result))

    def test_validateNewUsername(self):
        '''Validate a user name that does not exist'''
        username = "thisIsANonExistingUser"

        result = self.toolBox.validateUsername(username)
        self.assertTrue('success' in result, result)


    def test_registerNewUser(self):
        '''Attempt to create and register a new random user'''
        self.registerNewUser()


    def test_registerExistingUser(self):
        '''Register an existing user to ensure the correct failure case'''
        username = STATICUSERNAME
        password = PASSWORD
        email = username + '@brainquake.com'
        result = self.toolBox.register(username, email, password)
        self.assertTrue('errors' in result)
        self.assertTrue('message' in result['errors']['error'])
        self.assertTrue(result['errors']['error']['message'] == 'Invalid Username - Username Not Available','Incorrect error message '+str(result))

    def test_getExistingUserByName(self):
        '''Get an existing user by username'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)


    def test_getExistingUserById(self):
        '''Get an existing username by ID'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']

        result = self.toolBox.getUserById(id)
        self.assertTrue('user' in result)

    def test_validateCode(self):
        '''Simple test to validate a good coupon code'''
        username = STATICUSERNAME
        code = 'GOODCODE'
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']
        result = self.toolBox.validateCode(id, code)
        #self.assertTrue('session' in result, str(result))
        self.assertTrue(result['errors']['error']['message'] == "Coupon doesn't exist.", result)


    def test_redeemCode(self):
        '''Simple test to validate a garbage coupon code'''
        username = STATICUSERNAME
        code = 'GOODCODE'
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']
        result = self.toolBox.redeemCode(id, code)
        #self.assertTrue('session' in result, str(result))
        self.assertTrue(result['errors']['error']['message'] == "Coupon doesn't exist.", result)

    def test_getMasterBillingAccount(self):
        '''Test getMasterBillingAccount of a valid user'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']

        result = self.toolBox.getMasterBillingAccount(id)
        self.assertTrue('masterAccount' in result, result)

    def test_createMasterBillingAcct(self):
        '''Test setting up a new master billing account'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        firstName = 'Ted'
        lastName = 'Henson'
        billingType = '1'
        address1 = '123 Fake Street'
        city = 'San Mateo'
        state = 'CA'
        country = 'US'
        zipCode = '94403'
        phoneNumber = '555-555-5555'
        planId = '10012005'
        #self.toolBox.setTitleCodeParam(None)
        billingResult = self.toolBox.createMasterBillingAcct(id,gameAcctId,firstName,lastName,billingType,address1,city,state,country,zipCode,phoneNumber,planId,
                                                      creditCardNo='378282246310005',secureNo='123',ccMonth='10', ccYear='2011')
        self.assertTrue('masterAccount' in billingResult, billingResult)


    def test_logout(self):
        '''Test logout of a valid user'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']

        result = self.toolBox.logout(id)
        self.assertTrue('session' in result, result)

    def test_getAvailablePlans(self):
        '''Test get avail plans'''
        self.toolBox.setTitleCodeParam("")
        result = self.toolBox.getAvailablePlans()
        self.assertTrue('listPlan' in result, result)

    def test_startGameSession(self):
        '''Test logout of a valid user'''
        username = STATICUSERNAME
        result = self.toolBox.getUserByUsername(username)
        self.assertTrue('user' in result)
        id = result['user']['id']

        result = self.toolBox.startGameSession(id, '0')
        self.assertTrue('session' in result, result)

    def registerNewUser(self, password=PASSWORD, email=EMAIL, maxTries=100):
        '''Attempt to create and register a new user

        @param  password
        @param  email
        @return username
        '''
        username = ''
        count = 0
        isDone = False

        letters = list(string.letters)
        digits = list(string.digits)

        while not isDone:
            count += 1
            random.shuffle(letters)
            random.shuffle(digits)

            username =  ''.join(letters[:5] + digits[:5])
            email = username + '@brainquake.com'

            result = self.toolBox.register(username, email, password)

            if count > maxTries or 'user' in result:
                isDone = True

        failMessage = 'Username: %s - Result: %s' % (username, result)
        self.assertTrue('user' in result, result)
        self.assertEqual(result['user']['username'], username, failMessage)

        return username

    def registerStaticUser(self, username=STATICUSERNAME, password=PASSWORD, email=EMAIL, maxTries=100):
        '''Attempt to create and register a new user
        @username
        @param  password
        @param  email
        '''
        email = username + '@brainquake.com'
        result = self.toolBox.register(username, email, password)
        return username