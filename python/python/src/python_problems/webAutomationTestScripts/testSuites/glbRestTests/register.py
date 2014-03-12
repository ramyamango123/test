#Passes username, password, and email address strings to the register web service.
#Includes both positive and negative test cases.
#One test case (custom game acct id) has a dependency on validateUsername
#Created by Tarja Rechsteiner on 10.28.09.

import sys
import random
import string
import re

from testSuiteBase import TestSuiteBase

STATICPASSWORD = 'password'
STATICUSERNAME = 'adsfghj2003'
EMAIL = STATICUSERNAME + '@brainquake.com'
PROFANE_FILE = 'testSuites/data/profane.txt'
USERNAME_FILE = 'testSuites/data/illegal_usernames.txt'
REFERRALURL = 'http://www.gaziilion.com'
REFERRALVALUE = '123'
class Register(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()

        
    def test_validInfo(self):
        '''Valid username, password, and email address -- TC1'''
        username, result = self.toolBox.registerNewUsername()

        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result, username, username+'@brainquake.com')
        self.toolBox.scriptOutput("register valid account", {"username": username, 'id': result['user']['id']})
        
        
    def test_validInfoNoEmail(self):
        '''Valid username and password, no email address -- TC2'''
        username, result = self.toolBox.registerNewUsername(email=None)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result, username, '')
        self.toolBox.scriptOutput("register valid account", {"username": username, 'id': result['user']['id']})
        
        
    def test_customGameAcctId(self):
        '''Valid username abd password, non-numeric game account id -- TC3 (Bug 841)'''
        #dependency on validateUsername
        self.fail("Bug 841")
        username = ''
        gameAcctId = ''
        characters = string.letters + string.digits
        c = 0
        
        #similar logic to helper methods in toolBox.  Attempts to generate a random username/gameAcctId 100 times.
        #since these are the test cases for register, using validateUsername as a check rather than register itself.
        while (not username) or 'errors' in usernameResult or c > 100:
            username = ''
            for i in range(10):
                username += random.choice(characters)
            for j in range(5):
                gameAcctId += random.choice(characters)
            usernameResult = self.toolBox.validateUsername(username)
            c += 1
        self.assertFalse('errors' in usernameResult, "username registration failed: " + str(usernameResult))
        result = self.toolBox.register(username, STATICPASSWORD, gameUserId=gameAcctId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result)
        self.infoSuccessCheck(result, username, '', gameAcctId)
        self.toolBox.scriptOutput("register non-numeric gameAcctId account", {"username": username, 'id': result['user']['id']})
        
        
    def test_withoutParameters(self):
        '''Without any parameters -- TC4'''
        result = self.toolBox.blankPost('register')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertEqual(result['errors']['error']['message'],\
                        'Not enough parameters to satisfy request',\
                        "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertEqual(result['errors']['error']['code'], '4000',\
                        "Expected error code not found.  Found: " + str(result['errors']['error']['code']))

        
    def test_withBlankInfo(self):
        '''Blank username, password, and email address -- TC5'''
        result = self.toolBox.blankPost('register', {'username': '', 'password': '', 'emailAddress': '', 'gameUserId': ''})

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', '', '', '')

    
    def test_withBlankUsername(self):
        '''Valid password and email address, but blank username -- TC6'''
        result = self.toolBox.blankPost('register', {'username': '', 'password': STATICPASSWORD, 'emailAddress': EMAIL, 'gameUserId': ''})
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', EMAIL, STATICPASSWORD, '')
    
    
    def test_withBlankPassword(self):
        '''Valid username and email address, but invalid password -- TC7'''
        result = self.toolBox.register(STATICUSERNAME, '', EMAIL)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, STATICUSERNAME, EMAIL, '')
        
        
    def test_withBlankUsernameAndPassword(self):
        '''Blank username and password, valid email address -- TC8'''
        result = self.toolBox.blankPost('register', {'username': '', 'password': '', 'emailAddress': EMAIL, 'gameUserId': ''})
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', EMAIL, '', '')

    
    def test_withBlankPasswordAndEmailAddress(self):
        '''Blank email address and password, valid username -- TC9'''
        result = self.toolBox.register(STATICUSERNAME, '', '')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, STATICUSERNAME, '', '')


    def test_withBlankUsernameAndEmailAddress(self):
        '''Blank username and email address, valid password -- TC10'''
        result = self.toolBox.blankPost('register', {'username': '', 'password': STATICPASSWORD, 'emailAddress': '', 'gameUserId': ''})
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', '', STATICPASSWORD, '')
        
    def test_withBlankGameUserId(self):
        '''Blank gameUserId, valid username, email, and password -- TC11'''
        result = self.toolBox.blankPost('register', {'username': STATICUSERNAME, 'password': STATICPASSWORD, 'emailAddress': EMAIL, 'gameUserId': ''})
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, STATICUSERNAME, EMAIL, STATICPASSWORD, '')


    def test_withIllegalCharacters(self):
        '''Username with special characters -- TC12'''
        f = open(USERNAME_FILE, 'r')
        usernameList = f.readlines()
        f.close()
        
        for username in usernameList:
            username = username.strip()
            result = self.toolBox.register(username, STATICPASSWORD)
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: " + username)
            self.failureCheck(result,\
                          ['Invalid Username - Illegal Characters Used',\
                          '13000'])
            self.infoFailCheck(result, username, '', STATICPASSWORD)
            

    def test_withInappropriateContent(self):
        '''Username contains inappropriate content -- TC13 (Bug 841)'''
        f = open(PROFANE_FILE, 'r')
        profaneList = f.readlines()
        f.close()
        
        self.fail("Bug 556")
        for username in profaneList:
            username = username.strip()
            result = self.toolBox.register(username, STATICPASSWORD)
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
            self.failureCheck(result,\
                              [['Invalid Username - Inappropriate Content', '13001']])
            self.infoFailCheck(result, username, '', STATICPASSWORD)
    
    
    def test_existingGameAccount(self):
        '''Username is unavailable (already registered with same GameAcctId) -- TC14'''
        username, _ = self.toolBox.registerNewUsername(email=EMAIL, password=STATICPASSWORD)
        result = self.toolBox.register(username, STATICPASSWORD, EMAIL)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['The game account already exists',\
                          '13023'])
        self.infoFailCheck(result, username, EMAIL, STATICPASSWORD)
        
        
    def test_existingGameAccountNoEmail(self):
        '''Username is unavailable (already registered with same GameAcctId), no email -- TC15'''
        username, _ = self.toolBox.registerNewUsername(email=None, password=STATICPASSWORD)
        result = self.toolBox.register(username, STATICPASSWORD)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['The game account already exists',\
                          '13023'])
        self.infoFailCheck(result, username, '', STATICPASSWORD)
            
            
    def test_unavailableUsername(self):
        '''Username is unavailable (different GameAcctId) -- TC16'''
        username, _ = self.toolBox.registerNewUsername(email=EMAIL, password=STATICPASSWORD)
        result = self.toolBox.register(username, STATICPASSWORD, EMAIL, gameUserId='9999999999999999')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Invalid Username - Username Not Available',\
                          '13002'])
        self.infoFailCheck(result, username, EMAIL, STATICPASSWORD)
        self.assertTrue('extraInfo' in result['errors']['error'], "XML structure failed, missing extraInfo" + str(result))
        delineate = re.compile(',')
        suggestions = delineate.split(str(result['errors']['error']['extraInfo']))
        
        for i in suggestions :
            suggestionresult = self.toolBox.register(i, STATICPASSWORD, EMAIL)
            self.assertTrue(suggestionresult.httpStatus() == 200,\
                        "http status code: " + str(suggestionresult.httpStatus()))
            self.successCheck(suggestionresult)
            self.infoSuccessCheck(suggestionresult, i, EMAIL)
            
            
    def test_unavailableUsernameNoEmail(self):
        '''Username is unavailable (different GameAcctId) -- TC17'''
        username, _ = self.toolBox.registerNewUsername(email=None, password=STATICPASSWORD)
        result = self.toolBox.register(username, STATICPASSWORD, gameUserId='9999999999999999')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ['Invalid Username - Username Not Available',\
                          '13002'])
        self.infoFailCheck(result, username, '', STATICPASSWORD)
        self.assertTrue('extraInfo' in result['errors']['error'], "XML structure failed, missing extraInfo" + str(result))
        delineate = re.compile(',')
        suggestions = delineate.split(str(result['errors']['error']['extraInfo']))
        
        for i in suggestions :
            suggestionresult = self.toolBox.register(i, STATICPASSWORD)
            self.assertTrue(suggestionresult.httpStatus() == 200,\
                        "http status code: " + str(suggestionresult.httpStatus()))
            self.successCheck(suggestionresult)
            self.infoSuccessCheck(suggestionresult, i, '')
        
    
    def test_bannedEmailAddress(self):
        '''Email address which has been banned -- TC18'''
        #Unknown: banned email address
        result = self.toolBox.register(STATICUSERNAME, STATICPASSWORD, 'test@binkmail.com')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", email address: test@binkmail.com")
        self.failureCheck(result,\
                          ['Email Restricted',\
                          '13010'])
        self.infoFailCheck(result, STATICUSERNAME, 'test@binkmail.com', STATICPASSWORD)
        
    
    def test_usernameTooLong(self):
        '''Username with too many characters -- TC19'''
        result = self.toolBox.register("abcdefghijklmnopqrstuvwxyzabcdefg", STATICPASSWORD)

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: abcdefghijklmnopqrstuvwxyzabcdefg")
        self.failureCheck(result,\
                          ['Invalid Username - Incorrect length Used',\
                          '13014'])
        self.infoFailCheck(result, "abcdefghijklmnopqrstuvwxyzabcdefg", '', STATICPASSWORD)
    
    
    def test_usernameTooShort(self):
        '''Username with too few characters -- TC20'''
        result = self.toolBox.register("uwxyz", STATICPASSWORD)
       
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: uwxyz")
        self.failureCheck(result,\
                          ['Invalid Username - Incorrect length Used',\
                          '13014'])
        self.infoFailCheck(result, "uwxyz", '', STATICPASSWORD)
    
    
    def test_inappropriateIllegalUsername(self):
        '''Username with both profanity and illegal characters -- TC21'''
        result = self.toolBox.register("fuck&*", STATICPASSWORD, EMAIL)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          [['Invalid Username - Illegal Characters Used', '13000'], 
                          ['Invalid Username - Inappropriate Content', '13000']])
        self.infoFailCheck(result, "fuck&*", EMAIL, STATICPASSWORD)
        
        
    def test_nonmatchingTitleCode(self):
        '''A Title Code which is not "KFPW" -- TC22'''
        self.toolBox.setTitleCodeParam('somejunk')           
        result = self.toolBox.register(STATICUSERNAME, STATICPASSWORD)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, STATICUSERNAME, '', STATICPASSWORD, titleCode='somejunk')
        
        
    def test_emptyTitleCode(self):
        '''A Title Code which is "" -- TC23'''
        self.toolBox.setTitleCodeParam('')           
        result = self.toolBox.register(STATICUSERNAME, STATICPASSWORD)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, STATICUSERNAME, '', STATICPASSWORD, titleCode='')
        
        
    def test_missingTitleCode(self):
        '''Without a Title Code -- TC24'''
        self.toolBox.setTitleCodeParam(None)           
        result = self.toolBox.register(STATICUSERNAME, STATICPASSWORD)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result,\
                          ["Not enough parameters to satisfy request", '4000'])
        self.infoFailCheck(result, STATICUSERNAME, '', STATICPASSWORD, titleCode='')
        
    def test_validInfoReferralValuesCheck(self):
        '''Valid username, password, email address and referral values -- TC25'''
        username, result = self.toolBox.registerNewUsername(referralUrl=REFERRALURL, referrer=REFERRALVALUE)
        referralUrlAddress = result['user']['referralUrl']
        referrerValue = result['user']['referrer']
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))
        self.successCheck(result, "referralParametersCheck")
        self.infoSuccessCheck(result, username, username+'@brainquake.com', parameterCheck="referralvaluescheck", referralUrl=referralUrlAddress, referrer=referrerValue)
        self.toolBox.scriptOutput("register valid account", {"username": username, 'id': result['user']['id']})
    
    def test_invalidReferralValuesCheck(self):
        '''Pass invalid url and valid referrer value -- TC26'''
        InvalidUrl = "99999999"
        _, result = self.toolBox.registerNewUsername(email=None, referralUrl=InvalidUrl, referrer=REFERRALVALUE)
        s1 = result['errors']['error']['parameters']
        Pattern = re.search("(username=)(\w+)(&titleCode)", s1)
        username = Pattern.group(2)
        gameUserId = self.toolBox.getGameIdFromUser(username) 
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(result, '15002', 'The URL is invalid')
        self.parameterValuesCheck(result, username, gameUserId, InvalidUrl, REFERRALVALUE)
                             
    def test_emptyReferrerValueCheck(self):
        '''Pass valid url and empty referrer value -- TC27'''
        emptyReferrer = ''
        _, result = self.toolBox.registerNewUsername(email=None, referralUrl=REFERRALURL, referrer=emptyReferrer)
        
        s1 = result['errors']['error']['parameters']
        Pattern = re.search("(username=)(\w+)(&titleCode)", s1)
        username = Pattern.group(2)
        gameUserId = self.toolBox.getGameIdFromUser(username) 
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(result, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(result, username, gameUserId, REFERRALURL, emptyReferrer)
    
    def test_emptyReferralUrlValueCheck(self):
        '''Pass valid referral value and empty url  -- TC28'''
        emptyReferrerUrl = ''
        _, result = self.toolBox.registerNewUsername(email=None, referralUrl=emptyReferrerUrl, referrer=REFERRALVALUE)
        
        s1 = result['errors']['error']['parameters']
        Pattern = re.search("(username=)(\w+)(&titleCode)", s1)
        username = Pattern.group(2)
        gameUserId = self.toolBox.getGameIdFromUser(username) 
             
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(result, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(result, username, gameUserId, emptyReferrerUrl, REFERRALVALUE)    
        
    # Helper Methods #    
    def successCheck(self, result, referralvaluescheck=None):
        '''Checks for success cases'''
        #check success case XML structure
        self.assertTrue('id' in result['user'], "XML structure failed, no id" + str(result))
        self.assertTrue('user' in result, "XML structure failed, no user" + str(result))
        self.assertTrue('username' in result['user'], "XML structure failed, no username" + str(result))
        self.assertTrue('verifiedAdult' in result['user'], "XML structure failed, no verifiedAdult"  + str(result))
        self.assertTrue('chatType' in result['user'], "XML structure failed, no chatType" + str(result))
        self.assertTrue('gameNewsletterEnabled' in result['user'], "XML structure failed, no gameNewsletterEnabled"  + str(result))
        self.assertTrue('emailAddress' in result['user'], "XML structure failed, no emailAddress" +str(result))
        self.assertTrue('gameUserId' in result['user'], "XML structure failed, no gameUserId"  + str(result))
        self.assertTrue('accountReminderEnabled' in result['user'], "XML structure failed, no accountReminderEnabled"  + str(result))
        self.assertTrue('friendFinderEnabled' in result['user'], "XML structure failed, no friendFinderEnabled" + str(result))
        self.assertFalse('errors' in result['user'], "Success case returned an error structure" + str(result))
        if referralvaluescheck == "referralParametersCheck":
            self.assertTrue('referralUrl' in result['user'], "XML structure failed, no referralUrl" + str(result))
            self.assertTrue('referrer' in result['user'], "XML structure failed, no referrer" + str(result))
            
            
        
        
    def infoSuccessCheck(self, result, username, email, gameUserId='', parameterCheck=None, referralUrl=None, referrer=None):
        '''Checks that the information passed by a success is the same as the information given'''
        #check success case information returned
        self.assertEqual(result['user']['username'], username,\
                                "Username received does not match username submitted:" + username + " " + result['user']['username'])
        self.assertEqual(result['user']['emailAddress'], email,\
                                "Email address received does not match email address submitted: " + email + " " + result['user']['emailAddress'])
        self.assertTrue(result['user']['id'] != -1 and result['user']['id'] != 0, "Id returned a strange result: " + str(result['user']['id']))
        self.assertEqual(result['user']['verifiedAdult'], 'false', "verifiedAdult returned True")
        self.assertTrue(result['user']['chatType'] == 'CANNED', "chatType did not return Canned: " + str(result['user']['chatType']))
        self.assertEqual(result['user']['gameNewsletterEnabled'], 'false', "gameNewsletter returned True")
        if not gameUserId:
            self.assertEqual(result['user']['gameUserId'], self.toolBox.getGameIdFromUser(username), "GameUserId does not match calculated Id. Returned: " + result['user']['gameUserId'])
        else:
            self.assertEqual(result['user']['gameUserId'], gameUserId, "GameUserId does not match. Returned: " + result['user']['gameUserId'])
        if parameterCheck != None:
            self.assertEqual(result['user']['referralUrl'], referralUrl,\
                                "referral Url received does not match url address submitted:" + referralUrl + " " + result['user']['referralUrl'])
            self.assertEqual(result['user']['referrer'], referrer,\
                                "referrer value does not match value submitted:" + referrer + " " + result['user']['referrer'])
        
        self.assertEqual(result['user']['accountReminderEnabled'], 'false', "accountReminderEnabled returned True")
        self.assertEqual(result['user']['friendFinderEnabled'], 'true', "friendFinderEnabled returned False")
        
    
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure before performing isInstance check
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        if isinstance(result['errors']['error'], list) :
            self.multipleFailureCheck(result, expected)
        else :
            self.singleFailureCheck(result, expected)
    
    
    def singleFailureCheck(self, result, expected) :
        '''Checks for failure cases with one error message'''
        # Check for error xml structure
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('user' in result, "Fail case returned a success structure")
        
        #catches for list within list structure - required for multipleFailureCheck crossover cases.  If not list within list, structure should always have even lengths because of message/code pairs.
        if len(expected) == 1:
            expected = expected[0]
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def multipleFailureCheck(self, result, expected):
        '''Checks for multiple failure cases'''
        # Check for error xml structure
        self.assertFalse('user' in result, "Fail case returned a success structure")
        for i in result['errors']['error'] :
            self.assertTrue('code' in i, "XML structure failed, no code")
            self.assertTrue('message' in i, "XML structure failed, no message")
            self.assertTrue('parameters' in i, "XML structure failed, parameters")
        
        # Checks for messages
        for j in expected :
            messagePresent = False
            codePresent = False
            for i in result['errors']['error'] :
                if i['message'] == j[0] :
                    messagePresent = True
                if i['code'] == j[1] : 
                    codePresent = True
            self.assertTrue(messagePresent, "Couldn't find the expected error message: " + str(result['errors']['error']))
            self.assertTrue(codePresent, "Couldn't find the error code expected: " + str(result['errors']['error']))
        
    
    def infoFailCheck(self, result, username, email, password, gameUserId=1, titleCode='KFPW'):
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        if isinstance(result['errors']['error'], list) :
            self.multipleInfoFailCheck(result, username, email, password, gameUserId, titleCode)
        else :
            self.singleInfoFailCheck(result, username, email, password, gameUserId, titleCode)
        
        
    def singleInfoFailCheck(self, result, username, email, password, gameUserId, titleCode):
        '''Checks that the information passed is equal to the information given for one error message'''
        #check for certain characters used in the "Illegal characters" test - these are used as delineators, and their presence will throw off the param to dict function.  
        #Removes the username and replaces it after the param string is translated to a dictionary.  This action will only succeed if the username is returned fully.
        if '&' in username or '=' in username :
            convertedString = ''
            remover = re.compile(username)
            convertedList = remover.split(result['errors']['error']['parameters'])
            for j in convertedList :
                convertedString += j
            parameters = self.toolBox.httpParamToDict(convertedString)
            parameters['username'] = username
        else :
            parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        #Checks for information
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs: " + str(result))
        self.assertTrue(parameters['username'] == username, "Username returned not equal to username given: " + username + " " + str(parameters))
        self.assertTrue(parameters['password'] == password, "Password returned not equal to password given: " + password + " " + str(parameters))
        if parameters.has_key('emailAddress') :
            self.assertTrue(parameters['emailAddress'] == email, "Email returned not equal to email given: " + email + " " + str(parameters))
        else :
            self.assertTrue(email == '', "Email returned not equal to email given: " + email + " " + str(parameters))
        self.assertTrue(parameters['service'] == "register", "Service returned not equal to service called: register " + str(parameters))
        if 'titleCode' in parameters :
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))
        self.assertTrue('gameUserId' in parameters, "GameUserId missing from parameters string: " + str(parameters))
        #if the gameUserId is expected to be empty, the default value can be overwritten in the call to force the "if" case.  Otherwise, falls to the "else"
        if gameUserId == '':
            self.assertTrue(parameters['gameUserId'] == '', "GameUserId did not return null when null was passed: " + str(parameters))
        else :
            self.assertTrue(parameters['gameUserId'] != '', "GameUserId returned null when null was not passed: " + str(parameters))
    
    
    def multipleInfoFailCheck(self, result, username, email, password, gameUserId, titleCode):
        '''Checks that the information passed is equal to the information given for multiple error messages'''
        #check for certain characters used in the "Illegal characters" test - these are used as delineators, and their presence will throw off the param to dict function.  
        #Removes the username and replaces it after the param string is translated to a dictionary.  This action will only succeed if the username is returned fully.
        for i in result['errors']['error'] :
            if '&' in username or '=' in username :
                convertedString = ''
                remover = re.compile(username)
                convertedList = remover.split(i['parameters'])
                for j in convertedList :
                    convertedString += j
                parameters = self.toolBox.httpParamToDict(convertedString)
                parameters['username'] = username
            else :
                parameters = self.toolBox.httpParamToDict(i['parameters'])
            
            #check for information
            self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs: " + str(result))
            self.assertTrue(parameters['username'] == username, "Username returned not equal to username given: " + username + " " + str(parameters))
            self.assertTrue(parameters['password'] == password, "Password returned not equal to password given: " + password + " " + str(parameters))
            if parameters.has_key('emailAddress') :
                self.assertTrue(parameters['emailAddress'] == email, "Email returned not equal to email given: " + email + " " + str(parameters))
            else :
                self.assertTrue(email == '', "Email returned not equal to email given: " + email + " " + str(parameters))
            self.assertTrue(parameters['service'] == "register", "Service returned not equal to service called: register " + str(parameters))
            if titleCode == None:
                self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
            else:
                self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))
            self.assertTrue('gameUserId' in parameters, "GameUserId missing from parameters string: " + str(parameters))
            #if the gameUserId is expected to be empty, the default value can be overwritten in the call to force the "if" case.  Otherwise, falls to the "else"
            if gameUserId == '':
                self.assertTrue(parameters['gameUserId'] == '', "GameUserId did not return null when null was passed: " + str(parameters))
            else :
                self.assertTrue(parameters['gameUserId'] != '', "GameUserId returned null when null was not passed: " + str(parameters))
                
                
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')   


    def parameterValuesCheck(self, result, username, gameUserId, referralUrl, referrer,
                              titleCode = "KFPW", email = None, password="password"):
                                  
        str = ""
        str += "username=" + username
        str += "&titleCode=" + titleCode 
        str += "&referralUrl=" + referralUrl
        str += "&service=register"
        str += "&referrer=" + referrer
        if email != None:
            str += "&emailAddress=" + email
        str += "&gameUserId=" + gameUserId
        str += "&password=" + password
        self.assertEqual(result['errors']['error']['parameters'], str, 'parameters not matching')   
           
        
        
        
        