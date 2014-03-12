#Get Available Plans
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.01.09.

import sys
import random
import string
import re

from testSuiteBase import TestSuiteBase

class GetAvailablePlans(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()


    def test_validInfo(self):
        '''Valid information'''
        result = self.toolBox.getAvailablePlans()
        
        self.assertTrue(result.httpStatus() == 200,\
                        "http status code: " + str(result.httpStatus()))  
        self.successCheck(result)
        self.toolBox.scriptOutput("getAvailablePlans", {"available plans": str(result['listPlan'])})
        
        
    def test_missingParams(self):
        '''MIssing information'''
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getAvailablePlans()
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        
        
    def test_invalidTitleCodeParams(self):
        '''Invalid title code'''
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getAvailablePlans()
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Title code does not match any records', '17002'])
        
        
    def test_emptyTitleCodeParams(self):
        '''Empty title code'''
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getAvailablePlans()
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Parameter values are empty for the request', '4003'])
        
        
    def test_invalidPromoCode(self):
        '''Invalid promo code'''
        result = self.toolBox.getAvailablePlans('00')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['No billing plans matching the specified criteria', '16029'])
        
        
    def successCheck(self, result) :
        '''Checks for XML structure'''
        self.assertTrue('listPlan' in result, "XML structure failed, no listPlan")
        self.assertFalse('errors' in result, "error appeared in result: " + str(result))
        self.assertTrue('plan' in result['listPlan'], "XML structure failed, no plan")
        
        for plan in result['listPlan']['plan'] :
            self.assertTrue('planId' in plan, "plan missing a key: " + str(plan))
            self.assertTrue('planName' in plan, "plan missing a name: " + str(plan))
            self.assertTrue('planTerm' in plan, "plan missing a term: " + str(plan))
            self.assertTrue('planPrice' in plan, "plan missing a price: " + str(plan))
            
            
    def failureCheck(self, result, expected) :
        '''Checks for XML structure and messages'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        self.assertFalse('listPlan' in result, "XML structure failed, success XML found")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, promoCode=None, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['service'] == "getAvailablePlans", "Service returned not equal to service called: getAvailablePlans" + str(parameters))
        if promoCode == None :
            self.assertFalse('promoCode' in parameters, "promoCode not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['promoCode'] == promoCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))
        if titleCode == None :
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else :
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))