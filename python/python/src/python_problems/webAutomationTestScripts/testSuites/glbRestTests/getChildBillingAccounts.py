#Get child billing accounts
#Includes both positive and negative test cases.
#Created by Tarja Rechsteiner on 12.01.09.

import sys
from selenium import selenium
import time

from testSuiteBase import TestSuiteBase

class GetChildBillingAccounts(TestSuiteBase):


    def setUp(self):
        self.toolBox = self.getGlbToolbox()


    # def test_validInfo(self):
        # '''Valid information -- TC1'''
        # #Accounts taken from script output of 2010-05-17.  Unable to create new legacy accounts; hardcoding
        # childId = '69798'
        # childAccountId = '2924298'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts valid account", {"Gazillion Id": childId, "childBillingAccountId": childAccountId})
        
        
    def test_validNewInfo(self):
        '''Valid new account information -- TC2'''
        self.selenium = selenium("localhost", 4444, "*firefox", "https://stage.ariasystems.net/webclients/dreamworksPay/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        username, result1 = self.toolBox.registerNewUsername()
        self.assertTrue('user' in result1, "XML from register does not contain user")
        gameAcctId = self.toolBox.getGameIdFromUser(username)
        id = result1['user']['id']
        firstName = 'Tester'
        lastName = 'Dummy'
        billingType = '1'
        address1 = '123 Fake Street'
        city = 'San Mateo'
        state = 'CA'
        country = 'US'
        zipCode = '94403'
        gameUrl = 'http://gazillion.com'
        clientIpAddress = '192.168.1.1'
        result2 = self.toolBox.createBillingAcct(id,gameAcctId,billingType,clientIpAddress,planId='10003936',firstName=firstName,lastName=lastName,
                                                      address1=address1,city=city,state=state,country=country,zipCode=zipCode,gameUrl=gameUrl)
        self.assertTrue('account' in result2, result2)
        billingId = result2['account']['accountId']
        sessionId = result2['account']['inSessionID']
        flowId = result2['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.selenium.close()
        self.selenium.stop()

        result = self.toolBox.getChildBillingAccounts(id)
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['There are no child billing accounts for this user', '16035'])
        self.infoFailCheck(result, id)
        self.toolBox.scriptOutput("getChildBillingAccounts new account structure account", {"Gazillion Id": id, "childBillingAccountId": billingId})
        
        
    # def test_validInfoLvl1(self):
        # '''Valid Level 1 information -- TC3'''
        # childAccountId = '2924322'
        # childId = '69810'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts valid level 1 account", {"Gazillion Id": childId, "childBillingAccountId": childAccountId})
        
        
    # def test_validPaypalInfo(self):
        # '''Valid paypal information -- TC4'''
        # childId = '69804'
        # childAccountId = '2924305'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts paypal account", {"Gazillion Id": childId, "childBillingAccountId": childAccountId})
        
        
    # def test_validPaypalInfoLevel1(self):
        # '''Valid Level 1 paypal information -- TC5'''
        # childId = '69806'
        # childAccountId = '2924309'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts paypal level 1 account", {"Gazillion Id": childId, "childBillingAccountId": childAccountId})
        
        
    # def test_validParentInfo(self):
        # '''Valid Parent information -- TC6 (Bug #509)'''
        # self.fail("Bug #509")
        # #no easy way to get this data currently - GMan down and these test cases were not run recently due to the self.fail.
		# #commenting out test: bug deprioritized/not fixed and attepmting to hard code this legacy case isn't a good use of resources -ngould
        # _, childAccountId, id = self.validAccountCreation()
        # result = self.toolBox.getChildBillingAccounts(id)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts valid account", {"Gazillion Id": id, "childBillingAccountId": childAccountId})
        
        
    # def test_validParentInfoLvl1(self):
        # '''Valid Parent Level 1 information -- TC7 (Bug #509)'''
        # self.fail("Bug #509")
		# #commenting out test: bug deprioritized/not fixed and attepmting to hard code this legacy case isn't a good use of resources -ngould
        # childAccountId, _, id = self.validAccountCreationLevel1()
        # result = self.toolBox.getChildBillingAccounts(id)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts valid level 1 account", {"Gazillion Id": id, "childBillingAccountId": childAccountId})
        
        
    # def test_validParentPaypalInfo(self):
        # '''Valid Parent paypal information -- TC8 (Bug #509)'''
        # self.fail("Bug #509")
		# #commenting out test: bug deprioritized/not fixed and attepmting to hard code this legacy case isn't a good use of resources -ngould
        # _, childAccountId, id = self.validPaypalAccountCreation()
        # result = self.toolBox.getChildBillingAccounts(id)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts paypal account", {"Gazillion Id": id, "childBillingAccountId": childAccountId})
        
        
    # def test_validParentPaypalInfoLevel1(self):
        # '''Valid Parent Level 1 paypal information -- TC9 (Bug #509)'''
        # self.fail("Bug #509")
		# #commenting out test: bug deprioritized/not fixed and attepmting to hard code this legacy case isn't a good use of resources -ngould
        # _, childAccountId, id = self.validPaypalAccountCreationLevel1()
        # result = self.toolBox.getChildBillingAccounts(id)
        
        # self.assertTrue(result.httpStatus() == 200,\
                        # "http status code: " + str(result.httpStatus()))    
        # #structure check
        # self.assertTrue('childAccount' in result, "No childAccount found")
        # self.assertTrue('accountId' in result['childAccount'], "No accountId found: " + str(result))
        # #values check
        # self.assertEqual(childAccountId, result['childAccount']['accountId'], "values don't match")
        # self.toolBox.scriptOutput("getChildBillingAccounts paypal level 1 account", {"Gazillion Id": id, "childBillingAccountId": childAccountId})
        
        
    # def test_unvalidatedInfoLvl1(self):
        # '''unvalidated Level 1 information -- TC10'''
        # childId = '69794'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 499,\
                        # "http status code: " + str(result.httpStatus()))    
        # self.failureCheck(result, ['There are no child billing accounts for this user', '16035'])
        # self.infoFailCheck(result, childId)
        # self.toolBox.scriptOutput("getChildBillingAccounts unvalidated level 1 account", {"user id": childId})
        
        
    # def test_unvalidatedPaypalInfoLevel1(self):
        # '''Unvalidated Level 1 paypal information -- TC11'''
        # childId = '69796'
        # result = self.toolBox.getChildBillingAccounts(childId)
        
        # self.assertTrue(result.httpStatus() == 499,\
                        # "http status code: " + str(result.httpStatus()))    
        # self.failureCheck(result, ['There are no child billing accounts for this user', '16035'])
        # self.infoFailCheck(result, childId)
        # self.toolBox.scriptOutput("getChildBillingAccounts unvalidated Paypal level 1 account", {"user id": childId})
    
    
    # def test_validInfoNoAccount(self):
        # '''Valid information with no child billing accounts - TC12'''
        # userid = '69803'
        # result = self.toolBox.getChildBillingAccounts(userid)
        
        # self.assertTrue(result.httpStatus() == 499,\
                        # "http status code: " + str(result.httpStatus()))    
        # self.failureCheck(result, ['There are no child billing accounts for this user', '16035'])
        # self.infoFailCheck(result, userid)
        # self.toolBox.scriptOutput("getChildBillingAccounts invalid account", {"user id": userid})
    
    
    def test_missingParams(self):
        '''Missing information - TC13'''
        result = self.toolBox.blankGet('getChildBillingAccounts')

        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
    
    
    def test_emptyValues(self):
        '''Empty values - TC14'''
        result = self.toolBox.getChildBillingAccounts('')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, '')
    
    
    def test_invalidInfo(self):
        '''Invalid information -- TC15'''
        result = self.toolBox.getChildBillingAccounts('00000000000000000')
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))
        self.failureCheck(result, ['Id does not match any records', '17000'])
        self.infoFailCheck(result, '00000000000000000')
        

    def test_invalidTitleCode(self):
        '''Invalid title code -- TC16'''
        #userId taken from valid account 2010-05-12
        userId = '69038'
        self.toolBox.setTitleCodeParam('somejunk')
        result = self.toolBox.getChildBillingAccounts(userId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Title code does not match any records", '17002'])
        self.infoFailCheck(result, userId, 'somejunk')
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_emptyTitleCode(self):
        '''Empty Title Code -- TC17'''
        #userId taken from valid account 2010-05-12
        userId = '69038'
        self.toolBox.setTitleCodeParam('')
        result = self.toolBox.getChildBillingAccounts(userId)
        
        self.assertTrue(result.httpStatus() == 499,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ["Parameter values are empty for the request", '4003'])
        self.infoFailCheck(result, userId, '')
        self.toolBox.setTitleCodeParam('KFPW')
        
    
    def test_withoutTitleCode(self):
        '''Without a Title Code -- TC18'''
        #userId taken from valid account 2010-05-12
        userId = '69038'
        self.toolBox.setTitleCodeParam(None)
        result = self.toolBox.getChildBillingAccounts(userId)
        
        self.assertTrue(result.httpStatus() == 400,\
                        "http status code: " + str(result.httpStatus()))    
        self.failureCheck(result, ['Not enough parameters to satisfy request', '4000'])
        self.infoFailCheck(result, userId, None)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def ariaHostedPage(self, sessionId, flowId):
        '''Entering credit card information through selenium'''
        sel = self.selenium
        sel.open(r"file://///hq-fs01/dept/Dev/QA/Web/KungFuPandaWorld/Web_Services/DB/Web%20Services%20Test.html")
        sel.select("wsUrl", "label=" + str(self.toolBox.webHost))
        sel.click("//input[@value='set environment']")
        sel.wait_for_page_to_load("30000")
        sel.is_text_present("Current Environment: " + str(self.toolBox.webHost))
        sel.type("ahp_inSessionID", sessionId)
        sel.type("ahp_flowID", flowId)
        sel.click("ahp_submit")
        sel.wait_for_page_to_load("30000")
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
        sel.wait_for_page_to_load("30000")
        
    
    def failureCheck(self, result, expected) :
        '''Checks for XML structure and messages'''
        #checking for XML structure
        self.assertTrue('errors' in result, "XML structure failed, no errors")
        self.assertTrue('error' in result['errors'], "XML structure failed, no error")
        self.assertTrue('code' in result['errors']['error'], "XML structure failed, no code")
        self.assertTrue('message' in result['errors']['error'], "XML structure failed, no message")
        self.assertTrue('parameters' in result['errors']['error'], "XML structure failed, parameters")
        
        # Checks for messages
        self.assertEqual(result['errors']['error']['message'], expected[0], "Expected error message not found.  Found: " + str(result['errors']['error']['message']))
        self.assertEqual(result['errors']['error']['code'], expected[1], "Expected error code not found.  Found: " + str(result['errors']['error']['code']))
        
        
    def infoFailCheck(self, result, accountId, titleCode='KFPW') :
        '''Checks that the information passed is equal to the information given for one error message'''
        #need to see what parameters message for this looks like
        parameters = self.toolBox.httpParamToDict(result['errors']['error']['parameters'])
        
        self.assertTrue(len(parameters) != 0, "Parameters string did not resolve to pairs" + str(result))
        self.assertTrue(parameters['accountId'] == accountId, "accountId returned not equal to accountId given: " + accountId + " " + str(parameters))
        self.assertTrue(parameters['service'] == "getChildBillingAccounts", "Service returned not equal to service called: getChildBillingAccounts " + str(parameters))
        if titleCode == None:
            self.assertFalse('titleCode' in parameters, "titleCode not passed, but included in return XML: " + str(parameters))
        else:
            self.assertTrue(parameters['titleCode'] == titleCode, "Title code returned not equal to title code called: " + titleCode + " " + str(parameters))