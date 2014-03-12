#Update Coin Balance (POST)
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 1.6.10.

import sys
import urllib2
import re

from testSuiteBase import TestSuiteBase

ADDRESS = "http://webapp01.itg.kfp.sjc.adm.gazint:8080/webservices-1.0-SNAPSHOT/avatars"
ADDRESSBASE = "http://webapp01.itg.kfp.sjc.adm.gazint:8080/webservices-1.0-SNAPSHOT/users?avatars.id="

AVATARID = '3075578'
USERID = '1249972'
MISMATCHUSERID = '321'

class UpdateCoinBalance(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        
        
    def test_validInfoAddCoins(self):
        '''Valid information adding coins -- TC1'''
        #currently using forced avatarId/userId so I'm not changing random coin balances...
        result = self.toolBox.updateCoinBalance(AVATARID, '100', USERID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))    
        self.assertTrue('success' in result, "No success found in result: " + str(result))
        self.assertTrue('value' in result['success'], "No value found in success: " + str(result))
        self.assertTrue(result['success']['value'], "Success didn't return TRUE: " + str(result))
        
        
    def test_validInfoTakeCoins(self):
        '''Valid information removing coins -- TC2'''
        #currently using forced avatarId/userId so I'm not changing random coin balances...
        result = self.toolBox.updateCoinBalance(AVATARID, '-50', USERID)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))            
        self.assertTrue('success' in result, "No success found in result: " + str(result))
        self.assertTrue('value' in result['success'], "No value found in success: " + str(result))
        self.assertTrue(result['success']['value'], "Success didn't return TRUE: " + str(result))
        
        
    def test_avatarUserIdMismatch(self):
        '''Avatar and user ids which do not match -- TC3'''
        #when hooked up dynamically, take userId and subtract 1?
        #for the moment, make an account after the main account for this userId
        nonUserId = int(USERID) - 1
        result = self.toolBox.updateCoinBalance(AVATARID, '100', MISMATCHUSERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Avatar Id is not in our records.', '17013'])
        self.infoFailCheck(result, AVATARID, '100', MISMATCHUSERID)
        
        
    def test_invalidInfoAvatarId(self):
        '''Invalid information - AvatarId -- TC4'''
        result = self.toolBox.updateCoinBalance('-1', '100', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Avatar Id is not in our records.', '17013'])
        self.infoFailCheck(result, '-1', '100', USERID)
        
        
    def test_invalidInfoCoinAmount(self):
        '''Invalid information - coinAmount -- TC5'''
        result = self.toolBox.updateCoinBalance(AVATARID, 'asdf', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Request could not be processed', '499'])
        self.infoFailCheck(result, AVATARID, 'asdf', USERID)
        
        
    def test_invalidInfoUserId(self):
        '''Invalid information - userId -- TC6'''
        result = self.toolBox.updateCoinBalance(AVATARID, '100', '-1')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Title Account does not match any records', '17003'])
        self.infoFailCheck(result, AVATARID, '100', '-1')
        
        
    def test_invalidTitleCode(self):
        '''Invalid Title Code -- TC7'''
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.updateCoinBalance(AVATARID, '100', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, AVATARID, '100', USERID, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyAvatarId(self):
        '''Empty AvatarId -- TC8'''
        result = self.toolBox.updateCoinBalance('', '100', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, '', '100', USERID)
        
        
    def test_emptyCoinAmount(self):
        '''Empty coinAmount -- TC9'''
        result = self.toolBox.updateCoinBalance(AVATARID, '', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, AVATARID, '', USERID)
        
        
    def test_emptyUserId(self):
        '''Empty userId -- TC10'''
        result = self.toolBox.updateCoinBalance(AVATARID, '100', '')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, AVATARID, '100', '')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC11'''
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.updateCoinBalance(AVATARID, '100', USERID)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        self.infoFailCheck(result, AVATARID, '100', USERID, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_missingParams(self):
        '''Missing information -- TC12'''
        result = self.toolBox.blankPost('updateCoinBalance')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
    
    def test_missingAvatarId(self):
        '''Missing avatarId -- TC13'''
        result = self.toolBox.blankPost('updateCoinBalance', {'coinAmount': '100', 'gameUserId': USERID, 'titleCode': 'KFPW'})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingCoinAmount(self):
        '''Missing coinAmount -- TC14'''
        result = self.toolBox.blankPost('updateCoinBalance', {'avatarId': AVATARID, 'gameUserId': USERID, 'titleCode': 'KFPW'})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_missingUserId(self):
        '''Missing userId -- TC15'''
        result = self.toolBox.blankPost('updateCoinBalance', {'avatarId': AVATARID, 'coinAmount': '100', 'titleCode': 'KFPW'})
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_withoutTitleCode(self):
        '''Without a Title Code -- TC16'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.updateCoinBalance(AVATARID, '100', USERID)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, AVATARID, '100', USERID, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def avatarIdSearch(self):
        '''Reads in URL, searches for valid avatar id.  Grabs the last created'''
        try:
            website = urllib2.urlopen(ADDRESS)
        except urllib2.HTTPError, e:
            print "Cannot retrieve URL: HTTP Error Code", e.code
        except urllib2.URLError, e:
            print "Cannot retrieve URL: " + e.reason[1]
        
        website_text = website.readlines()
        avatarId = ''
        i = len(website_text) - 1
        while i > 0:
            website_text[i] = website_text[i].strip()
            if ("<id>" and "</id>") in website_text[i] and "</home>" in website_text[i-1]:
                avatarId = website_text[i]
                i = 0
            i -= 1
        self.assertTrue(avatarId != '', "Avatar XML structure failed, no player ids captured")
        avatarId = self.removeXmlTag(avatarId)
        return avatarId
        
            
    def removeXmlTag(self, item):
        '''Removes the xml tags from the avatar xml'''
        delineate = re.compile('<id>|</id>|<category>|</category>')
        convertedList = delineate.split(item)
        convertedString = ''
        for i in convertedList :
            convertedString += i
        return convertedString
    
    
    def retrieveUserId(self, avatarId):
        '''Retrieves the userId from GMan if given an avatar Id'''
        try:
            website = urllib2.urlopen(ADDRESSBASE+avatarId)
        except urllib2.HTTPError, e:
            print "Cannot retrieve URL: HTTP Error Code", e.code
        except urllib2.URLError, e:
            print "Cannot retrieve URL: " + e.reason[1]
              
        website_text = website.readlines()
        userId = ''
        i = len(website_text) - 2
        while i > 0:
            website_text[i] = website_text[i].strip()
            if ("<id>" and "</id>") in website_text[i] and ("<name>" and "</name>") in website_text[i+1]:
                userId = website_text[i]
                i = 0
            i -= 1
        self.assertTrue(userId != '', "Avatar XML structure failed, no player ids captured")
        userId = self.removeXmlTag(userId)
        return userId
        
        
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('success' in result, "XML structure failed, success found in error XML")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']) + " " + expected[0])
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, avatarId, coinAmount, userId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['avatarId'] == avatarId, "AvatarId returned not equal to AvatarId given: " + avatarId + " " + str(parameters))
        self.assertTrue(parameters['coinAmount'] == coinAmount, "coinAmount returned not equal to coinAmount given: " + coinAmount + " " + str(parameters))
        self.assertTrue(parameters['gameUserId'] == userId, "userId returned not equal to userId given: " + userId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "updateCoinBalance", "Service returned not equal to service called: updateCoinBalance " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))

