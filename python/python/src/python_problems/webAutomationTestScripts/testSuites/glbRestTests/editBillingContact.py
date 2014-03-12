'''Edit Billing Contact Testsuite
Passes Billing Account Id (type - int) obtained via
create master billing account and 
user information to service EditBillingContact
This WS edits existing billing account contact information
Created by Sharmila Janardhanan Date on 12/01/2009'''

from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, time

FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "Address1"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIP = "94087"
PLANID = "10003936"
NEWFIRSTNAME = "newFirstname"
NEWLASTNAME = "newLastname"
NEWADDRESS1 = "newAddress1"
NEWZIP = "94085"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"

class EditBillingContact(TestSuiteBase):

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
        # '''Pass valid information of all parameters and invalid zipCode'''
        # billingLists = self.toolBox.getBillingParentChildIdFromDb()
        # billingId = billingLists[0]
        # zip = "invalid"
        # resultDict =  self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                      # STATE, COUNTRY, zip, CLIENTIPADDRESS)
        
        # self.fail("success XML displayed bug # 86 " + str(resultDict))
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '16027', 'Unable to set billing contact information. Please try again later.')
        # self.parameterValuesCheck(resultDict, billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, 
                                  # CITY, STATE, COUNTRY, zip, CLIENTIPADDRESS)
        # self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid zip code", "No extra info tag found")
        
        
    ####################
    ###          test cases          ###
    ####################
               
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function'''
        resultDict = self.toolBox.blankPost('editBillingContact')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=editBillingContact', \
                                    'Edit billing contact parameter not matching')
        
        
    def test_emptyValues(self):
        '''Pass empty value to the service'''
        billingId = firstname = lastname = address1 = city = state = country = zip = clientIpAddress =  ""
        resultDict = self.toolBox.editBillingContact(billingId, firstname, lastname, address1, 
                                                     city, state, country, zip, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, firstname, lastname, address1, 
                                  city, state, country, zip, clientIpAddress)               
                                  
    def test_invalidInformation(self):
        '''Pass invalid information to the service'''
        billingId = firstname = lastname = address1 = city = state = country = zip = clientIpAddress =  "invalid"
        resultDict = self.toolBox.editBillingContact(billingId, firstname, lastname, address1, 
                                                     city, state, country, zip, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, billingId, firstname, lastname, address1, 
                                  city, state, country, zip, clientIpAddress)
                                  
                                  
    def test_mixEmptyInvalidValues(self):
        '''Pass mixture of invalid and empty values'''
        billingId = firstname = lastname = address1 = city = ""
        state = country = zip = clientIpAddress = "invalid"
        resultDict = self.toolBox.editBillingContact(billingId, firstname, lastname, address1, 
                                                     city, state, country, zip, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, firstname, lastname, address1, 
                                  city, state, country, zip, clientIpAddress)
    
    
    def test_mixEmptyValidValues(self):
        '''Pass mixture of empty and valid values'''
        billingId = firstname = lastname = address1 = city = ""
        resultDict = self.toolBox.editBillingContact(billingId, firstname, lastname, address1, city, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, firstname, lastname, address1, 
                                  city, STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
                                           

    def test_mixInvalidValidValues(self):
        '''Pass mixture of invalid and valid values'''
        billingId = firstname = lastname = address1 = city = "invalid"
        resultDict = self.toolBox.editBillingContact(billingId, firstname, lastname, address1, city, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, firstname, lastname, address1, 
                                  city, STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        
        
    def test_invalidBillingAccountId(self):
        '''Pass all valid information with invalid Billing account id'''
        billingId = "0000000000"
        resultDict =  self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                      STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, 
                                  CITY, STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
                                  
                                  
    def test_invalidState(self):
        '''Pass valid information of all parameters and invalid state'''
        billingId = self.toolBox.getLatestBillingIdFromDb()
        state = "invalid"
        resultDict =  self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                      state, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16027', 'Unable to set billing contact information. Please try again later.')
        self.parameterValuesCheck(resultDict, billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, 
                                  CITY, state, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "Invalid state_prov entered", "No extra info tag found")
        
    
    def test_invalidCountry(self):
        '''Pass valid information of all parameters and invalid country'''
        billingId = self.toolBox.getLatestBillingIdFromDb()
        country = "invalid"
        resultDict =  self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                      STATE, country, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16027', 'Unable to set billing contact information. Please try again later.')
        self.parameterValuesCheck(resultDict, billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, 
                                  CITY, STATE, country, NEWZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid iso country code", "No extra info tag found")
        
         
    def test_invalidClientIpAddress(self):
        '''Pass valid information of all parameters and invalid client IP address'''
        billingId = self.toolBox.getLatestBillingIdFromDb()
        clientIpAddress = "100"
        resultDict =  self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                      STATE, COUNTRY, NEWZIP, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, 
                                  CITY, STATE, COUNTRY, NEWZIP, clientIpAddress)
                                  
        
    def test_validEditBillingContactWithOptionalParameter(self):
        '''Pass valid billing contact information with optional parameter (child info)'''
        billingId = self.createBillingId()
        self.toolBox.scriptOutput("editBillingContact - With Optional parameter(Billing CC - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS, address2 = 'newAddress2')
        self.successCheck(resultDict)
        
    
    def test_validEditBillingContactWithoutOptionalParameter(self):
        '''Pass valid billing contact information without optional parameter (child info)'''
        billingId = self.createBillingId()
        self.toolBox.scriptOutput("editBillingContact - Without Optional parameter(Billing CC - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.successCheck(resultDict)
        
        
    def test_validEditBillingContactWithOptionalParameterForPaypal(self):
        '''Pass valid billing contact information with optional parameter for paypal billing (child info)'''
        billingId = self.createBillingIdForPaypal()
        self.toolBox.scriptOutput("editBillingContact - With Optional parameter(Billing Paypal - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS, address2 = 'newAddress2')
        self.successCheck(resultDict)
        
        
    def test_validEditBillingContactWithoutOptionalParameterForPaypal(self):
        '''Pass valid billing contact information without optional parameter for paypal billing (child info)'''
        billingId = self.createBillingIdForPaypal()
        self.toolBox.scriptOutput("editBillingContact - Without Optional parameter(Billing Paypal - child info)", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.successCheck(resultDict)
        

    def test_validEditBillingContactForPaypalParentInfo(self):
        '''Change billing contact for paypal (parent info)'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, "11", CLIENTIPADDRESS, PLANID)
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        self.toolBox.scriptOutput("editBillingContact - Billing Paypal - parent info", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.successCheck(resultDict)
        
        
    def test_validEditBillingTypeForCreditCardParentInfo(self):
        '''Change billing contact for credit card (parent info)'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        billingResult = self.toolBox.createBillingAcct(parentId, parentGameId, "1", CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("editBillingContact - Billing CC - parent info", {"Billing Id": billingId})
        resultDict = self.toolBox.editBillingContact(billingId, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, NEWZIP, CLIENTIPADDRESS)
        self.successCheck(resultDict)

        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code'''
        titleCode = "somejunk"
        billingId = self.toolBox.getLatestBillingIdFromDb()
        self.toolBox.setTitleCodeParam(titleCode)           
        resultDict = self.toolBox.editBillingContact(billingId, FIRSTNAME, LASTNAME, NEWADDRESS1, CITY, 
                                                     STATE, COUNTRY, ZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, billingId, FIRSTNAME, LASTNAME, NEWADDRESS1, CITY, 
                                  STATE, COUNTRY, ZIP, CLIENTIPADDRESS, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code'''
        titleCode = ""
        billingId = self.toolBox.getLatestBillingIdFromDb()
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.editBillingContact(billingId, FIRSTNAME, LASTNAME, ADDRESS1, CITY, 
                                                     STATE, COUNTRY, ZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, 
                                  COUNTRY, ZIP, CLIENTIPADDRESS, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
        
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service'''
        self.toolBox.setTitleCodeParam(None)  
        billingId = self.toolBox.getLatestBillingIdFromDb()
        resultDict = self.toolBox.editBillingContact(billingId, FIRSTNAME, LASTNAME, ADDRESS1, CITY, 
                                                     STATE, COUNTRY, ZIP, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'city=' + CITY + '&billingAcctId=' + billingId +
                                    '&service=editBillingContact' + '&firstName=' + FIRSTNAME +
                                    '&lastName=' + LASTNAME + '&zipCode=' + ZIP + '&state=' + STATE + 
                                    '&clientIpAddress=' + CLIENTIPADDRESS + '&address1=' + ADDRESS1 + 
                                    '&country=' + COUNTRY, 'Edit billing contact parameter not matching') 
        self.toolBox.setTitleCodeParam('KFPW')    
        
        
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
        
    def createBillingId(self):
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        resultDict = self.toolBox.createBillingAcct(id, gameId, 1, CLIENTIPADDRESS, PLANID, FIRSTNAME, LASTNAME, 
                                                          ADDRESS1, CITY, STATE, COUNTRY, ZIP, gameUrl = GAMEURL)
        billingAcctId = resultDict['account']['accountId']
        return billingAcctId
        
    def createBillingIdForPaypal(self):
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        resultDict = self.toolBox.createBillingAcct(id, gameId, 11, CLIENTIPADDRESS, PLANID)
        billingAcctId = resultDict['account']['accountId']
        return billingAcctId
        
    def createParentChildInfo(self):
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        return childId, childGameId, parentGameId, parentId
                
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
        
    def parameterValuesCheck(self, resultDict, billingId, firstname, lastname, address1, city, state, 
                             country, zip, clientIpAddress, titleCode = 'KFPW'):
        '''Error XML validation specific to this Web Service'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + 
                                    '&city=' + city + '&billingAcctId=' + billingId +
                                    '&service=editBillingContact' + '&firstName=' + firstname +
                                    '&lastName=' + lastname + '&zipCode=' + zip + '&state=' + state +
                                    '&clientIpAddress=' + clientIpAddress + '&address1=' + address1 +
                                    '&country=' + country, 'Edit billing contact parameter not matching')
                                    
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not found")
        self.assertTrue("value" in resultDict['success'], "Value tag not found")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')