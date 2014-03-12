from testSuiteBase import TestSuiteBase
import random
import string

PASSWORD = 'password'

class ValidateRegisterLogin(TestSuiteBase):


    def setUp(self):
        '''Called before each and every test case'''
        self.toolBox = self.getGlbToolbox()
    
    
    def tearDown(self):
        '''Called after each and every test case'''
        pass


    def test_positiveNewUser(self):
        '''Validate, register and logging a newly created user'''
        # Make random user name
        username = random.choice(string.letters)
        username += str(random.randrange(1000000000, 9999999999))

        # Run through the scenario while collecting results
        results = []
        results.append(self.toolBox.validateUsername(username))
        results.append(self.toolBox.register(username,\
                                            username+'@brainquake.com',\
                                            PASSWORD))
        results.append(self.toolBox.login(username, PASSWORD))
        
        # Verify that there were no error tags anywhere
        for result in results:
            self.assertFalse(self.getValueFromKey(result, 'error'), result)
        
        # Verify that login result (last in result list) contains an id key with username as value
        self.assertEqual(self.getValueFromKey(result, 'username'), username, result)
        
        
    def getValueFromKey(self, dictionary, targetKey):
        '''Utility function to retrieve the value given key
        inside embedded dictionaries (recursively)
        
        @param  dict
        @param  targetKey
        @return value
        '''
        value = ''
        for eachKey in dictionary.keys():
            if eachKey == targetKey:
                return dictionary[targetKey]
            elif isinstance(dictionary[eachKey], dict):
                returnedValue = self.getValueFromKey(dictionary[eachKey],\
                                                                targetKey)
                if returnedValue:
                    return returnedValue
        return value