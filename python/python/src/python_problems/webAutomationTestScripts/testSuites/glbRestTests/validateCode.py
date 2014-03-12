#Passes userId, titleCode, and code strings to the valudateCode web service.
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 11.18.09.

import sys
import random
import string
import re

from testSuiteBase import TestSuiteBase

STATICUSERNAME = 'staticuser123'
PASSWORD = 'password'
EMAIL = STATICUSERNAME + "@brainquake.com"
#GOODTIMECODE = 'f53e3723-4410-45be-8fff-f17a4f531347'
#ILOVEKUNGFUCODE = '980f1bee-54ff-4c49-b9d8-d5705cc5cab7'
INACTIVEKEY = '0013-0A7A-5BB6-85A3'
DWAKEY = '0003ccf2c8fb6c08'
userid = "56767675657"
REDEEMKEY1 = "005d228aa3b2d89c"
REDEEMKEY2 = "006eda5fbbe2539e"
PROMOTIONCODE = 'SummerSplash0625'
KUNGFUWUSHUSKILLCODE = '0708a8ed-a976-11df-a8fb-68e649bacbc8'
GOODTIMECODECOUPON = '9ecbef75-581d-4810-affb-e31eb838a15e'

class ValidateCode(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()

    def test_userTimeItem(self):
        '''Both time and item -- TC1'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        result = self.toolBox.validateCode("GOODTIMECODE", gameAcctId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.successCheck(result, GOODTIMECODECOUPON, 'ITEM', '144000')
    
    
    def test_userJustTime(self):
        '''Just time -- TC2'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        result = self.toolBox.validateCode("JUSTTIME", gameAcctId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        #structure check
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('type' in result['coupon'], "No type found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in success case")
        #values check
        self.assertEqual('1296000', result['coupon']['time'], "Time value is not 1296000")
        
        
    def test_userSkill(self):
        '''Validating userSkill id (KUNGFUWUSHUSKILL) -- TC3'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        
        result = self.toolBox.validateCode("KUNGFUWUSHUSKILL", gameAcctId)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.successCheck(result, KUNGFUWUSHUSKILLCODE, 'SKILL', time='432000')
    
    
    def test_withoutUserTimeItem(self):
        '''Valid coupon for both time and item, no user -- TC4'''
        result = self.toolBox.validateCode("GOODTIMECODE")
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.successCheck(result, GOODTIMECODECOUPON, 'ITEM', '144000')
    
    
    def test_withoutUserJustTime(self):
        '''Valid coupon for time, no user -- TC5'''
        result = self.toolBox.validateCode("JUSTTIME")
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        #structure check
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in success case")
        #values check
        self.assertEqual('1296000', result['coupon']['time'], "Time value is not 1296000")
        
        
    def test_validPromoCodeWithUserId(self):
        '''Valid promotion code with user id -- TC6'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        
        result = self.toolBox.validateCode(PROMOTIONCODE, gameAcctId)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        #structure check
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in success case")
        #values check
        self.assertEqual('86400', result['coupon']['time'], "Time value is not 86400")
        
        
    def test_validPromoCodeNoUserId(self):
        '''Valid promotion code without user id -- TC7'''
        result = self.toolBox.validateCode(PROMOTIONCODE)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        #structure check
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in success case")
        #values check
        self.assertEqual('86400', result['coupon']['time'], "Time value is not 86400")
    
    
    def test_missingParameters(self):
        '''Missing parameters -- TC8'''
        result = self.toolBox.blankPost('validateCode')
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ["Not enough parameters to satisfy request", '4000'])
                        
    
    def test_bothEmptyParameters(self):
        '''Both parameters empty -- TC9'''
        result = self.toolBox.validateCode('', '')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))        
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, '', '')
        
        
    def test_emptyCoupon(self):
        '''Empty coupon -- TC10'''
        result = self.toolBox.validateCode('')

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))        
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, '')
        
        
    def test_emptyUserId(self):
        '''Empty (optional) userid parameter -- TC11'''
        result = self.toolBox.validateCode('JUSTTIME', '')
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))       
        self.successCheckForTimeOnlyCode(result, "justtime", "1296000")
        
        
    def test_nonexistantCoupon(self):
        '''Invalid coupon code -- TC12'''
        result = self.toolBox.validateCode('SOMEJUNK')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))       
        self.failureCheck(result, ["Key or Coupon doesn't exist", '16020'])
        self.infoFailCheck(result, 'SOMEJUNK')
        
        
    def test_invalidUserId(self):
        '''Invalid UserId -- TC13'''
        result = self.toolBox.validateCode('JUSTTIME', 'abcd')
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title Account does not match any records", '17003'])
        self.infoFailCheck(result, "JUSTTIME", 'abcd')
        
        
    def test_inactiveCode(self):
        '''Inactive coupon code -- TC14'''
        result = self.toolBox.validateCode("NOGOOD")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Coupon is inactive.", '16021'])
        self.infoFailCheck(result, "NOGOOD")
        
    
    def test_notMatchingTitleId(self):
        '''Dreamworks id -- TC15'''
        result = self.toolBox.validateCode("DREAMWORKSCODE")

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ["Coupon doesn't match title.", '16022'])
        self.infoFailCheck(result, "DREAMWORKSCODE")
        
    
    def test_notYetStarted(self):
        '''Coupon code that is not yet valid -- TC16'''
        result = self.toolBox.validateCode("EARLYCODE")

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Coupon redemption occurs before start time.", '16024'])
        self.infoFailCheck(result, "EARLYCODE")
        
        
    def test_pastEndDate(self):
        '''Coupon code that is not yet valid -- TC17'''
        result = self.toolBox.validateCode("PASTCODE")

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Coupon redemption occurs after end time.", '16025'])
        self.infoFailCheck(result, "PASTCODE")      

        
    def test_noRedemption(self):
        '''Coupon code that has already been redeemed -- TC18'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        self.toolBox.redeemCode(gameAcctId, "NOREDEEMCODE")
        result = self.toolBox.validateCode("NOREDEEMCODE", gameAcctId)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Coupon exceeds redemption limit.", '16023'])
        self.infoFailCheck(result, "NOREDEEMCODE", gameAcctId)   

        
    def test_nonmatchingTitleCode(self):
        '''Title Code which is not KFPW -- TC19'''
        self.toolBox.setTitleCodeParam('somejunk')           
        result = self.toolBox.validateCode("JUSTTIME")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, "JUSTTIME", titleCode="somejunk")   
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC20'''
        self.toolBox.setTitleCodeParam('')           
        result = self.toolBox.validateCode("JUSTTIME")
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, "JUSTTIME", titleCode="")   
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_validKeyJustTime(self):
        '''Valid key information with just time -- TC21'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        
        key = self.toolBox.getLatestConsumedKeyFromDb()
        result = self.toolBox.validateCode(key, gameAcctId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.failureCheck(result, ["Key is consumed.", '16002'])
        self.infoFailCheck(result, key, gameAcctId)
        
        
    def test_validKeyJustTimeNoUser(self):
        '''Valid key information with just time, no userId passed -- TC22'''
        key = self.toolBox.getLatestConsumedKeyFromDb()
        result = self.toolBox.validateCode(key)
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        #structure check
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('time' in result['coupon'], "no time found")
        
        #values check
        self.assertEqual('43200', result['coupon']['time'], "Time value is not 43200")
        
        
    def test_validKeyUnconsumed(self):
        '''Valid unconsumed key information -- TC23'''
        key = self.toolBox.getLatestUnconsumedKeyFromDb()
        result = self.toolBox.validateCode(key)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertFalse('errors' in result, "Errors found in result")
        
        
    def test_validKeyUnconsumedWithUserId(self):
        '''Valid unconsumed key information with userid -- TC24'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        key = self.toolBox.getLatestUnconsumedKeyFromDb()
        
        result = self.toolBox.validateCode(key, gameAcctId)
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertFalse('errors' in result, "Errors found in result")
        
        
    def test_inactiveKey(self):
        '''Inactive key with userid -- TC25'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        #key = self.toolBox.getLatestInactiveKeyFromDb()
        
        result = self.toolBox.validateCode(INACTIVEKEY, gameAcctId)

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Key is inactive.", '16001'])
        self.infoFailCheck(result, INACTIVEKEY, gameAcctId)
        
        
    def test_inactiveKeyWithoutUserId(self):
        '''Inactive key without userid -- TC26'''
        
        result = self.toolBox.validateCode(INACTIVEKEY)
        #key = self.toolBox.getLatestInactiveKeyFromDb()
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()) + " " + str(result))
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertFalse('errors' in result, "Errors found in result")
        
        
    def test_notMatchingTitleKey(self):
        '''Key for DWA instead of KFPW -- TC27'''
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        
        key = self.toolBox.getLatestDreamworksKeyFromDb()
        result = self.toolBox.validateCode(key, gameAcctId)

        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))            
        self.failureCheck(result, ["Key doesn't match title.", '16003'])
        self.infoFailCheck(result, key, gameAcctId)
          
    def test_noRedemptionKey(self):
        '''Key code that's already been redeemed. Redemption limit is set to 1 -- TC28
           So after every test run go to QA DB, execute the sql query for test key code - 005d228aa3b2d89c 
           and change its account field values (date_consumed, consumed_account_id, account_title_id) to NULL
           SQL query required for this action is listed below '''
           #SELECT * FROM account_key WHERE account_key_group_id = 33 AND account_key_value = "005d228aa3b2d89c" 
        
        username, result = self.toolBox.registerNewUsername()
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        
        redeemCodeResult = self.toolBox.redeemCode(gameAcctId, REDEEMKEY1)
        self.assertTrue("success" in redeemCodeResult, "success tag not found")
        
        resultDict = self.toolBox.validateCode(REDEEMKEY2, gameAcctId)
        self.assertTrue(resultDict.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(resultDict, ["KeyType redemption limit has been met already", '16005'])
        self.infoFailCheck(resultDict, REDEEMKEY2, gameAcctId)  
      
      
      # Helper Methods # 
      
    def registerStaticUser(self, username=STATICUSERNAME, password=PASSWORD, email=EMAIL, maxTries=100):
        '''Attempts to create a new user.
        if user already registered this method failed.
        After user created, for that user Game account 
        Id retrieved by calling the helper method 
        getGameIdFromUser from toolBox.
        This game Id used for all the tests
        '''
        
        result = self.toolBox.register(username, email, password)
        print result
        self.assertTrue(('errors' in result) or ('user' in result), "XML structure for register is failed")
        gameId = self.toolBox.getGameIdFromUser(username)
        return gameId
 
         
    def successCheck(self, result, itemId, name, time='360'):
        '''Checks for expected structure/value for a GOODCODE return'''
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('listItems' in result['coupon'], "No listItems found " + str(result))
        self.assertTrue('item' in result['coupon']['listItems'], "No item found")
        self.assertTrue('id' in result['coupon']['listItems']['item'], "No item id found")
        self.assertTrue('quantity' in result['coupon']['listItems']['item'], "No item quantity found")
        self.assertTrue('name' in result['coupon']['listItems']['item'], "No name found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in result")
        
        #values check
        self.assertEqual(itemId, result['coupon']['listItems']['item']['id'], "Item id is not " + str(itemId) + ": " + str(result))
        self.assertEqual(name, result['coupon']['listItems']['item']['name'], "Item name is not " + str(name) + ": " + str(result))
        self.assertEqual('1', result['coupon']['listItems']['item']['quantity'], "Item quantity is not 1")
        self.assertEqual(time, result['coupon']['time'], "Time value is not " + str(time))

    def successCheckForTimeOnlyCode(self, result, type, time):
        '''Checks for expected structure/value for a GOODCODE return'''
        self.assertTrue('coupon' in result, "No coupon found")
        self.assertTrue('time' in result['coupon'], "no time found")
        self.assertFalse('errors' in result, "errors found in result")
        
        #values check
        self.assertEqual(type, result['coupon']['type'], "type is not " + str(type))
        self.assertEqual(time, result['coupon']['time'], "Time value is not " + str(time))
        
    def failureCheck(self, result, expected) :
        '''Determines whether there are multiple error messages or not and calls appropriate helper method'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('coupon' in result, "Fail case returned a success structure")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, code, userId=None, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['code'] == code, "Code returned not equal to Code given: " + code + " " + str(parameters))
        if userId == None:
            self.assertFalse('userId' in parameters, "userId not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['userId'] == userId, "UserId returned not equal to userId given: " + userId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "validateCode", "Service returned not equal to service called: validateCode " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: KFPW " + str(parameters))
            