'''Validate Username Testsuite
Passes username (type - string) to service validateUsername
This WS verifies username is valid, unique and approriate.
Created by Sharmila Janardhanan on 10/28/2009'''

from testSuiteBase import TestSuiteBase
import random, string
import re

PROFANE_FILE_NAME = 'testsuites/data/profane.txt'

class ValidateUsername(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()

             
    #These commented cases verified through game UI
    # def test_whiteSpacesAsUsername(self):
        # '''White spaces as username -- TC1'''
        # username = '      '
        # resultDict = self.toolBox.validateUsername(username)
        # #self.errorXMLStructureCodeMessageCheck(resultDict, code, message)
        # self.fail("Bug #537 filed. Error message not matching. Globant specific bug. " + str(resultDict))
        
       
    # def test_poundSymbolInUsername(self):
        # '''Pound (#) symbol in username -- TC2'''
        # usernames = ['m#ilajan', '######']
        # for username in usernames:
            # resultDict = self.toolBox.validateUsername(username)
            # #self.errorXMLStructureCodeMessageCheck(resultDict, code, message)
            # self.fail("Bug #536 filed. Error message not matching. Globant specific bug. " + str(resultDict))
        
       
    # def test_ampersandSymbolAsUsername(self):
        # '''Ampersand (&) symbol as username -- TC3'''
        # username = "&" 
        # resultDict = self.toolBox.validateUsername(username)
        # #self.errorXMLStructureCodeMessageCheck(resultDict, code, message)
        # self.fail("Bug #536 filed. Error message not matching. Globant specific bug. " + str(resultDict))
        
        
    def test_emptyUsername(self):
        '''Empty string as username -- TC4'''
        username = ''
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, username) 
                                    
                            
    def test_minCharsInUsername(self):
        '''Enter minimum(5) characters in username -- TC5'''
        username = '54321'
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13014', 'Invalid Username - Incorrect length Used')
        self.parameterValuesCheck(resultDict, username)
        
                
    def test_maxCharsInUsername(self):
        '''Enter maximum(33) characters in username -- TC6'''
        username = 'abcdefghijklmnopqrstuvwxyzzyxwvut'
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13014', 'Invalid Username - Incorrect length Used')
        self.parameterValuesCheck(resultDict, username)
            
        
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function -- TC7'''
        resultDict = self.toolBox.blankGet('validateUsername')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=validateUsername', \
                                    'Validate Username parameter value not matched')
        
        
    def test_illegalCharactersAsUsername(self):
        '''Passing atleast 6 illegal characters as username from a list -- TC8'''
        illegalUsernames = ['!@$%^*', 'email@domain.com', '123%%%', '0987()', '{}[(*@', \
                                        '<>?,./', '~`_-?,', '=[]|\:;"']
        for username in illegalUsernames:
            resultDict = self.toolBox.validateUsername(username)
            self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
            self.errorXMLStructureCodeMessageCheck(resultDict, '13000', 'Invalid Username - Illegal Characters Used')
            self.parameterValuesCheck(resultDict, username)
            
       
    def test_inappropriateIllegalUsername(self):
        '''Profanity string with special characters as username -- TC9'''
        username = '*fuck!'
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.multipleErrorMessageValidation(resultDict, '13001', 'Invalid Username - Inappropriate Content')
        
     
    def test_inappropriateUsernameFromFile(self):
        '''Profanity string as username, reading from text file -- TC10 (bug #556)'''
        f = open(PROFANE_FILE_NAME, 'r')
        profane_list = f.readlines()
        f.close()
        for username in profane_list:
            # To strip leading and trailing new lines, spaces, etc
            username = username.strip()
            resultDict = self.toolBox.validateUsername(username)
            self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
            usernameLength = len(username)
            self.fail("Profane error not displayed for some of the words in the list, bug #556 filed. " + str(resultDict))
            self.multipleErrorMessageValidation(resultDict, '13001', 'Invalid Username - Inappropriate Content', usernameLength)
            
    
    def test_existingUser(self):
        '''Existing user as username - TC11'''
        _, username = self.registerNewUser()
        resultDict = self.toolBox.validateUsername(username)
        print resultDict
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13002', 'Invalid Username - Username Not Available')
        self.parameterValuesCheck(resultDict, username)
        self.assertTrue('extraInfo' in resultDict['errors']['error'], resultDict)
        #username suggestion stored in a variable as tuple
        usernameSuggestions = (resultDict['errors']['error']['extraInfo'])
        #convert tuple into a list
        usernameSuggestion = usernameSuggestions.split(',')
        for suggestion in usernameSuggestion:
        
            #check suggestion returned as 5 string with a  max of 7 digit random numbers using RegEx
            self.assertTrue(re.match('^[a-zA-Z]{5}\d{3,7}$', suggestion))
        
        #check username suggestions not in the database
        for username in usernameSuggestion:
            resultDict = self.toolBox.validateUsername(username)
            self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
            self.assertTrue("success" in resultDict, resultDict)
                
        
    def test_validUniqueAppropriateUsername(self):
        '''Enter valid, unique, and appropriate username - TC12'''
        usernames = ['sixdgt', 'abcdefghijklmnopqrstuvwxyzabcdef']
        for username in usernames:
            resultDict = self.toolBox.validateUsername(username)
            self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
            self.assertTrue("success" in resultDict, "Success tag not found")
            self.assertTrue("value" in resultDict['success'], "Value tag not found")
            self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched.')
            
            
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC13'''
        #for adding new title code
        titleCode = 'somejunk'
        username = "sixdgt"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, username, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC14'''
        titleCode = ""
        username = "sixdgt"
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.validateUsername(username)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, username, titleCode) 
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC15'''
        self.toolBox.setTitleCodeParam(None)   
        resultDict = self.toolBox.validateUsername("sixdgt")
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'username=sixdgt&' + 'service=validateUsername', \
                                    'Parameter value not matched') 
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    ########################
    ###            Helper Methods          ###
    ########################
    
    def registerNewUser(self):
        '''register a new user with 5 random characters plus 4 random digits'''
        flag = "false"
        counter = 0
        randomString = ""
        for i in range(5):
            randomString += random.choice(string.letters)
        while flag == "false" or counter > 100:
            username = randomString + str(random.randint(0, 9999))
            regResultDict = self.toolBox.register(username, 'password', username + '@brainquake.com')
            if('errors' in regResultDict):
                flag = "false"
                counter = counter + 1
            else:
                flag = "true"
                counter = 100
            return regResultDict, username
            
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                       
    def parameterValuesCheck(self, resultDict, username, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode +
                                    '&username=' + username + '&service=validateUsername', \
                                    'Validate Username parameter values not matched')
                                     
    def multipleErrorMessageValidation(self, resultDict, code, message, usernameLength=''):
        '''Function to check multiple error messages. The output is
           dictionary inside a list which is inside another dictionary'''
        self.assertTrue('errors' in resultDict, resultDict)
        self.assertTrue('error' in resultDict['errors'], resultDict)
        if (usernameLength == ''):
            self.assertEqual(resultDict['errors']['error'][0]['code'], str(13000))
            self.assertEqual(resultDict['errors']['error'][0]['message'], \
                                        'Invalid Username - Illegal Characters Used', \
                                        'Error message not matched.')
            self.assertEqual(resultDict['errors']['error'][1]['code'], code)
            self.assertEqual(resultDict['errors']['error'][1]['message'], \
                                            message, 'Error message not matched.')
        else:    
            if (usernameLength >= 6 and usernameLength <= 32):
                self.assertEqual(resultDict['errors']['error']['code'], code)
                self.assertEqual(resultDict['errors']['error']['message'], \
                                            message, 'Error message not matched.')
            else:
                #checks dictionary value inside a list which in turn inside another dictionary
                self.assertEqual(resultDict['errors']['error'][0]['code'], str(13014))
                self.assertEqual(resultDict['errors']['error'][0]['message'], \
                                        'Invalid Username - Incorrect length Used', \
                                        'Error message not matched.')
                self.assertEqual(resultDict['errors']['error'][1]['code'], code)
                self.assertEqual(resultDict['errors']['error'][1]['message'], \
                                            message, 'Error message not matched.')