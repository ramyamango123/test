#Parent Account Registration (Post)
#Includes both positive and negative test cases.
#dependency on validateUsername for some cases
#Created by Tarja Rechsteiner on 12.03.09.

import sys
import string
import random
import re

from testSuiteBase import TestSuiteBase

PROFANE_FILE = 'testSuites/data/profane.txt'
USERNAME_FILE = 'testSuites/data/illegal_usernames.txt'

class ParentAccountRegistration(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()


    def test_validAgeInfo(self):
        '''Valid information for Age registration -- TC1 '''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE',
                                                        emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, [parentUsername, childUsername+'@brainquake.com', parentUserId],
                              [childUsername, childUserId], '1980-10-10')
        self.toolBox.scriptOutput("parentAccountRegistration Age Gate", {"childUsername": childUsername, 'ChildId': childUserId, "parentUsername": parentUsername, "parentUserId": parentUserId})
        
    
    def test_validEmailInfo(self):
        '''Valid information for Email registration -- TC2'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.successCheck(result)
        self.infoSuccessCheck(result, [parentUsername, childUsername+'@brainquake.com', parentUserId],
                              [childUsername, childUserId], '')
        self.toolBox.scriptOutput("parentAccountRegistration Age Gate", {"childUsername": childUsername, 'ChildId': childUserId, "parentUsername": parentUsername, "parentUserId": parentUserId})
        
        
        
    def test_missingInfo(self):
        '''Missing parameters -- TC3'''
        result = self.toolBox.blankPost('parentAccountRegistration')
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingParentUserId(self):
        '''Without the parentUserId parameter -- TC4'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'password': 'password', 'validationType': 'AGE',
                                        'emailAddress': 'tester@brainquake.com', 'birthDate': '1970-01-01', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])   
    
        
    def test_missingUsername(self):
        '''Without the parentUsername parameter -- TC5'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'GameUserId': '000', 'password': 'password', 'validationType': 'AGE',
                                        'emailAddress': 'tester@brainquake.com', 'birthDate': '1970-01-01', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingPassword(self):
        '''Without the password parameter -- TC6'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'validationType': 'AGE',
                                        'emailAddress': 'tester@brainquake.com', 'birthDate': '1970-01-01', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_missingValidationType(self):
        '''Without the validationType parameter -- TC7'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'emailAddress': 'tester@brainquake.com', 'birthDate': '1970-01-01', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_ageMissingEmailAddress(self):
        '''Age validation without the email parameter -- TC8'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'validationType': 'AGE', 'birthDate': '1970-01-01', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_ageMissingBirthDate(self):
        '''Age validation without the birthdate parameter -- TC9'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'validationType': 'AGE', 'emailAddress': 'tester@brainquake.com', 
                                        'childUsername': 'testerKid', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_ageMissingChildUsername(self):
        '''Age without the child username parameter -- TC10'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'validationType': 'AGE', 'emailAddress': 'tester@brainquake.com', 
                                        'birthDate': '1970-01-01', 'childPassword': 'password'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_ageMissingChildPassword(self):
        '''Age without the child password parameter -- TC11'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'validationType': 'AGE', 'emailAddress': 'tester@brainquake.com', 
                                        'birthDate': '1970-01-01', 'childUsername': 'testerKid'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_emailMissingValidationType(self):
        '''Email validation without the validationType parameter -- TC12'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'token': '8394234237853284390345'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        
        
    def test_emailMissingToken(self):
        '''Email validation without the token parameter -- TC13'''
        result = self.toolBox.blankPost('parentAccountRegistration', {'username': 'tester', 'GameUserId': '000', 'password': 'password',
                                        'validationType': 'EMAIL'})
        self.assertTrue(result.httpStatus() == 400,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000']) 
        

    def test_emptyParentUserId(self):
        '''Empty parentUserId parameter -- TC14'''
        result = self.toolBox.parentAccountRegistration('', 'tester', 'password', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='testerKid', childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, 'AGE', ['tester', 'password', '', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['testerKid', 'password'])
        
        
    def test_emptyUsername(self):
        '''Empty parentUsername parameter -- TC15'''
        result = self.toolBox.parentAccountRegistration('000', '', 'password', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='testerKid', childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, 'AGE', ['', 'password', '000', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['testerKid', 'password'])
    
        
    def test_emptyPassword(self):
        '''Empty password parameter -- TC16'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', '', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='testerKid', childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'AGE', ['tester', '', '000', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['testerKid', 'password'])
        
        
    def test_emptyValidationType(self):
        '''Empty validationType parameter -- TC17'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', '',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='testerKid', childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, '', ['tester', 'password', '000', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['testerKid', 'password'])
        
        
    def test_ageEmptyEmailAddress(self):
        '''Age validation with empty email parameter -- TC18'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', 'AGE',
                                                        emailAddress='', birthDate='1980-10-10', childUsername='testerKid', childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'AGE', ['tester', 'password', '000', ''],
                           birthDate='1980-10-10', childInfo=['testerKid', 'password'])
        
        
    def test_ageEmptyBirthDate(self):
        '''Age validation with empty birthdate parameter -- TC19'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='', childUsername='testerKid', childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'AGE', ['tester', 'password', '000', 'tester@brainquake.com'],
                           birthDate='', childInfo=['testerKid', 'password'])
        
        
    def test_ageEmptyChildUsername(self):
        '''Age with empty child username parameter -- TC20'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='', childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'AGE', ['tester', 'password', '000', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['', 'password'])
        
        
    def test_ageEmptyChildPassword(self):
        '''Age with empty child password parameter -- TC21'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', 'AGE',
                                                        emailAddress='tester@brainquake.com', birthDate='1980-10-10', childUsername='testerKid', childPassword='', token='')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'AGE', ['tester', 'password', '000', 'tester@brainquake.com'],
                           birthDate='1980-10-10', childInfo=['testerKid', ''])
        
        
    def test_emailEmptyValidationType(self):
        '''Email validation with empty validationType parameter -- TC22'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', '', token='000')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, '', ['tester', 'password', '000'],
                               token='000')
        
        
    def test_emailEmptyToken(self):
        '''Email validation with empty token parameter -- TC23'''
        result = self.toolBox.parentAccountRegistration('000', 'tester', 'password', 'EMAIL', token='')
        
        self.assertTrue(result.httpStatus() == 499,\
                    "http status code: " + str(result.httpStatus()) + ", result " + str(result))
        self.failureCheck(result, ['Parameter values are empty for the request', '4003']) 
        self.infoFailCheck(result, 'EMAIL', ['tester', 'password', '000'],
                               token='')
        
        
    def test_ageInvalidUsername(self):
        '''Age validation with illegal characters username -- TC24'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        f = open(USERNAME_FILE, 'r')
        usernameList = f.readlines()
        f.close()
        
        for username in usernameList:
            username = username.strip()
            parentUserId = self.toolBox.getGameIdFromUser(username)
            result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: " + username)
            self.failureCheck(result, ['Invalid Username - Illegal Characters Used', '13000'])    
            self.infoFailCheck(result, 'AGE', [username, 'password', parentUserId, childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_emailInvalidUsername(self):
        '''Email validation with illegal characters username -- TC25'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        f = open(USERNAME_FILE, 'r')
        usernameList = f.readlines()
        f.close()
        
        for username in usernameList:
            username = username.strip()
            parentUserId = self.toolBox.getGameIdFromUser(username)
            result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
            
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: " + username)
            self.failureCheck(result, ['Invalid Username - Illegal Characters Used', '13000'])    
            self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId],
                               token=token)
            
            
    def test_ageInappropriateUsername(self):
        '''Age validation with inappropriate username (Bug #556) -- TC26'''
        self.fail("Bug 556")
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        
        f = open(PROFANE_FILE, 'r')
        usernameList = f.readlines()
        f.close()
        for username in usernameList:
            username = username.strip()
            parentUserId = self.toolBox.getGameIdFromUser(username)
            result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: " + username)
            self.failureCheck(result, ['Invalid Username - Inappropriate Content', '13001']) 
            self.infoFailCheck(result, 'AGE', [username, 'password', parentUserId, childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_emailInappropriateUsername(self):
        '''Email validation with inappropriate username (Bug #556) -- TC27'''
        self.fail("Bug 556")
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        f = open(PROFANE_FILE, 'r')
        usernameList = f.readlines()
        f.close()
        
        for username in usernameList:
            username = username.strip()
            parentUserId = self.toolBox.getGameIdFromUser(username)
            result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
            self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + ", username: " + username)
            self.failureCheck(result, ['Invalid Username - Illegal Characters Used', '13000'])    
            self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId],
                               token=token)
            
            
    def test_ageInvalidTitleCodeParams(self):
        '''Invalid title code for Age validation -- TC28'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Title code does not match any records', '17002'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'], titleCode='somejunk')
    
    
    def test_emailInvalidTitleCodeParams(self):
        '''Invalid title code for Email validation -- TC29'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Title code does not match any records', '17002'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId],
                               token=token, titleCode='somejunk')
        
        
    def test_ageEmptyTitleCodeParams(self):
        '''Empty title code for age validation -- TC30'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'], titleCode='')


    def test_emailEmptyTitleCodeParams(self):
        '''Empty title code for Email validation -- TC31'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId],
                               token=token, titleCode='') 
 
        
    def test_ageUnavailableAccount(self):
        '''Previously registered username/account ID combo for age validation -- TC32'''
        #register the first time
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        #register the second time.  New childname to prevent potential multiple errors
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['The game account already exists', '13023'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_emailUnavailableAccount(self):
        '''Previously registered username/account ID combo for email validation -- TC33'''
        #register the first time
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        #register the second time
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['The game account already exists', '13023'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId], token=token)         
    
    
    def test_ageUnavailableUsername(self):
        '''Previously registered username, not account ID for age validation -- TC34'''
        #register the first time
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        #register the second time.  New childname to prevent potential multiple errors
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        result = self.toolBox.parentAccountRegistration('9999999999999999', parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Username - Username Not Available', '13002'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', '9999999999999999', childUsername+'@brainquake.com'],
                               birthDate='1980-10-10', childInfo=[childUsername, 'password'])
        
        delineate = re.compile(',')
        suggestions = delineate.split(str(result['errors']['error']['extraInfo']))
        for username in suggestions :
            suggestionresult = self.toolBox.validateUsername(username)
            self.assertTrue(suggestionresult.httpStatus() == 200,\
                        "http status code: " + str(suggestionresult.httpStatus()))
            self.assertFalse('errors' in suggestionresult, "errors found in validation of suggestions: " + str(suggestionresult))
            self.assertTrue('success' in suggestionresult, "errors found in validation of suggestions: " + str(suggestionresult))
        
    
    def test_emailUnavailableUsername(self):
        '''Previously registered username, not account ID for email validation -- TC35'''
        #register the first time
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        #register the second time
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        username1 = self.createParentUsername()
        parentUserId1 = self.toolBox.getGameIdFromUser(username1)
        result = self.toolBox.parentAccountRegistration(parentUserId1, username, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Username - Username Not Available', '13002'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId1], token=token)

        delineate = re.compile(',')
        suggestions = delineate.split(str(result['errors']['error']['extraInfo']))
        for username in suggestions :
            suggestionresult = self.toolBox.validateUsername(username)
            self.assertTrue(suggestionresult.httpStatus() == 200,\
                        "http status code: " + str(suggestionresult.httpStatus()))
            self.assertFalse('errors' in suggestionresult, "errors found in validation of suggestions: " + str(suggestionresult))
            self.assertTrue('success' in suggestionresult, "errors found in validation of suggestions: " + str(suggestionresult))
    
    
    def test_ageUsernameTooShort(self):
        '''age validation username less than 6 characters -- TC36'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = 'abcde'
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['Invalid Username - Incorrect length Used', '13014']) 
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
     
    def test_emailUsernameTooShort(self):
        '''email validation username less than 6 characters -- TC37'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = 'asdf'
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Username - Incorrect length Used', '13014'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId], token=token)
        
        
    def test_ageUsernameTooLong(self):
        '''age validation username greater than 32 characters -- TC38'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = 'abcdefghijklmnopqrstuvwxyzabcdefg'
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['Invalid Username - Incorrect length Used', '13014'])   
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_emailUsernameTooLong(self):
        '''email validation username greater than 32 characters -- TC39'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = 'abcdefghijklmnopqrstuvwxyzabcdefg'
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Username - Incorrect length Used', '13014'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId], token=token)
    
    
    def test_ageInvalidValidationType(self):
        '''Age parameters - Invalid validationType -- TC40'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'SOMEJUNK', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['Adult Validation Type not supported', '13016'])   
        self.infoFailCheck(result, 'SOMEJUNK', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_emailInvalidValidationType(self):
        '''Email parameters - Invalid validationType -- TC41'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        childId = regResult['user']['id']
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'SOMEJUNK', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Adult Validation Type not supported', '13016'])
        self.infoFailCheck(result, 'SOMEJUNK', [username, 'password', parentUserId], token=token)
    
    
    def test_invalidChild(self):
        '''Unregistered child -- TC42'''
        childUsername = 'abcde'
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress='test@binkmail.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['Invalid Child - Username does not match any records', '13015'])   
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, 'test@binkmail.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_youngBirthDate(self):
        '''Invalid age -- TC43'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE',
                                                        emailAddress=childUsername+'@brainquake.com', birthDate='2000-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['User is not an Adult', '13017'])   
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='2000-10-10', childInfo=[childUsername, 'password'])
    
    
    def test_childAlreadyParented(self):
        '''Child who's already linked -- TC44'''
        #register the first time
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        #register the second time.  New childname to prevent potential multiple errors
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Child - Child has already been assigned to a parent', '13019'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])

    
    def test_emailChildAlreadyParented(self):
        '''email validation of child who's already linked to a parent through Age validation -- TC45'''
        #register the first time
        childUsername, regResult = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        childId = regResult['user']['id']
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        #register the second time
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        token = self.toolBox.getRegisterTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Child - Child has already been assigned to a parent', '13019'])
        self.infoFailCheck(result, 'EMAIL', [parentUsername, 'password', parentUserId], token=token)
        
        
    def test_emailTokenTwice(self):
        '''email validation of child who's already linked to a parent through email validation -- TC46'''
        #register the first time
        childUsername, regResult = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        childId = regResult['user']['id']
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        token = self.toolBox.getRegisterTokenFromDb(childId)
        self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'EMAIL', token=token)
        
        #register the second time
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Child - Child has already been assigned to a parent', '13019'])
        self.infoFailCheck(result, 'EMAIL', [parentUsername, 'password', parentUserId], token=token)
        
        
    def test_emailNonexistentToken(self):
        '''email validation with an non-existent token -- TC47'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        token = '-1'
        
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Validation Token does not exist', '13021'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId], token=token)
        
        
    def test_emailInvalidToken(self):
        '''email validation with an invalid token -- TC48'''
        childUsername, regResult = self.toolBox.registerNewUsername()
        childId = regResult['user']['id']
        username = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(username)
        token = self.toolBox.getChatTokenFromDb(childId)
        result = self.toolBox.parentAccountRegistration(parentUserId, username, 'password', 'EMAIL', token=token)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Validation Token is invalid', '13022'])
        self.infoFailCheck(result, 'EMAIL', [username, 'password', parentUserId], token=token)    
    
    
    def test_childPasswordMismatch(self):
        '''Wrong child password -- TC49'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress=childUsername+'@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='a')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Invalid Child - Password does not match child record', '13018'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, childUsername+'@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'a'])
    
    
    def test_childEmailMismatch(self):
        '''Wrong child email address -- TC50'''
        childUsername, _ = self.toolBox.registerNewUsername()
        childUserId = self.toolBox.getGameIdFromUser(childUsername)
        parentUsername = self.createParentUsername()
        parentUserId = self.toolBox.getGameIdFromUser(parentUsername)
        result = self.toolBox.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE', \
                                            emailAddress='none@brainquake.com', birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Invalid Child - Parent email does not match child's email", '13020'])
        self.infoFailCheck(result, 'AGE', [parentUsername, 'password', parentUserId, 'none@brainquake.com'],
                           birthDate='1980-10-10', childInfo=[childUsername, 'password'])
    
    
    def createParentUsername(self):
        '''Generates a parent username and uses validateUsername to assure validity'''
        c = 0
        characters = string.letters + string.digits
        parentUsername = ''
        while (not parentUsername) or c > 100 or 'errors' in result :
            parentUsername = ''
            for i in range(10):
                parentUsername += random.choice(characters)
            result = self.toolBox.validateUsername(parentUsername)
            c += 1
        self.assertFalse('errors' in result, "parent username registration failed: " + str(result))
        return parentUsername
            
        
    def successCheck(self, result):
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
        self.assertTrue('childAccounts' in result['user'], "XML structure failed, no childAccounts" + str(result))
        self.assertTrue('userBrief' in result['user']['childAccounts'], "XML structure failed, no userBrief" + str(result))
        self.assertTrue('username' in result['user']['childAccounts']['userBrief'], "XML structure failed, no child username" + str(result))
        self.assertTrue('gameUserId' in result['user']['childAccounts']['userBrief'], "XML structure failed, no child gameUserId" + str(result))
        self.assertTrue('id' in result['user']['childAccounts']['userBrief'], "XML structure failed, no child id" + str(result))
        self.assertFalse('errors' in result['user'], "Success case returned an error structure" + str(result))
        
        
    def infoSuccessCheck(self, result, parentInfo, childInfo, birthDate) :
        '''Checks that the general information passed by a success is the same as the information given'''
        #parentInfo is a list in this form: username, emailAddress, gameUserId.
        #parent level info check
        self.assertTrue(result['user']['id'] != -1 and result['user']['id'] != 0, "Id returned a strange result: " + str(result['user']['id']))
        self.assertEqual(result['user']['verifiedAdult'], 'true', "verifiedAdult returned False")
        self.assertTrue(result['user']['chatType'] == 'FULL', "chatType did not return Full: " + str(result['user']['chatType']))
        self.assertEqual(result['user']['gameNewsletterEnabled'], 'false', "gameNewsletter returned True")
        self.assertEqual(result['user']['accountReminderEnabled'], 'false', "accountReminderEnabled returned True")
        self.assertEqual(result['user']['friendFinderEnabled'], 'true', "friendFinderEnabled returned False")
        #childInfo is a list in this form: username, gameUserId
        self.assertEqual(result['user']['childAccounts']['userBrief']['username'], childInfo[0],\
                        "Child Username received does not match child username submitted:" + childInfo[0] + " " + str(result))
        self.assertEqual(result['user']['childAccounts']['userBrief']['gameUserId'], childInfo[1],\
                        "Child UserId received does not match Child UserId submitted:" + childInfo[1] + " " + str(result))
        self.assertTrue(result['user']['childAccounts']['userBrief']['id'] != -1 and result['user']['childAccounts']['userBrief']['id'] != 0, 
                        "Child id returned a strange result:" + str(result))
        
        self.assertEqual(result['user']['username'], parentInfo[0],\
                        "Username received does not match username submitted:" + parentInfo[0] + " " + str(result))
        self.assertEqual(result['user']['emailAddress'], parentInfo[1],\
                        "Email address received does not match email address submitted: " + parentInfo[1] + " " + str(result))
        self.assertEqual(result['user']['gameUserId'], parentInfo[2], "GameUserId does not match calculated Id. Returned: " + result['user']['gameUserId'])
        
        
    def failureCheck(self, result, expected) :
        '''Checks for failure cases in the XML structure'''
        # Check for error xml structure
        innerResult = result['errors']['error']
        if isinstance(innerResult, list) :
            #multiple error message case - list inside the dictionary
            messageCount = 0
            codeCount = 0
            for errors in innerResult :
                self.assertTrue('code' in errors, "XML structure failed, no code")
                self.assertTrue('message' in errors, "XML structure failed, no message")
                self.assertTrue('parameters' in errors, "XML structure failed, parameters")
                self.assertFalse('user' in errors, "Fail case returned a success structure")
                if errors['message'] == expected[0] :
                    messageCount += 1
                if errors['code'] == expected[1] :
                    codeCount += 1
            self.assertEqual(messageCount, 1, "Expected error message not found.  Found: " + str(result))
            self.assertEqual(codeCount, 1, "Expected error code not found.  Found: " + str(result))
        else :
            #single error message case - dictionaries all the way down
            self.assertTrue('code' in innerResult, "XML structure failed, no code")
            self.assertTrue('message' in innerResult, "XML structure failed, no message")
            self.assertTrue('parameters' in innerResult, "XML structure failed, parameters")
            self.assertFalse('user' in innerResult, "Fail case returned a success structure")
            self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
            self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
            
            
            
    def infoFailCheck(self, result, validationType, parentInfo, birthDate='', childInfo=[], token='', titleCode="KFPW") :
        '''Checks that the information passed is equal to the information given'''
        #parentInfo is a list of the form [username, password, emailAddress, gameUserId, birthdate]
        #childInfo is a list of the form [childUsername, childPassword]
        innerResult = result['errors']['error']
        if isinstance(innerResult, list) :
            for errors in innerResult :
                self.singleInfoFailCheck(errors['parameters'], validationType, parentInfo, birthDate=birthDate, childInfo=childInfo, token=token, titleCode=titleCode)
        else :
            self.singleInfoFailCheck(result['errors']['error']['parameters'], validationType, parentInfo, birthDate=birthDate, childInfo=childInfo, token=token, titleCode=titleCode)
    
    
    def singleInfoFailCheck(self, result, validationType, parentInfo, birthDate='', childInfo=[], token="", titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for 1 error message'''
        if '&' in parentInfo[0] or '=' in parentInfo[0] :
            convertedString = ''
            remover = re.compile(parentInfo[0])
            convertedList = remover.split(result)
            for j in convertedList :
                convertedString += j
            parameters = self.toolBox.httpParamToDict(convertedString)
            parameters['username'] = parentInfo[0]
        else :
            parameters = self.toolBox.httpParamToDict(result)
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs: " + str(result))
        self.assertTrue(parameters['validationType'] == validationType, "validationType returned not equal to ValidationType given: " + validationType + " " + str(parameters))
        #parentInfo
        self.assertTrue(parameters['username'] == parentInfo[0], "Username returned not equal to username given: " + parentInfo[0] + " " + str(parameters))
        self.assertTrue(parameters['password'] == parentInfo[1], "Password returned not equal to password given: " + parentInfo[1] + " " + str(parameters))
        self.assertTrue(parameters['gameUserId'] == parentInfo[2], "GameUserId returned not equal to username given: " + parentInfo[2] + " " + str(parameters))
        #ChildInfo
        if validationType == 'AGE':
            self.assertTrue(parameters['childUsername'] == childInfo[0], "childUsername returned not equal to childUsername given: " + childInfo[0] + " " + str(parameters))
            self.assertTrue(parameters['childPassword'] == childInfo[1], "childPassword returned not equal to childPassword given: " + childInfo[1] + " " + str(parameters))
            self.assertTrue(parameters['birthDate'] == birthDate, "birthDate returned not equal to birthdate given: " + birthDate + " " + str(parameters))
            self.assertTrue(parameters['emailAddress'] == parentInfo[3], "Email returned not equal to username given: " + parentInfo[3] + " " + str(parameters))
        elif validationType == 'EMAIL':
            self.assertTrue(parameters['token'] == token, "token returned not equal to token given: " + token + " " + str(parameters))
        self.assertTrue(parameters['service'] == "parentAccountRegistration", "Service returned not equal to service called: parentAccountRegistration " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))