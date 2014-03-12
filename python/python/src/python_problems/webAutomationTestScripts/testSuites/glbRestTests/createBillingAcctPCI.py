'''Create Billing Account PCI Testsuite(POST)
This web service is part of the PCI billing flow which is called from the game 
to create new billing account. Internally it creates the new billing account, 
updates the payment method and finishes processing
Includes both positive and negative test cases.
Created by Ramya Nagendra on 08.06.2010.'''



from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, string, time, re
import MySQLdb

PLANIDMONTHLY = "10003936"
PLANID6MONTH = "10003937" 
PLANID12MONTH = "10003938"

BILLINGTYPE = "11"
FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "address1"
ADDRESS2 = "address2"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIPCODE = "94087"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"
PROMOTION = "3monthPromo"
AUTOCONSUME = "TRUE"
CC = "4111111111111111"
CVV = "123"
EXPMONTH = "09"
EXPYEAR = "2012"
TITLECODE = "KFPW"




class CreateBillingAcctPCI(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()

    def tearDown(self):
        pass

    # ############# #  
    # #     testcases      ## #
    # ############# #

        
     #This test case will be commented out until issue related to blankxmlPost (created to billing PCI complaince) API method  is resolved  
    # def test_noParametersPassedtoCCPCI(self):
        # '''No parameters passed to the Web Services function - TC1'''
        # resultDict = self.toolBox.blankxmlPost('createBillingAcct')
        # print "resultDict", resultDict
        # self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        # self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    # 'titleCode=KFPW&' + 'service=createBillingAcct', \
                                    # 'Create Billing Parameters not matched')
        # self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         # "OOPs! Your account cannot be created at this time.  Please try again.")  
                         
    def test_CreateBillingAcctForMonthlyCCPCI(self):
        '''Pass all valid information with monthly plan Id to create a billing account - TC2'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003936"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, TITLECODE, parentId, parentGameId,
                                email=email, address2=ADDRESS2, promotion=PROMOTION, autoConsume=AUTOCONSUME)
                                
        billingId =  self.successCheck(resultDict)
        self.toolBox.scriptOutput("createBillingAcctPCI - Valid CC", {"Billing Id": billingId})
        
        
    def test_CreateBillingAcctFor6MonthsCCPCI(self):
        '''Pass all valid information with 6 month plan Id to create a billing account - TC3'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003937"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID6MONTH, TITLECODE, parentId, parentGameId,
                                email=email, address2=ADDRESS2, promotion=PROMOTION, autoConsume=AUTOCONSUME)
                                
        billingId =  self.successCheck(resultDict)
        self.toolBox.scriptOutput("createBillingAcctPCI - Valid CC", {"Billing Id": billingId})
        
        
    def test_CreateBillingAcctFor12MonthsCCPCI(self):
        '''Pass all valid information with 12 month plan Id to create a billing account - TC4'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003938"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId,
                                email=email, address2=ADDRESS2, promotion=PROMOTION, autoConsume=AUTOCONSUME)
                                
        billingId =  self.successCheck(resultDict)
        self.toolBox.scriptOutput("createBillingAcctPCI - Valid CC", {"Billing Id": billingId})
        
        
    def test_CreateBillingAcctWithoutOptParamsCCPCI(self):
        '''Pass only required parameters with valid values to create a billing account - TC5'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003938"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
                               
        billingId =  self.successCheck(resultDict)
        self.toolBox.scriptOutput("createBillingAcctPCI - Valid CC", {"Billing Id": billingId})
    
                         
    def test_passEmptyNameAdressForCCPCI(self):
        '''Pass empty first name and address1 (required fields) to the service - TC6'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        firstname = ''
        address1 = ''
        resultDict = self.toolBox.createBillingAcctPCI(firstname, LASTNAME, address1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheckCCPCI(resultDict, firstname, LASTNAME, address1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH, parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    # This test case will fail until the fraud filters are turned on.
    def test_passInvalidNameAdressForBillingPCI(self):
        '''Pass invalid first name and address1 (required fields) to the service - TC7'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        firstname = '555'
        address1 = '000'
        resultDict = self.toolBox.createBillingAcctPCI(firstname, LASTNAME, address1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '26003', 'General Could not validate billing intomation')
        self.parameterValuesCheckCCPCI(resultDict, firstname, LASTNAME, address1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'Could not validate credit card.  Please re-enter card number, expiration and cvv.')   
                         
    def test_passInvalidEmailForBillingPCI(self):
        '''Pass invalid email id to the service - TC8'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = '123'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId, email=email)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '15001', 'Email address is invalid')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH,  parentId, parentGameId, TITLECODE, email=email)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')  
                         
    def test_passEmptyEmailForBillingPCI(self):
        '''Pass empty email id(optional param) to the service - TC9'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId, email=email)
        
        self.successCheck(resultDict)
        
    def test_passInvalidPromoCCPCI(self):
        '''Pass invalid promo to the service - TC10'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003937"
        promotion = 'invalidpromo'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY , TITLECODE, parentId, parentGameId,
                                email=email, address2=ADDRESS2, promotion=promotion, autoConsume=AUTOCONSUME)
                                
        billingAcctId = resultDict['account']['accountId']
        self.assertTrue('ERROR' in resultDict['account']['promotion'], "promo tag not found")           
        self.assertEqual( resultDict['account']['accountId'], billingAcctId, "Account Id tag not found") 
        
    def test_passEmptyPromoCCPCI(self):
        '''Pass empty promo code to the service - TC11'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003937"
        promotion = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY , TITLECODE, parentId, parentGameId,
                                email=email, address2=ADDRESS2, promotion=promotion, autoConsume=AUTOCONSUME)
                                
        billingId =  self.successCheck(resultDict)  
        self.assertFalse("promotion" in resultDict,  "Promo tag found")
        
    # Commenting out this test case since Promo code check functionality got removed.  
    # def test_passInvalidAutoConsumeCCPCI(self):
        # '''Pass invalid autoCosume value to the service - TC12'''
        # username, registrationResult = self.toolBox.registerNewUsername()
        # parentId = registrationResult['user']['id']
        # parentGameId = registrationResult['user']['gameUserId']
        # email = registrationResult['user']['emailAddress']
        # planId = "10003937"
        # autoConsume = "false"
        # resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                # ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                # PLANIDMONTHLY , TITLECODE, parentId, parentGameId,
                                # email=email, address2=ADDRESS2, promotion=PROMOTION, autoConsume=autoConsume)
                                
        # billingAcctId = resultDict['account']['accountId']
        # self.assertTrue('promotion' in resultDict['account'],  "promo tag not found")   
        # promoCode = self.getPromotionCodeValueFromDB(billingAcctId)
        
        # self.assertEqual(resultDict['account']['promotion'], promoCode , "promotion code doesn't match")        
        # self.assertEqual( resultDict['account']['accountId'], billingAcctId, "Account Id tag not found") 
        
    def test_passEmptyAutoConsumeCCPCI(self):
        '''Pass empty auto Consume to the service - TC13'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003937"
        autoConsume = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY , TITLECODE, parentId, parentGameId,
                                email=email, promotion=PROMOTION, autoConsume=autoConsume)
                                
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.emptyAutoConsumeParameterCheck(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANIDMONTHLY, parentId, parentGameId, TITLECODE, email = email, promotion=PROMOTION, autoConsume=autoConsume)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
    
    def test_passEmptyPromotionAutoConsumeCCPCI(self):
        '''Pass empty promotion code and auto Consume to the service - TC14'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        planId = "10003937"
        promotion = ''
        autoConsume = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY , TITLECODE, parentId, parentGameId,
                                email=email, promotion=promotion, autoConsume=autoConsume)
                                        
        billingId =  self.successCheck(resultDict)
        self.assertFalse("promotion" in resultDict, "Promo tag found")
        
    def test_passInvalidParentIdForBillingPCI(self):
        '''Pass invalid parent id (Gazillion id) to the service - TC15'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = '00000000000000000000'
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH, parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please contact Customer Service.')        
                         
    def test_passInvalidParentGameIdForBillingPCI(self):
        '''Pass invalid parent game id (Gazillion id) to the service - TC16'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = '000000000000000000'
        email = registrationResult['user']['emailAddress']
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
                
        self.errorXMLStructureCodeMessageCheck(resultDict, '17016', 'The specified platform & associated title account was not found')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH, parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please contact Customer Service.')                              
    
                              
    def test_passInvalidCCForBillingPCI(self):
        '''Pass invalid CC no (required field) to the service - TC17'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        cc = 'invalid'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, cc, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
                                
        self.errorXMLStructureCodeMessageCheck(resultDict, '26003', 'General Could not validate billing information')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PLANID12MONTH,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'Could not validate credit card.  Please re-enter card number, expiration and cvv.')    
    
    def test_passEmptyCCForBillingPCI(self):
        '''Pass empty CC no (required field) to the service - TC18'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        cc = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, cc, CVV, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', '')    
        self.ParameterSecureMessageCheck(resultDict, parentId, parentGameId)
        
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    def test_passInvalidCVVForBillingPCI(self):
        '''Pass invalid CC no (required field) to the service - TC19'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        cvv = 'abc'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, cvv, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '21016', 'General Invalid input')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, cvv, EXPMONTH, EXPYEAR, PLANID12MONTH,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')            
                         
    def test_passEmptyCVVForBillingPCI(self):
        '''Pass empty CVV no (required field) to the service - TC20'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        cvv = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, cvv, EXPMONTH, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', '')    
        self.ParameterSecureMessageCheck(resultDict, parentId, parentGameId)
        
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    def test_passInvalidexpMonthForBillingPCI(self):
        '''Pass invalid exp month (required field) to the service - TC21'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        expMonth = 'January'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, expMonth, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '21016', 'General Invalid input')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, expMonth, EXPYEAR, PLANID12MONTH,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')            
    
    def test_passEmptyExpMonthForBillingPCI(self):
        '''Pass empty expmonth (required field) to the service - TC22'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        expmonth = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, expmonth, EXPYEAR,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', '')    
        self.ParameterSecureMessageCheck(resultDict, parentId, parentGameId)
        
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    def test_passInvalidexpYearForBillingPCI(self):
        '''Pass invalid CC no (required field) to the service - TC23'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        expYear = 'invalid'
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, expYear,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '21016', 'General Invalid input')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, expYear, PLANID12MONTH,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')       
                         
    def test_passEmptyExpYearForBillingPCI(self):
        '''Pass empty expyear (required field) to the service - TC24'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        expyear = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, expyear,
                                PLANID12MONTH, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', '')    
        self.ParameterSecureMessageCheck(resultDict, parentId, parentGameId)
        
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    def test_passIncorrectPlanIdForBillingPCI(self):
        '''Pass incorrect plan id (required field) to the service - TC25'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        PlanIdMonthly = "10011625"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PlanIdMonthly, TITLECODE, parentId, parentGameId)
        
        self.errorXMLStructureCodeMessageCheck(resultDict, '16029', 'No billing plans matching the specified criteria')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PlanIdMonthly,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please contact Customer Service.')            
                         
    def test_passInvalidPlanIdForBillingPCI(self):
        '''Pass invalid plan id (required field) to the service - TC26'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        PlanIdMonthly = "00000000000"
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PlanIdMonthly, TITLECODE, parentId, parentGameId)
             
        self.errorXMLStructureCodeMessageCheck(resultDict, '16029', 'No billing plans matching the specified criteria')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PlanIdMonthly,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please contact Customer Service.')        
                        
                                
                          
    def test_passEmptyPlanIdForBillingPCI(self):
        '''Pass empty plan id (required field) to the service - TC27'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        PlanIdMonthly = ''
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PlanIdMonthly, TITLECODE, parentId, parentGameId)
                                
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheckCCPCI(resultDict, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR, PlanIdMonthly, parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')   
                         
    def test_createBillingForAlreadyExistingAcct(self):
        '''Create billing account to a user and setup another billing again - TC28'''
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, TITLECODE, parentId, parentGameId)
        
        resultDict2 = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, TITLECODE, parentId, parentGameId)
                             
        self.errorXMLStructureCodeMessageCheck(resultDict2, '16038', 'The user already has an active master billing account')
        self.parameterValuesCheckCCPCI(resultDict2, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PLANIDMONTHLY,  parentId, parentGameId, TITLECODE)
                                 
        self.assertEqual(resultDict2['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please contact Customer Service.')            
          
            
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC29 (Bug 575)'''
        self.fail("Bug 575")
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        titleCode = 'somejunk'
        self.toolBox.setTitleCodeParam(titleCode)  
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, titleCode, parentId, parentGameId)
                              
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheckCCPCI(resultDict2, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PLANIDMONTHLY,  parentId, parentGameId, titleCode)
                                  
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         "OOPs! Your account cannot be created at this time. Please contact Customer Service.",\
                         "No extra info tag found")
        self.toolBox.setTitleCodeParam('KFPW')  
        
                
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC30 (Bug 575)'''
        self.fail("Bug 575")
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)  
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, titleCode, parentId, parentGameId)
                               
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheckCCPCI(resultDict2, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PLANIDMONTHLY,  parentId, parentGameId, titleCode)
                                  
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         "OOPs! Your account cannot be created at this time. Please contact Customer Service.",\
                         "No extra info tag found")
        self.toolBox.setTitleCodeParam('KFPW')    
        
    
    def test_noTitleCode(self):
        '''Pass no title code - TC31 (Bug 575)'''
        self.fail("Bug 575")
        username, registrationResult = self.toolBox.registerNewUsername()
        parentId = registrationResult['user']['id']
        parentGameId = registrationResult['user']['gameUserId']
        email = registrationResult['user']['emailAddress']
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)  
        resultDict = self.toolBox.createBillingAcctPCI(FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY,
                                ZIPCODE, CC, CVV, EXPMONTH, EXPYEAR,
                                PLANIDMONTHLY, titleCode, parentId, parentGameId)
                             
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheckCCPCI(resultDict2, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIPCODE,
                                  CC, CVV, EXPMONTH, EXPYEAR,  PLANIDMONTHLY,  parentId, parentGameId, titleCode)
                                  
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         "OOPs! Your account cannot be created at this time. Please contact Customer Service.",\
                         "No extra info tag found")
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    # # Helper Methods # #
    
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertFalse("errors" in resultDict, "Error XML displayed")
        self.assertTrue("account" in resultDict, "Account tag not found")
        self.assertTrue("accountId" in resultDict['account'], "Account Id tag not found")
        billingAcctId = resultDict['account']['accountId']
        return billingAcctId
           
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
    
    
        
    def parameterValuesCheckCCPCI(self, resultDict, firstname, lastName, address1, city, state, country, zipCode,
                                  cc, cvv, expMonth, expYear, planId, accountId, gameAcctId, titleCode = TITLECODE,
                                  email = None, address2=None, promotion=None, autoConsume=None):
                
        str = ""
        str += "accountId=" + accountId
        str += "&gameId=" + gameAcctId
        str += '&body=<?xml version="1.0" encoding="utf-8"?>\n'
        str += "<subscription>"
        str += "<titleCode>" + titleCode + "</titleCode>"
        str += "<city>" + city + "</city>"
        str += "<service>createBillingAcct</service>"
        str += "<firstName>" + firstname + "</firstName>"
        str += "<lastName>" + lastName + "</lastName>"
        str += "<planId>" + planId + "</planId>"
        str += "<zipCode>" + zipCode + "</zipCode>"
        str += "<state>" + state + "</state>"
        str += "<country>" + country + "</country>"
        str += "<address1>" + address1 + "</address1>"
        if email != None:
            str += "<email>" + email + "</email>"
        str += "<clientIpAddress>216.38.137.137</clientIpAddress>"
        str += "</subscription>"
        # print "resultDict parameters", resultDict['errors']['error']['parameters']
        self.assertEqual(resultDict['errors']['error']['parameters'], str, 'parameters not matching')   
        

    def ParameterSecureMessageCheck(self, resultDict, parentId, parentGameId):
        str = ""
        str += "accountId=" + parentId
        str += "&gameId=" + parentGameId
        str += '&body=Not returned for security reasons'
        self.assertEqual(resultDict['errors']['error']['parameters'], str, 'parameters not matching')
        self.assertEqual(resultDict['errors']['error']['extraInfo'], \
                         'OOPs! Your account cannot be created at this time. Please try again.')    
        
  
    
    def emptyAutoConsumeParameterCheck(self, resultDict, firstname, lastName, address1, city, state, country, zipCode,
                                  cc, cvv, expMonth, expYear, planId, accountId, gameAcctId, titleCode = TITLECODE,
                                  email = None, address2=None, promotion=None, autoConsume=None):
                                  
        str = ""
        str += "accountId=" + accountId
        str += "&gameId=" + gameAcctId
        str += '&body=<?xml version="1.0" encoding="utf-8"?>\n'
        str += "<subscription>"
        str += "<titleCode>" + titleCode + "</titleCode>"
        str += "<city>" + city + "</city>"
        str += "<service>createBillingAcct</service>"
        str += "<firstName>" + firstname + "</firstName>"
        str += "<lastName>" + lastName + "</lastName>"
        str += "<planId>" + planId + "</planId>"
        str += "<zipCode>" + zipCode + "</zipCode>"
        str += "<state>" + state + "</state>"
        str += "<country>" + country + "</country>"
        str += "<promotion>" + promotion + "</promotion>"
        str += "<address1>" + address1 + "</address1>"
        str += "<autoConsume>" + autoConsume + "</autoConsume>"
        if email != None:
            str += "<email>" + email + "</email>"
        str += "<clientIpAddress>216.38.137.137</clientIpAddress>"
        str += "</subscription>"
        self.assertEqual(resultDict['errors']['error']['parameters'], str, 'parameters not matching')   
    


    def getAcctKeyValueFromDB(self, billingId):
        '''returns User Updated Email by means of Game Id'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT billing_account_promotion_key.account_key_value, billing_account_promotion_key.third_party_account_id, account.account_id  FROM billing_account_promotion_key, account
                          WHERE billing_account_promotion_key.account_id = account.account_id
                          AND third_party_account_id = "%s"'''%(billingId))
        result = cursor.fetchall()
        return result[0]



    def getPromotionCodeValueFromDB(self, billingAcctId):
        '''Promo Code is obtained from DB using billing Id and updated using regex to
        match up the dashes displayed between numbers (Ex:24CC-E4C9-1839-B949)'''
        accountValue = self.getAcctKeyValueFromDB(billingAcctId)
        accountKey = accountValue[0]
        t1 = accountKey.upper()
        pattern = re.search("(\w{4})(\w{4})(\w{4})(\w{4})", t1)
        promoCode = pattern.group(1) + "-" + pattern.group(2) + "-" + pattern.group(3) + "-" + pattern.group(4)    
        return promoCode




    