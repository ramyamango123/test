'''Redeem Coupon/ Key Service Testsuite
Passes game account Id (type - string), Coupon or Key code (type - string) to service 
This WS is used to redeem/consume either valid coupon or a key
Created by Sharmila Janardhanan Date on 11/18/2009'''

from testSuiteBase import TestSuiteBase
import random
STATICUSERNAME = 'x12345'
PASSWORD = 'password'
EMAIL = STATICUSERNAME + "@brainquake.com"
PROMOTIONCODE = 'UNLIMITEDCOUPON'
REDEEMKEY1 = "040CBD3EF82E31CF"
REDEEMKEY2 = "0540DB0B9051313F"
KUNGFUWUSHUSKILLCODE = '0708a8ed-a976-11df-a8fb-68e649bacbc8'
GOODTIMECODECOUPON = '9ecbef75-581d-4810-affb-e31eb838a15e'

class RedeemCouponKey(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
       
            
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function - TC1'''
        resultDict = self.toolBox.blankPost('redeemCode')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=redeemCode', \
                                    'Redeem Code Parameter value not matching')
        
                
    def test_emptyGameIdCouponCode(self):
        '''Pass empty Game account Id and coupon code to redeemCode service - TC2'''
        gameId = ''
        couponCode = ''
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, gameId, couponCode)  
      
    
    def test_invalidGameIdEmptyCouponCode(self):
        '''Pass invalid game account id and empty coupon code - TC3'''
        gameId = 'invalidgameId'
        couponCode = ''
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, gameId, couponCode)  
        
        
    def test_emptyGameIdInvalidCouponCode(self):
        '''Pass empty game account id and invalid coupon code - TC4'''
        gameId = ''
        couponCode = 'invalidcode'
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, gameId, couponCode)  
       
                   
    def test_invalidGameIdValidCouponCodeTimeAndItem(self):
        '''Pass invalid game account id and valid coupon code (time and item) - TC5'''
        gameId = 'invalidgameId'
        couponCode = "GOODTIMECODE"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, couponCode) 
                
    
    def test_invalidGameIdValidCouponCodeTimeOnly(self):
        '''Pass invalid game account id and valid coupon code (time only) - TC6'''
        gameId = 'invalidgameId'
        couponCode = "JUSTTIME"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, couponCode) 
        
        
    def test_invalidGameIdInactiveCouponCode(self):
        '''Pass invalid game account id and inactive coupon code - TC7'''
        gameId = 'invalidgameId'
        couponCode = "NOGOOD"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, couponCode)   
       
            
    def test_invalidGameIdInvalidCouponCode(self):
        '''Pass invalid game account id and invalid coupon code - TC8'''
        gameId = 'invalidgameId'
        couponCode = "invalidcouponcode"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, couponCode)   
        
    
    def test_invalidGameIdValidKeyCode(self):
        '''Pass invalid game account id and valid Key Code - TC9'''
        gameId = 'invalidgameId'
        keyCode = self.toolBox.getLatestUnconsumedKeyFromDb()
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, keyCode) 
        
        
    def test_invalidGameIdInactiveKeyCode(self):
        '''Pass invalid game account id and inactive key code - TC10'''
        gameId = 'invalidgameId'
        keyCode = "2624CEADF84A1A8D"
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, keyCode)
        
        
    def test_validGameIdInvalidCouponCode(self):
        '''Pass valid game account id and invalid coupon code - TC11'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "invalidcouponcode"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16020', 'Key or Coupon doesn\'t exist')
        self.parameterValuesCheck(resultDict, gameId, couponCode)   
       
       
    def test_validGameIdInactiveCouponCode(self):
        '''Pass valid game account id and inactive coupon code - TC12'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "NOGOOD"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16021', 'Coupon is inactive.')
        self.parameterValuesCheck(resultDict, gameId, couponCode)   
        
        
    def test_invalidGameIdInvalidKeyCode(self):
        '''Pass invalid game account id and invalid key code - TC13'''
        gameId = 'invalidgameId'
        keyCode = 'invalidkey'
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17003', 'Title Account does not match any records')
        self.parameterValuesCheck(resultDict, gameId, keyCode)  
        
        
    def test_validGameIdInvalidKeyCode(self):
        '''Pass valid game account id and invalid key code - TC14'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        keyCode = 'invalidkey'
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16020', 'Key or Coupon doesn\'t exist')
        self.parameterValuesCheck(resultDict, gameId, keyCode)  
        
        
    def test_validGameIdValidCouponCodeTimeAndItem(self):
        '''Pass valid game account id and valid coupon code (time and item) - TC15'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "GOODTIMECODE"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.successCheck(resultDict)
        self.assertEqual(resultDict['session']['type'], 'GOODTIMECODECOUPON', 'Key type not matched')

        
    # def test_validGameIdValidCouponCodeTimeOnly(self):
        # '''Pass valid game account id and valid coupon code (time only) - TC16'''
        # childUsername, childRegResult = self.toolBox.registerNewUsername()
        # id = childRegResult['user']['id']
        # gameId = childRegResult['user']['gameUserId']
        # couponCode = "justtime"
        # resultDict = self.toolBox.redeemCode(gameId, couponCode)
        # self.toolBox.scriptOutput("redeemCouponKey - Valid Time only Coupon", {"Child Id": id})
        # self.successCheck(resultDict)
        # self.assertEqual(resultDict['session']['type'], 'justtime', 'Key type not matched')
    
        
    def test_validGameIdValidCouponCodeSkill(self):
        '''Pass valid game account id and valid coupon code (skill) one user can consume once - TC17'''
        childUsername, childRegResult = self.toolBox.registerNewUsername()
        id = childRegResult['user']['id']
        gameId = childRegResult['user']['gameUserId']
        couponCode = "KUNGFUWUSHUSKILL"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        #Writing output to a text file with service name, test case name and the value of the output
        self.toolBox.scriptOutput("redeemCouponKey - Valid Skill Coupon", {"Child Id": id})
        self.successCheck(resultDict)
        self.assertEqual(resultDict['session']['type'], 'KUNGFUWUSHUSKILLCODE', 'Key type not matched')
    
       
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC18'''
        gameId = self.registerStaticUser()
        couponCodes = ["JUSTTIME", "GOODCODE"]
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)
        for couponCode in couponCodes:
            resultDict = self.toolBox.redeemCode(gameId, couponCode)
            self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
            self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
            self.parameterValuesCheck(resultDict, gameId, couponCode, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC19'''
        gameId = self.registerStaticUser()
        couponCodes = ["JUSTTIME", "GOODCODE"]
        titleCode = ""
        self.toolBox.setTitleCodeParam("")     
        for couponCode in couponCodes:        
            resultDict = self.toolBox.redeemCode(gameId, couponCode)
            self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
            self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
            self.parameterValuesCheck(resultDict, gameId, couponCode, titleCode) 
        self.toolBox.setTitleCodeParam('KFPW') 
        

    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC20'''
        gameId = self.registerStaticUser()
        couponCodes = ["JUSTTIME", "GOODCODE"]
        self.toolBox.setTitleCodeParam(None)   
        for couponCode in couponCodes:        
            resultDict = self.toolBox.redeemCode(gameId, couponCode)
            self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
            self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
            self.assertEqual(resultDict['errors']['error']['parameters'], \
                                        'code=' + couponCode +
                                        '&userId=' + gameId + '&service=redeemCode', \
                                        'Redeem code parameter value not matching') 
        self.toolBox.setTitleCodeParam('KFPW')
    
    
    def test_couponTitleNotMatching(self):
        '''Pass valid coupon code which does not matches with title code KFPW - TC21'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "DREAMWORKSCODE"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16022', 'Coupon doesn\'t match title.')       
        self.parameterValuesCheck(resultDict, gameId, couponCode)  
        
        
    def test_redeemBeforeStartTime(self):
        '''Redeem a coupon before the start time - TC22'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "EARLYCODE"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16024', 'Coupon redemption occurs before start time.')
        self.parameterValuesCheck(resultDict, gameId, couponCode)   
        
        
    def test_redeemAfterEndTime(self):
        '''Redeem a coupon which has expired - TC23'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "PASTCODE"
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16025', 'Coupon redemption occurs after end time.')     
        self.parameterValuesCheck(resultDict, gameId, couponCode)  
           
               
    def test_redemptionLimitOverCoupon(self):
        '''Redeem a coupon which has redemption limit over - TC24'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        couponCode = "NOREDEEMCODE"
        result = self.toolBox.redeemCode(gameId, couponCode)
        self.toolBox.validateCode("NOREDEEMCODE", gameId)
        resultDict = self.toolBox.redeemCode(gameId, couponCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16023', 'Coupon exceeds redemption limit.') 
        self.parameterValuesCheck(resultDict, gameId, couponCode) 
          

    def test_validGameIdValidKeyTimeOnly(self):
        '''Redeem a key, generated by time only coupon - TC25'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        #cosumed key from db
        keyCode = self.toolBox.getLatestConsumedKeyFromDb()
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16002', 'Key is consumed.')
        self.parameterValuesCheck(resultDict, gameId, keyCode)
        
        
    def test_inActiveKey(self):
        '''Passing inactive key - TC26'''
        #Create a  new batch in Gman with type key only and inactive
        
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        keyCode = "00130A7A5BB685A3"
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16001', 'Key is inactive.')
        self.parameterValuesCheck(resultDict, gameId, keyCode)
        
    
    def test_keyTitleNotMatching(self):
        '''Passing not matching titlecode key - TC27'''
        #Create a  new batch in Gman with type dreamworks code
        
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        keyCode = "0003ccf2c8fb6c08"
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16003', "Key doesn't match title.")
        self.parameterValuesCheck(resultDict, gameId, keyCode)
         
         
    # def test_redemptionLimitOverKey(self):
        # #Create a  new batch in Gman with type No redeem
        # '''Passing redemption limit over key - TC28
        # Key code that's already been redeemed. Redemption limit is set to 1.
        # So after every test run go to QA DB, execute the sql query for test key code - 040CBD3EF82E31CF
        # and change its account field values (date_consumed, consumed_account_id, account_title_id) to NULL
        # SQL query required for this action is listed below'''
        # #SELECT * FROM account_key WHERE account_key_group_id = 33 AND account_key_value = "040CBD3EF82E31CF" 
               
        # username, result = self.toolBox.registerNewUsername()
        # gameId = self.toolBox.getGameIdFromUser(username)
        # resultDict = self.toolBox.redeemCode(gameId, REDEEMKEY1)
        # print resultDict
        # resultDict = self.toolBox.redeemCode(gameId, REDEEMKEY2)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '16005', "KeyType redemption limit has been met already")
        # self.parameterValuesCheck(resultDict, gameId, keyCode)
    
    
    def test_notConsumedKey(self):
        '''Pass valid game id and not consumed key - TC29'''
        #create a new type and batch in Gman with type valid keys
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        keyCode = self.toolBox.getLatestUnconsumedKeyFromDb()
        resultDict = self.toolBox.redeemCode(gameId, keyCode)
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not found")
        self.assertTrue("value" in resultDict['success'], "Value tag not found")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')
        
        
    def test_validGameIdValidPromoCode(self):
        '''Pass valid game account id and valid promo code - TC30'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        resultDict = self.toolBox.redeemCode(gameId, PROMOTIONCODE)
        print resultDict
        # self.successCheck(resultDict)
        # self.assertEqual(resultDict['session']['type'], PROMOTIONCODE, 'Key type not matched')
        
    def test_validGameIdValidPromoCode(self):
        '''Pass valid game account id and valid promo code - TC30'''
        username, result = self.toolBox.registerNewUsername()
        gameId = self.toolBox.getGameIdFromUser(username)
        resultDict = self.toolBox.redeemCode(gameId, 'PROMOCODE')
        self.successCheck(resultDict)
        self.assertEqual(resultDict['session']['type'], 'PROMOCODE', 'Key type not matched')
        
    ########################
    ###            Helper Methods          ###
    ########################    
     
    def registerStaticUser(self, username=STATICUSERNAME, password=PASSWORD, email=EMAIL, maxTries=100):
        '''Attempts to create a new user.
        if user already registered this method failed.
        After user created, for that user Game account 
        Id retrieved by calling the helper method 
        getGameIdFromUser from toolBox.
        This game Id used for all the tests
        
        @username
        @param  password
        @param  email
        '''
        email = username + '@brainquake.com'
        result = self.toolBox.register(username, email, password)
        self.assertTrue(('errors' in result) or ('user' in result), "XML structure for register is failed")
        gameId = self.toolBox.getGameIdFromUser(username)
        return gameId
        
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')

    def parameterValuesCheck(self, resultDict, id, code, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode +
                                    '&code=' + code +
                                    '&userId=' + id + '&service=redeemCode', \
                                    'Redeem Code Parameter value not matching')    
            
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("session" in resultDict, "session tag not found")
        self.assertTrue("type" in resultDict['session'], "Type tag not found")    
     
    