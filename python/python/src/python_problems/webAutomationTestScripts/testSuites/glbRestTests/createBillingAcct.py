'''Create Billing Account Testsuite
Passes Game Base(platform) account Id (type - int), user information and 
KFPW game account id (type - int) to service CreateBillingAcct
This WS creates a billing account
Created by Sharmila Janardhanan Date on 12/01/2009
Updated with promotion functionality on 05/28/2010 by Ramya Nagendra'''

from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, string, time, re
import MySQLdb

PLANID = "10003936"
BILLINGTYPE = "11"
FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "address1"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIP = "94087"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"
PROMOTION = "3monthPromo"
AUTOCONSUME = "TRUE"

class CreateBillingAcct(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()
        
          
    #this commented case verified through game UI   
    # def test_invalidZipCode(self):
        # '''Pass valid information of all parameters and invalid for zipCode - TC11'''
        # username, result = self.toolBox.registerNewUsername(8)
        # self.assertTrue('user' in result, "user tag not found")
        # id = result['user']['id']
        # gameId = result['user']['gameUserId']
        # billingType = "11"
        # zip = "invalid"
        # resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                          # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, zip) 
        # self.fail("Success XML displayed bug #85 " + str(resultDict))
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        # self.parameterValuesCheck(resultDict,str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  # ADDRESS1, CITY, STATE, COUNTRY, zip, PLANID)
        # self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid zip code", "No extra info tag found")
        
    ################  
    ###     testcases      ###
    ################
        
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function - TC1'''
        resultDict = self.toolBox.blankPost('createBillingAcct')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=createBillingAcct', \
                                    'Create Billing Parameters not matched')
        
    
    def test_allEmptyValues(self):
        '''Pass all empty values to the service - TC2'''
        id = gameId = firstname = lastname = billingType = address1 = ''
        city = state = country = zip = planId = clientIpAddress = ''
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, clientIpAddress, planId,
                                                    firstname, lastname, address1, city, state, country, zip)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                  address1, city, state, country, zip, planId)
            
    
    def test_allInvalidInformation(self):
        '''Pass all invalid information to the service - TC3'''
        id = gameId = firstname = lastname = billingType = address1 = 'invalid'
        city = state = country = zip = planId = clientIpAddress = 'invalid'
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, clientIpAddress, planId,
                                                    firstname, lastname, address1, city, state, country, zip)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                  address1, city, state, country, zip, planId)
                                          
                                          
    def test_mixEmptyInvalidValues(self):
        '''Pass mixture of invalid and empty values - TC4'''
        id = gameId = firstname = lastname = billingType = address1 = ''
        city = state = country = zip = phone = planId = clientIpAddress = 'invalid'
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, clientIpAddress, planId,
                                                    firstname, lastname, address1, city, state, country, zip)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                  address1, city, state, country, zip, planId)
    
    
    def test_mixEmptyValidValues(self):
        '''Pass mixture of empty and valid values - TC5'''
        id = gameId = firstname = lastname = billingType = address1 = clientIpAddress = ''
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, clientIpAddress, PLANID,
                                                    firstname, lastname, address1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                  address1, CITY, STATE, COUNTRY, ZIP, PLANID)
                                           

    def test_mixInvalidValidValues(self):
        '''Pass mixture of invalid and valid values - TC6'''
        id = gameId = firstname = lastname = address1 = clientIpAddress = 'invalid'
        resultDict = self.toolBox.createBillingAcct(id, gameId, BILLINGTYPE, clientIpAddress, PLANID,
                                                    firstname, lastname, address1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, id, gameId, firstname, lastname, BILLINGTYPE, clientIpAddress, 
                                  address1, CITY, STATE, COUNTRY, ZIP, PLANID)
        
    
    def test_validIdInvalidGameId(self):
        '''Pass valid account id and invalid game account id - TC7'''
        id, _ = self.returnChildInfo()
        gameId = "999999999999999999999999999999999"
        billingType = "11"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)                                                              
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17016', 'The specified platform & associated title account was not found')
        self.parameterValuesCheck(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID)
        
        
    def test_invalidAccountId(self):
        '''Pass invalid account id and valid game account id - TC8'''
        _, gameId = self.returnChildInfo()
        id = "000000000000"
        billingType = "11"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(resultDict, id, gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID)
                                  
                                  
    def test_invalidStateName(self):
        '''Pass valid information of all parameters and invalid for state/ province - TC9'''
        id, gameId = self.returnChildInfo()
        billingType = "11"
        state = "invalid"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, state, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        self.parameterValuesCheck(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, state, COUNTRY, ZIP, PLANID)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "Invalid state_prov entered", "No extra info tag found")
        
        
    def test_invalidCountry(self):
        '''Pass valid information of all parameters and invalid for country - TC10'''
        id, gameId = self.returnChildInfo()
        billingType = "11"
        country = "invalid"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, country, ZIP) 
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        self.parameterValuesCheck(resultDict,str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, country, ZIP, PLANID)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid iso country code", "No extra info tag found")
        
          
    def test_invalidBillingType(self):
        '''Pass valid information of all parameters and invalid billing type - TC11'''
        id, gameId = self.returnChildInfo()
        billingType = "100"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16030', 'The billing type specified does not exist')
        self.parameterValuesCheck(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID)
        
    
    def test_invalidClientIpAddress(self):
        '''Pass invalid client IP address with all valid information - TC12'''
        id, gameId = self.returnChildInfo()
        billingType = "1"
        clientIpAddress = "invalid"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, clientIpAddress, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheckWithCC(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, clientIpAddress, 
                                        ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL)
        
    
    def test_invalidGameUrl(self):
        '''Pass invalid Game URL with all valid information - TC13'''
        id, gameId = self.returnChildInfo()
        billingType = "1"
        gameUrl = "0000000"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = gameUrl) 
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '15002', 'The URL is invalid')
        self.parameterValuesCheckWithCC(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                        ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, gameUrl)
                                        
                                        
    def test_childRegistrationWithNoEmail(self):
        '''Pass child info registered without email address - TC14'''
        username, result = self.toolBox.registerNewUsername(8, email = None)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingType = "1"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17018', 'The platform account does not have a registered email address')
        self.parameterValuesCheckWithCC(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                        ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL)
                                        
                                        
    def test_creatBillingAcctTwiceForSameAcct(self):
        '''Create billing account for a user and setup another billing again - TC15'''
        #can setup 2 master billing accounts for an user. Ref: bug #773 - functionality changed as of build #142008 
        id, gameId = self.returnChildInfo()
        #billing type "1" for credit card and optional parameters are required for CC
        billingType = "1"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL)
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        resultDict2 = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                     FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                     gameUrl = GAMEURL)
        self.errorXMLStructureCodeMessageCheck(resultDict2, '16038', 'The user already has an active master billing account')
        self.parameterValuesCheckWithCC(resultDict2, id, gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                        ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL)
            

    def test_validCreateBillingAcctForCCUsingChildInfo(self):
        '''Pass valid information to create billing account for Credit card (For child) - TC16'''
        id, gameId = self.returnChildInfo()
        #billing type "1" for credit card and optional parameters are required for CC
        billingType = "1"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL)
        billingId =  self.successCheck(resultDict)
        self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("createBillingAcct - Valid CC using Child info (1 month)", {"Billing Id": billingId})
        
    
    def test_validCreateBillingAcctForPaypalUsingChildInfo(self):
        '''Pass valid information to create billing account for Paypal (For child) - TC17'''
        id, gameId = self.returnChildInfo()
        #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        billingType = "11"
        planId = "10003937"
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, planId)                          
        billingId = self.successCheck(resultDict)
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.toolBox.scriptOutput("createBillingAcct - Valid Paypal using Child info (6 months)", {"Billing Id": billingId})
           
    
    def test_validCreateBillingAcctForCCUsingParentInfo(self):
        '''Pass valid information to create billing account for Credit card (For parent) - TC18'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        #billing type "1" for credit card and optional parameters are required for CC
        billingType = "1"
        planId = "10003938"
        resultDict = self.toolBox.createBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS, planId,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL)
        billingId =  self.successCheck(resultDict)
        self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("createBillingAcct - Valid CC using Parent info (12 months)", {"Billing Id": billingId})
        
    
    def test_validCreatBillingAcctForPaypalUsingParentInfo(self):
        '''Pass valid information to create billing account for Paypal (For parent) - TC19'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        billingType = "11"
        resultDict = self.toolBox.createBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS, PLANID)                          
        billingId = self.successCheck(resultDict)
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.toolBox.scriptOutput("createBillingAcct - Valid Paypal using Parent info", {"Billing Id": billingId})
        
        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC20'''
        id, gameId = self.returnChildInfo()
        titleCode = "somejunk"
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.createBillingAcct(id, gameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, BILLINGTYPE, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC21'''
        id, gameId = self.returnChildInfo()
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.createBillingAcct(id, gameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, str(id), gameId, FIRSTNAME, LASTNAME, BILLINGTYPE, CLIENTIPADDRESS, 
                                  ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC22'''
        id, gameId = self.returnChildInfo()
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.createBillingAcct(id, gameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'city=' + CITY + '&billingType=' + BILLINGTYPE +
                                    '&firstName=' + FIRSTNAME + '&service=createBillingAcct' + 
                                    '&lastName=' + LASTNAME + '&planId=' + PLANID + 
                                    '&zipCode=' + ZIP + '&gameAcctId=' + gameId + '&state=' + STATE + 
                                    '&clientIpAddress=' + CLIENTIPADDRESS + '&address1=' + ADDRESS1 +
                                    '&country=' + COUNTRY + '&accountId=' + str(id), \
                                    'Create Billing Parameters not matched')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_completeValidationOnSecondTryBug891(self):
        '''Complete validation on second time  - TC23'''
        id, gameId = self.returnChildInfo()
        billingType = "1"
        billingResult = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                       FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                       address2='address2', gameUrl = GAMEURL)
        billingId1 = billingResult['account']['accountId']
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL)
        billingId2 =  self.successCheck(resultDict)
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("createBillingAcct - first try without validation", {"Billing Id": billingId1})
        self.toolBox.scriptOutput("createBillingAcct - second try with validation", {"Billing Id": billingId2})

        
    def test_ariaPciProcessSessionId(self):
        '''Prints session id to test PCI action manually - TC24'''
        for i in range(1,3):
            id, gameId = self.returnChildInfo()
            billingType = "1"
            resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                           FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                           address2='address2', gameUrl = GAMEURL)
            billingId = resultDict['account']['accountId']
            sessionId = resultDict['account']['inSessionID']
            flowId = resultDict['account']['flowID']
            self.toolBox.scriptOutput("createBillingAcct - Print Session Id/ Flow Id", {"Session Id": sessionId, "Flow Id": flowId})


    def test_reSubscriptionAfterCancellation(self):
        '''Re-subscription after cancellation  - should have status as active and only one invoice in Aria (ref bug #872) - TC25'''
        id, gameId = self.returnChildInfo()
        billingType = "1"
        billingResult = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                       FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        billingId = self.successCheck(billingResult)
        token = self.toolBox.getDeactivateTokenFromDb(id)
        banResult = self.toolBox.banChildAccount(token=token)
        resultDict = self.toolBox.createBillingAcct(id, gameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17017', 'The platform account is currently banned')
        self.parameterValuesCheckWithCC(resultDict, id, gameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                        ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL)
            
    # Commenting out all "3 month promo"  test cases since this functionality is no longer supported
    # def test_validBillingAcctForCCWithValidPromotion(self):
        # '''Pass valid info to create billing account for CC with valid promo code and autoConsume - TC26'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "1" for credit card and optional parameters are required for CC
        # billingType = "1"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    # address2='address2', gameUrl = GAMEURL, promotion = PROMOTION, autoConsume = AUTOCONSUME)
        # self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        # self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        # self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        # billingId = resultDict['account']['accountId']
        # sessionId = resultDict['account']['inSessionID']
        # flowId = resultDict['account']['flowID']
        # self.ariaHostedPagePromoUrlCheck(sessionId, flowId, "&type=3monthPromo")
          
             
    # def test_validBillingAcctForCCWithInvalidPromotion(self):
        # '''Pass valid info to create billing account for CC with invalid promo code - TC27'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # billingType = "1"
        # promotion = "invalid"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    # address2='address2', gameUrl = GAMEURL, promotion = 'invalid', autoConsume = AUTOCONSUME)
        # billingId =  self.successCheck(resultDict)
        # self.toolBox.scriptOutput("createBillingAcct - Valid CC", {"Billing Id": billingId})
        # self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        # self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        # self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        # billingId = resultDict['account']['accountId']
        # sessionId = resultDict['account']['inSessionID']
        # flowId = resultDict['account']['flowID']
        # self.ariaHostedPagePromoUrlCheck(sessionId, flowId, "&type=ERROR")
        
             
    # def test_validBillingAcctForCCWithAutoConsumeFalse(self):
        # '''Pass valid info to create billing account for CC with autoConsume = False - TC28'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "1" for credit card and optional parameters are required for CC
        # billingType = "1"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    # address2='address2', gameUrl = GAMEURL, promotion = PROMOTION, autoConsume = "false")
        # billingId =  self.successCheck(resultDict)
        # billingId = resultDict['account']['accountId']
        # sessionId = resultDict['account']['inSessionID']
        # flowId = resultDict['account']['flowID']
        # self.ariaHostedPagePromoUrlCheck(sessionId, flowId, None, billingId)
             
                 
    # def test_validBillingAcctForCCWithInvalidPromoAndAutoConsume(self):
        # '''Pass valid info to create billing account for CC with invalid promo and autoConsume - TC29'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "1" for credit card and optional parameters are required for CC
        # billingType = "1"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP,
                                                    # address2='address2', gameUrl = GAMEURL, promotion = "invalid", autoConsume = "invalid")
        # billingId =  self.successCheck(resultDict)
        # self.toolBox.scriptOutput("createBillingAcct - Valid CC", {"Billing Id": billingId})
        # self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        # self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        # self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        # billingId = resultDict['account']['accountId']
        # sessionId = resultDict['account']['inSessionID']
        # flowId = resultDict['account']['flowID']
        # self.ariaHostedPagePromoUrlCheck(sessionId, flowId, "&type=ERROR")
        
        
    # def test_validBillingAcctForCCWithEmptyPromo(self):
        # '''Pass valid info to create billing account for CC with empty promo - TC30'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # billingType = "1"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    # FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    # address2='address2', gameUrl = GAMEURL, promotion='', autoConsume = AUTOCONSUME)
        # billingId =  self.successCheck(resultDict)
        # self.toolBox.scriptOutput("createBillingAcct - Valid CC", {"Billing Id": billingId})
        # self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        # self.assertTrue("validationUrl" in resultDict['account'], "Validation Url tag not found")
        # self.assertEqual(resultDict['account']['validationUrl'], "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php", "Different URL displayed")
        # billingId = resultDict['account']['accountId']
        # sessionId = resultDict['account']['inSessionID']
        # flowId = resultDict['account']['flowID']
        # self.ariaHostedPagePromoUrlCheck(sessionId, flowId, "")
        
        
    def test_validBillingAcctForCCWithEmptyAutoConsume(self):
        '''Pass valid info to create billing account for CC with empty promo - TC31'''
        username, regResultDict = self.toolBox.registerNewUsername()
        childId = regResultDict['user']['id']
        childGameId = regResultDict['user']['gameUserId']
        billingType = "1"
        resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID,
                                                    FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL, promotion = PROMOTION, autoConsume = '')
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheckEmptyAutoConsume1(resultDict, childId, childGameId, FIRSTNAME, LASTNAME, billingType, CLIENTIPADDRESS, 
                                                   ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL, promotion = PROMOTION, autoConsume = '')   
                                             
        
    def test_invalidBillingAcctForCCWithValidPromotion(self):
        '''Pass some invalid values to billing account for CC with valid promot code and autoConsume - TC32'''
        username, regResultDict = self.toolBox.registerNewUsername()
        childId = regResultDict['user']['id']
        childGameId = regResultDict['user']['gameUserId']
        firstname = lastname = address1 = clientIpAddress = 'invalid'
        billingType = "1"
        resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, clientIpAddress, PLANID,
                                                    firstname, lastname, address1, CITY, STATE, COUNTRY, ZIP, 
                                                    address2='address2', gameUrl = GAMEURL, promotion = PROMOTION, autoConsume = AUTOCONSUME)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheckEmptyAutoConsume1(resultDict, childId, childGameId, firstname, lastname, billingType, clientIpAddress, 
                                                   address1, CITY, STATE, COUNTRY, ZIP, PLANID, GAMEURL, promotion = PROMOTION, autoConsume = AUTOCONSUME)
                                              
   # Promotion scripts for pay pal flow#
    
    # def test_validBillingAcctForPaypal(self):
        # '''Pass valid info to create billing account for Paypal - TC33'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, PLANID)                          
        # billingId = self.successCheck(resultDict)
        # self.toolBox.scriptOutput("createBillingAcct - Valid Paypal", {"Billing Id": billingId})
        
    
    # def test_validBillingAcctForPaypalWithValidPromotion(self):
        # '''Pass valid info to create billing account for Paypal with valid promo code and autoConsume - TC34'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume = AUTOCONSUME)                          
        # billingType = "11"
        # billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS, PLANID)  
        # billingId = billingResult['account']['accountId']
        # paypalResult = self.toolBox.startPaypalPlan(billingId)
        # paypalToken = paypalResult['paypal']['paypalToken']
        # paypalURL = paypalResult['paypal']['returnUrl']
        # URL = paypalURL + paypalToken
        # self.acceptPaypalAgreementUsingSelenium(URL)
        # resultDict = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        # self.successCheck(resultDict)       
        # billingId = self.successCheck(resultDict)
        # self.toolBox.scriptOutput("createBillingAcct - Valid Paypal", {"Billing Id": billingId})    
        
        
    # def test_validBillingAcctForPaypalWithInvalidPromotion(self):
        # '''Pass valid information to create billing account for Paypal with invalid promo code - TC35'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # promotion = "invalid"
        # resultDict = self.toolBox.createBillingAcct(childId, childGameId, billingType, CLIENTIPADDRESS, promotion, autoConsume = AUTOCONSUME)                                       
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # #self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        # self.parameterCheckWithPromotionForPaypal(resultDict, childId, childGameId, billingType, CLIENTIPADDRESS, promotion, autoConsume = AUTOCONSUME)
        
                                                                       
    # def test_validBillingAcctForPaypalWithAutoConsumeFalse(self):
        # '''Pass valid information to create billing account for Paypal with autoConsume = False - TC36'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # autoConsume =  "false"
        # resultDict = self.toolBox.createBillingAcct(childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume)                                       
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # #self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        # self.parameterCheckWithPromotionForPaypal(resultDict, childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume)
        
        
    # def test_validBillingAcctForPaypalWithEmptyAutoConsume(self):
        # '''Pass valid information to create billing account for Paypal with invalid promo and autoConsume  - TC37'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # autoConsume = ''
        # resultDict = self.toolBox.createBillingAcct(childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume)                                       
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # #self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        # self.parameterCheckWithPromotionForPaypal(resultDict, childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume)
        
                                                                       
    # def test_validBillingAcctForPaypalWithEmptyPromo(self):
        # '''Pass valid information to create billing account for Paypal with empty promo - TC38'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = regResultDict['user']['id']
        # childGameId = regResultDict['user']['gameUserId']
        # #billing type "11" for paypal and optional parmeters (gameUrl and address info) not required
        # billingType = "11"
        # promotion = ''
        # resultDict = self.toolBox.createBillingAcct(childId , childGameId , billingType, CLIENTIPADDRESS, promotion, autoConsume = AUTOCONSUME)                                       
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # #self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        # self.parameterCheckWithPromotionForPaypal(resultDict, childId , childGameId , billingType, CLIENTIPADDRESS, promotion, autoConsume = AUTOCONSUME)
        
        
     # def test_invalidBillingAcctForPaypalWithValidPromotion(self):
        # '''Pass some invalid values to billing account for Paypal with valid promo and autoConsume - TC39'''
        # username, regResultDict = self.toolBox.registerNewUsername()
        # childId = childGameId = billingType = 'invalid'
        # resultDict = self.toolBox.createBillingAcct(childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume = AUTOCONSUME)                                       
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # #self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        # self.parameterCheckWithPromotionForPaypal(resultDict, childId , childGameId , billingType, CLIENTIPADDRESS, promotion = PROMOTION, autoConsume = AUTOCONSUME)
                                                                                                            
    
    ########################
    ###            Helper Methods          ###
    ########################
  
    def ariaHostedPage(self, sessionId, flowId):
        #using selenium to enter credit card information
        sel = self.selenium
        sel.open(r"file://///hq-fs01/dept/Dev/QA/Web/KungFuPandaWorld/Web_Services/DB/Web%20Services%20Test.html")
        sel.select("wsUrl", "label=" + str(self.toolBox.webHost))
        sel.click("//input[@value='set environment']")
        sel.wait_for_page_to_load("30000")
        sel.is_text_present("Current Environment: " + str(self.toolBox.webHost))
        sel.type("ahp_inSessionID", sessionId)
        sel.type("ahp_flowID", flowId)
        sel.click("ahp_submit")
        sel.wait_for_page_to_load("120000")
        time.sleep(2)
        sel.type("cc_number", "4111111111111111")
        sel.click("cc_expire_mm")
        sel.select("cc_expire_mm", "label=January")
        sel.click("//option[@value='1']")
        sel.click("cc_expire_yyyy")
        sel.select("cc_expire_yyyy", "label=2012")
        sel.click("//option[@value='2012']")
        sel.click("cvv")
        sel.type("cvv", "123")
        sel.click("submitButton")
        sel.wait_for_page_to_load("90000")
        self.assertEqual("Gazillion Entertainment", sel.get_title())
        
    def acceptPaypalAgreementUsingSelenium(self, URL):
        #using selenium to agree paypalPlan
        sel = self.selenium
        sel.open("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=/")
        sel.click("link=PayPal Sandbox")
        sel.wait_for_page_to_load("30000")
        #login to paypal
        sel.type("login_email", "sharmila.janardhanan@slipg8.com")
        sel.type("login_password", "password")
        sel.click("submit")
        sel.wait_for_page_to_load("30000")
        time.sleep(6)
        sel.open(URL)
        sel.wait_for_page_to_load("30000")
        time.sleep(2)
        #login to sandbox test account
        sel.type("login_email", "sharmi_1263862208_per@slipg8.com")
        sel.type("login_password", "gazillion")
        sel.click("login.x")
        sel.wait_for_page_to_load("30000")
        #self.assertEqual("Review your information - PayPal", sel.get_title())
        sel.click("continue")
        sel.wait_for_page_to_load("30000")
        #self.assertEqual("Paypal Callback", sel.get_title())
        time.sleep(1)
        
    def ariaHostedPagePromoUrlCheck(self, sessionId, flowId, type, billingId = ""):
        sel = self.selenium
        sel.open(r"file://///hq-fs01/dept/Dev/QA/Web/KungFuPandaWorld/Web_Services/DB/Web%20Services%20Test.html")
        sel.select("wsUrl", "label=" + str(self.toolBox.webHost))
        sel.click("//input[@value='set environment']")
        sel.wait_for_page_to_load("30000")
        sel.is_text_present("Current Environment: " + str(self.toolBox.webHost))
        sel.type("ahp_inSessionID", sessionId)
        sel.type("ahp_flowID", flowId)
        sel.click("ahp_submit")
        sel.wait_for_page_to_load("90000")
        time.sleep(2)
        sel.type("cc_number", "4111111111111111")
        sel.click("cc_expire_mm")
        sel.select("cc_expire_mm", "label=January")
        sel.click("//option[@value='1']")
        sel.click("cc_expire_yyyy")
        sel.select("cc_expire_yyyy", "label=2012")
        sel.click("//option[@value='2012']")
        sel.click("cvv")
        sel.type("cvv", "123")
        sel.click("submitButton")
        sel.wait_for_page_to_load("90000")
        self.assertEqual("Gazillion Entertainment", sel.get_title())
        time.sleep(2)
        if type != None:
            if type == "":
                print sel.get_location()
                self.assertEqual("http://www.gazillion.com/?action=accept" + type + "#/home", sel.get_location(), "The supplied return != the expected return: " + str(sel.get_location())) 
            elif type == "&type=ERROR":
                self.assertEqual("http://www.gazillion.com/?action=accept&promoStatus=error&errorMsg=Could%20not%20use%20promotion#/home", sel.get_location(), "The supplied return != the expected return: " + str(sel.get_location())) 
            else:
                self.assertEqual("http://www.gazillion.com/?action=accept&promoStatus=ok" + type + "#/home", sel.get_location(), "The supplied return != the expected return (valid case): " + str(sel.get_location())) 
        
        else:
            accountValue = self.getAcctKeyValueFromDb(billingId)
            accountKey = accountValue[0]
            t1 = accountKey.upper()
            pattern = re.search("(\w{4})(\w{4})(\w{4})(\w{4})", t1)
            result = pattern.group(1) + "-" + pattern.group(2) + "-" + pattern.group(3) + "-" + pattern.group(4)
            print sel.get_location()
            self.assertEqual("http://www.gazillion.com/?action=accept&promoStatus=ok&promoCode=" + result + "#/home", sel.get_location(), "The supplied return != the expected return: " + str(sel.get_location()))
            self.toolBox.scriptOutput("accountValue and location", {"account key Value": str(accountKey)})                 
           
    def returnChildInfo(self):
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        return id, gameId
        
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
    def parameterValuesCheck(self, resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, address1, city, state, 
                                     country, zip, planId, titleCode = 'KFPW', address2 = "address2"):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&city=' + city + '&billingType=' + billingType +
                                    '&firstName=' + firstname + '&service=createBillingAcct' + 
                                    '&lastName=' + lastname + '&planId=' + planId + '&zipCode=' + zip + 
                                    '&gameAcctId=' + gameId + '&state=' + state + '&clientIpAddress=' + clientIpAddress +  
                                    '&address1=' + address1 + '&country=' + country +
                                    '&accountId=' + id,  'Create Billing Parameters not matched') 

    def parameterValuesCheckWithCC(self, resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                   address1, city, state, country, zip, planId, gameUrl, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&city=' + city + '&billingType=' + billingType +
                                    '&firstName=' + firstname + '&service=createBillingAcct' + 
                                    '&lastName=' + lastname + '&planId=' + planId + '&zipCode=' + zip + 
                                    '&gameAcctId=' + gameId + '&state=' + state + '&clientIpAddress=' + clientIpAddress +  
                                    '&address1=' + address1 + '&gameUrl=' + gameUrl + '&country=' + country +
                                    '&accountId=' + id,  'Create Billing Parameters not matched')  

    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertFalse("errors" in resultDict, "Error XML displayed")
        self.assertTrue("account" in resultDict, "Account tag not found")
        self.assertTrue("accountId" in resultDict['account'], "Account Id tag not found")
        billingAcctId = resultDict['account']['accountId']
        return billingAcctId
        
    # def parameterValuesCheckEmptyAutoConsume(self, resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                   # address1, city, state, country, zip, planId, gameUrl, titleCode = 'KFPW', address2 = "address2", promotion = PROMOTION, autoConsume = ''):
        # self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    # 'titleCode=' + titleCode + '&city=' + city + '&billingType=' + billingType +
                                    # '&firstName=' + firstname + '&service=createBillingAcct' + 
                                    # '&lastName=' + lastname + '&address2=' + address2 + '&planId=' + planId + '&zipCode=' + zip + 
                                    # '&gameAcctId=' + gameId + '&autoConsume=' + autoConsume + '&state=' + state + '&clientIpAddress=' + clientIpAddress +  
                                    # '&address1=' + address1 + '&gameUrl=' + gameUrl + '&country=' + country + '&promotion=' + promotion +
                                    # '&accountId=' + id,  'Create Billing Parameters not matched')
                                    
     
    def parameterValuesCheckEmptyAutoConsume1(self, resultDict, id, gameId, firstname, lastname, billingType, clientIpAddress, 
                                              address1, city, state, country, zip, planId, gameUrl, titleCode = 'KFPW', 
                                              address2 = "address2", promotion = PROMOTION, autoConsume = ''):
        str = 'titleCode=' + titleCode + '&city=' + city + '&billingType=' + billingType 
        str += '&firstName=' + firstname + '&service=createBillingAcct' 
        str += '&lastName=' + lastname + '&address2=' + address2 + '&planId=' + planId + '&zipCode=' + zip
        str += '&gameAcctId=' + gameId + '&autoConsume=' + autoConsume + '&state=' + state + '&clientIpAddress=' + clientIpAddress 
        str += '&address1=' + address1 + '&gameUrl=' + gameUrl + '&country=' + country + '&promotion=' + promotion
        str += '&accountId=' + id
        self.assertEqual(resultDict['errors']['error']['parameters'], str, 'parameters not matching')   
                                    
    def getAcctKeyValueFromDb(self, billingId):
        '''return User Updated Email by means of Game Id'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT billing_account_promotion_key.account_key_value, billing_account_promotion_key.third_party_account_id, account.account_id  FROM billing_account_promotion_key, account
                          WHERE billing_account_promotion_key.account_id = account.account_id
                          AND third_party_account_id = "%s"'''%(billingId))
        result = cursor.fetchone()
        print "getacct value", result
        return result[0]