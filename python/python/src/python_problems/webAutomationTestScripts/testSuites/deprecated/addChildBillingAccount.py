'''Add child billing account test suite
Passes master billing account id, child account
id and child game account id with billing
information to the service addChildBillingAccount
This WS adds a new child account and plan
Created by Sharmila Janardhanan on 01/06/2010'''

from testSuiteBase import TestSuiteBase
from selenium import selenium
import random, time

PLANID = "10003939"
BILLINGTYPE = "1"
FIRSTNAME = "firstname"
LASTNAME = "lastname"
ADDRESS1 = "address1"
CITY = "Sunnyvale"
STATE = "CA"
COUNTRY = "US"
ZIP = "94087"
NEWFIRSTNAME = "newFirstname"
NEWLASTNAME = "newLastname"
NEWADDRESS1 = "newAddress1"
NEWZIP = "94085"
CLIENTIPADDRESS = "192.168.1.1"
GAMEURL = "http://gazillion.com"

class AddChildBillingAccount(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefox", "https://secure.ariasystems.net/webclients/dreamworksPay2/Handler.php")
        self.selenium.start()
        self.selenium.window_maximize()
        
    def tearDown(self):
        self.selenium.close()
        self.selenium.stop()
        
        
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function - TC1'''
        resultDict = self.toolBox.blankPost('addChildBillingAccount')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=addChildBillingAccount', \
                                    'Add child billing Parameters not matching')
                                    
                                    
    def test_allEmptyValues(self):
        '''Pass all empty values to the service - TC2'''
        billingId = childId = childGameId = planId = useMasterBilling = clientIpAddress = ""
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
                                        
                                    
    def test_allInvalidInformation(self):
        '''Pass all invalid information to the service - TC3'''
        billingId = childId = childGameId = planId = useMasterBilling = clientIpAddress = "invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
        
        
    def test_mixEmptyInvalidValues(self):
        '''Pass mixture of empty and invalid values to the service - TC4'''
        childId = childGameId = planId = ""
        billingId = useMasterBilling = clientIpAddress = "Invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress)
        
        
    def test_mixEmptyValidValues(self):
        '''Pass mixture of empty and valid values to the service - TC5'''
        childId = childGameId = billingId = clientIpAddress = ""
        useMasterBilling = "true"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, PLANID, useMasterBilling, clientIpAddress)
        
        
    def test_mixInvalidValidValues(self):
        '''Pass mixture of invalid and valid values to the service - TC6'''
        childId = childGameId = billingId = clientIpAddress = "invalid"
        useMasterBilling = "true"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, clientIpAddress)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, PLANID, useMasterBilling, clientIpAddress)

        
    def test_validUserWithoutMasterAccountSet(self):
        '''Pass valid user information without master account set - TC7'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        useMasterBilling = "true"
        billingId = "1040441"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        

    def test_invalidBilllingId(self):
        '''Pass all valid information with invalid billing Id - TC8'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        useMasterBilling = "true"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, FIRSTNAME, LASTNAME, BILLINGTYPE, CLIENTIPADDRESS, 
                                                             ADDRESS1, CITY, STATE, COUNTRY, ZIP, PLANID, gameUrl = GAMEURL)
        billingId = "00000000000"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16032', 'No billing account exists for this user')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
            
        
    def test_invalidChildAccountId(self):
        '''Pass all valid information with invalid child account id - TC9'''
        billingLists = self.toolBox.getBillingParentChildIdFromDb()
        billingId = billingLists[0]
        username = self.toolBox.getLatestUsernameFromDb()
        childId = "000000000"
        childGameId = self.toolBox.getGameIdFromUser(username)
        useMasterBilling = "true"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        self.parameterValuesCheck(resultDict, billingId, str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS)


    def test_invalidChildGameAccountId(self):
        '''Pass all valid information with invalid child game account id - TC10'''
        billingLists = self.toolBox.getBillingParentChildIdFromDb()
        billingId = billingLists[0]
        childId = self.toolBox.getLatestPlatformIdFromDb()
        childGameId = "0000000000000000"
        useMasterBilling = "true"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17016', 'The specified platform & associated title account was not found')
        self.parameterValuesCheck(resultDict, billingId, str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS)
        

    def test_invalidPlanId(self):
        '''Pass all valid information with invalid plan id - TC11'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        planId = '000000000000'
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        childPlanId = "10003936"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, planId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16029', 'No billing plans matching the specified criteria')
        self.parameterValuesCheckWithAddress(resultDict, str(billingId), str(childId), str(childGameId), planId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)
        
        
    def test_useMasterBillingFalseWithNoBillingInfo(self):
        '''Pass useMasterBilling as false and no Address information given - TC12'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        billingType = "1"
        useMasterBilling = "false"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.parameterValuesCheck(resultDict, str(billingId), str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS)
            
        
    def test_invalidUseMasterBilling(self):
        '''invalid useMasterBillingAccount value - TC13'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        billingType = "11"
        useMasterBilling = "invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4002', 'Unexpected values to complete the request')
        self.parameterValuesCheck(resultDict, str(billingId), str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS)
        
        
    def test_invalidBillingType(self):
        '''Pass all valid information with invalid billingType - TC14'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        billingType = "100"
        useMasterBilling = "false"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS, billingType, 
                                                         NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16030', 'The billing type specified does not exist')
        self.parameterValuesCheckWithAddress(resultDict, str(billingId), str(childId), str(childGameId), PLANID, useMasterBilling, 
                                             CLIENTIPADDRESS, billingType, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)
            
        
    def test_invalidStateName(self):
        '''Pass all valid information with invalid state name - TC15'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003936"
        state = "invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, state, COUNTRY, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        self.parameterValuesCheckWithAddress(resultDict, billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, state, COUNTRY, NEWZIP, GAMEURL)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "Invalid state_prov entered", "No extra info tag found")
        
    
    def test_invalidCountryName(self):
        '''Pass all valid information with invalid country name - TC16'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003936"
        country = "invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, country, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        self.parameterValuesCheckWithAddress(resultDict, billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, country, NEWZIP, GAMEURL)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid iso country code", "No extra info tag found")
        
        
    def test_invalidZipCode(self):
        '''Pass all valid information with invalid zipCode - TC17'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003936"
        zip = "invalid"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS, BILLINGTYPE, 
                                                         NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, zip, gameUrl = GAMEURL)
        self.fail("Success XML dispalyed similar bugs #796, #797 filed " + str(resultDict))
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '13006', 'Account Registration Failed')
        self.parameterValuesCheckWithAddress(resultDict, billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS,
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, zip, GAMEURL)
        self.assertEqual(resultDict['errors']['error']['extraInfo'], "invalid zip code", "No extra info tag found")
         
       
    def test_notAssociatedChildandParent(self):
        '''Pass child not associated with the parent which has valid master billing - TC18'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        billingType = "11"
        useMasterBilling = "false"
        username, result = self.toolBox.registerNewUsername(8)
        self.assertTrue('user' in result, "user tag not found")
        id = result['user']['id']
        gameId = result['user']['gameUserId']
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS)
        billingId = billingResult['account']['accountId']
        resultDict = self.toolBox.addChildBillingAccount(billingId, id, gameId, PLANID, useMasterBilling, CLIENTIPADDRESS, billingType, 
                                                         NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, gameUrl = GAMEURL)                                                         
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17005', 'Parent Id and Child Id are not associated')
        self.parameterValuesCheckWithAddress(resultDict, billingId, id, gameId, PLANID, useMasterBilling, CLIENTIPADDRESS, 
                                             billingType, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)
            
        
    def test_childUserAlreadyHavingActiveBilling(self):
        '''Pass child info which has active child billing already - TC19'''
        childId, childGameId, childBillingId, masterBillingId = self.childBillingLists()
        useMasterBilling = "false"
        childPlanId = "10003936"
        resultDict = self.toolBox.addChildBillingAccount(masterBillingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16039', 'The user already has an active child billing account')
        self.parameterValuesCheckWithAddress(resultDict, str(masterBillingId), str(childId), str(childGameId), childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)
        
        
    def test_passChildBillingAccount(self):
        '''Pass child billing account to add child billing service - TC20'''
        childId, childGameId, childBillingId, masterBillingId = self.childBillingLists()
        useMasterBilling = "false"
        childPlanId = "10003936"
        resultDict = self.toolBox.addChildBillingAccount(childBillingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16040', 'The billing account is not a master account')
        self.parameterValuesCheckWithAddress(resultDict, str(childBillingId), str(childId), str(childGameId), childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)

                                             
    def test_notActiveMasterBillingAccountLevel2Child(self):
        '''Master billing not went through PCI process - not active (level 2 child)- TC21'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "true"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        childPlanId = "10003937"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16048', 'The payment method for the master account has not been validated')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
            
        
    def test_notActiveMasterBillingAccountLevel1Child(self):
        '''Master billing not went through PCI process - not active (level 1 child) - TC22'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        childPlanId = "10003937"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         gameUrl = GAMEURL)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '16048', 'The payment method for the master account has not been validated')
        self.parameterValuesCheckWithAddress(resultDict, billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                             BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP, GAMEURL)
            
        
    def test_subscriptionOnChildAcctUsingDefaultBillingUsingCC(self):
        '''1 month subscription using master billing info for Credit Card (level-2 child)  - TC23'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "true"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        childPlanId = "10003936"
        self.ariaHostedPage(sessionId, flowId)
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        childBillingId = self.successCheck(resultDict)
        self.toolBox.scriptOutput("addChildBillingAccount - Level 2 Child (CC)", {"Child Billing Id": childBillingId})
        
        
    def test_subscriptionOnChildAcctUsingUniqueBillingUsingCC(self):
        '''6 month subscription not using master billing info for Credit Card (level-1 child)- TC24'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003937"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         address2='address2', gameUrl = GAMEURL)
        childBillingId = self.successCheck(resultDict)
        self.toolBox.scriptOutput("addChildBillingAccount - Level 1 Child (CC)", {"Child Billing Id": childBillingId})
        self.assertTrue("flowID" in resultDict['account'], "Flow Id tag not found")
        self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        self.assertTrue("validationUrl" in resultDict['account'], "Validation URL not found")
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        
        
    def test_subscriptionOnVerifiedAdultAcctUsingDefaultBillingUsingCC(self):
        '''1 year subscription for verified adult using Credit Card - TC25'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        masterBillingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS,
                                                                   firstName=FIRSTNAME, lastName=LASTNAME, address1=ADDRESS1, 
                                                                   city=CITY, state=STATE, country=COUNTRY, zipCode=ZIP, 
                                                                   address2='address2', gameUrl = GAMEURL)
        billingId = masterBillingResult['account']['accountId']
        sessionId = masterBillingResult['account']['inSessionID']
        flowId = masterBillingResult['account']['flowID']
        childPlanId = "10003938"
        useMasterBilling = "true"
        self.ariaHostedPage(sessionId, flowId)
        resultDict = self.toolBox.addChildBillingAccount(billingId, parentId, parentGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        self.fail("The user already has an active child billing account bug #890 " + str(resultDict))
        childBillingId = self.successCheck(resultDict)
        self.toolBox.scriptOutput("addChildBillingAccount - Verified Adult (CC)", {"Child Billing Id": childBillingId})
        
        
    def test_validAddChildBillingWithMasterBillingInfoUsingPaypal(self):
        '''Pass valid childId, childGameId, master billingId using Paypal using Master Billing Info - TC26'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "true"
        billingType = "11"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS)
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        childPlanId = "10003938"
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        childBillingId = self.successCheck(resultDict)
        self.toolBox.scriptOutput("addChildBillingAccount - Level 2 Child (Paypal)", {"Child Billing Id": childBillingId})
        
        
    def test_validAddChildBillingWithoutMasterBillingInfoUsingPaypal(self):
        '''Pass valid childId, childGameId, master billingId using Paypal without using Master Billing Info - TC27'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        billingType = "11"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, billingType, CLIENTIPADDRESS)
        billingId = billingResult['account']['accountId']
        paypalResult = self.toolBox.startPaypalPlan(billingId)
        paypalToken = paypalResult['paypal']['paypalToken']
        paypalURL = paypalResult['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        #using selenium to goto paypal sandbox site test page and accept the paypal agreement
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypalResult2 = self.toolBox.finishPaypalPlan(billingId, paypalToken)
        childPlanId = "10003936"
        useMasterBilling = "false"
        self.selenium.close()
        self.selenium.stop()
        self.selenium.start()
        self.selenium.window_maximize()
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, 
                                                         CLIENTIPADDRESS, billingType = billingType)
        childBillingId = self.successCheck(resultDict)
        self.toolBox.scriptOutput("addChildBillingAccount - Level 1 Child (Paypal)", {"Child Billing Id": childBillingId})
        paypalResult3 = self.toolBox.startPaypalPlan(childBillingId)
        paypalToken = paypalResult3['paypal']['paypalToken']
        paypalURL = paypalResult3['paypal']['returnUrl']
        URL = paypalURL + paypalToken
        self.acceptPaypalAgreementUsingSelenium(URL)
        paypayResult4 = self.toolBox.finishPaypalPlan(childBillingId, paypalToken)
        
        
    def test_reSubscriptionAfterCancellation(self):
        '''Re-subscription after cancellation  - should have status as active and only one invoice in Aria - TC28'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "true"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        childPlanId = "10003936"
        self.ariaHostedPage(sessionId, flowId)
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        childBillingId = self.successCheck(resultDict)
        token = self.toolBox.getDeactivateTokenFromDb(childId)
        banResult = self.toolBox.banChildAccount(token=token)
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17017', 'The platform account is currently banned')
        self.parameterValuesCheck(resultDict, billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS)
            
        
    def test_notMatchingTitleCode(self):
        '''Pass not matching title code - TC29'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        useMasterBilling = "true"
        titleCode = 'somejunk'
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        self.parameterValuesCheck(resultDict, billingId, str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')  
        
        
    def test_emptyTitleCode(self):
        '''Pass empty title code - TC30'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        useMasterBilling = "true"
        titleCode = ''
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        self.parameterValuesCheck(resultDict, billingId, str(childId), str(childGameId), PLANID, useMasterBilling, CLIENTIPADDRESS, titleCode)
        self.toolBox.setTitleCodeParam('KFPW')
                
        
    def test_noTitleCode(self):
        '''Pass no title code (kfpw) to the service - TC31'''
        childId, childGameId, parentGameId, parentId, billingId = self.billingLists()
        useMasterBilling = "true"
        titleCode = None
        self.toolBox.setTitleCodeParam(titleCode)   
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, PLANID, useMasterBilling, CLIENTIPADDRESS)
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'service=addChildBillingAccount&planId=' + PLANID + 
                                    '&useMasterBilling=' + useMasterBilling + '&clientIpAddress=' + CLIENTIPADDRESS +
                                    '&masterBillingAcctId=' + billingId + '&childAcctId=' + str(childId) + 
                                    '&childGameAcctId=' + str(childGameId), 'Add child master Billing Parameters not matching')
        self.toolBox.setTitleCodeParam('KFPW')                               
                                    
                                    
    def test_completeValidationOnSecondTryBug891(self):
        '''Complete addChildBillingAcct where useMasterBilling=false validated on second time  - TC32'''
        childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
        useMasterBilling = "false"
        billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = billingResult['account']['accountId']
        sessionId = billingResult['account']['inSessionID']
        flowId = billingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003937"
        childBillingResult = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         address2='address2', gameUrl = GAMEURL)
        childBillingIdFirstTry = childBillingResult['account']['accountId']
        self.successCheck(childBillingResult)
        resultDict = self.toolBox.addChildBillingAccount(billingId, childId, childGameId, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                         BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                         address2='address2', gameUrl = GAMEURL)
        childBillingIdSecondTry = resultDict['account']['accountId']
        self.toolBox.scriptOutput("addChildBillingAccount - Complete Validation on second try", {"Child Billing Id first try": childBillingIdFirstTry, "Child Billing Id second try": childBillingIdSecondTry})
        self.assertTrue("flowID" in resultDict['account'], "Flow Id tag not found")
        self.assertTrue("inSessionID" in resultDict['account'], "inSession Id tag not found")
        self.assertTrue("validationUrl" in resultDict['account'], "Validation URL not found")
        sessionId = resultDict['account']['inSessionID']
        flowId = resultDict['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)

        
    def test_ariaPciProcessSessionId(self):
        '''Prints session id to test PCI action manually'''
        for i in range(1,3):
            childId, childGameId, parentGameId, parentId = self.createParentChildInfo()
            useMasterBilling = "true"
            billingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                                 FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                                 address2='address2', gameUrl = GAMEURL)
            billingId = billingResult['account']['accountId']
            sessionId = billingResult['account']['inSessionID']
            flowId = billingResult['account']['flowID']
            self.toolBox.scriptOutput("addChildBillingAccount - Print Session Id", {"Session Id": sessionId, "Flow Id": flowId})
            
            
    def test_childAssociationCheck(self):
        '''Associate 3 children with billing and check in Gman'''
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        child1Username = parentRegResult['user']['childAccounts']['userBrief']['username']
        childId1 = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId1 = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        masterBillingResult = self.toolBox.createMasterBillingAcct(parentId, parentGameId, BILLINGTYPE, CLIENTIPADDRESS, PLANID,
                                                             FIRSTNAME, LASTNAME, ADDRESS1, CITY, STATE, COUNTRY, ZIP, 
                                                             address2='address2', gameUrl = GAMEURL)
        billingId = masterBillingResult['account']['accountId']
        sessionId = masterBillingResult['account']['inSessionID']
        flowId = masterBillingResult['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        childPlanId = "10003936"
        useMasterBilling = "true"
        child1Billing = self.toolBox.addChildBillingAccount(billingId, childId1, childGameId1, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        child2Username, childRegResult = self.toolBox.registerNewUsername(8, child1Username+"@brainquake.com")
        childId2 = childRegResult['user']['id']
        childGameId2 = childRegResult['user']['gameUserId']
        addChildResult = self.toolBox.addChildAccount(parentId, child2Username, "password")
        child2Billing = self.toolBox.addChildBillingAccount(billingId, childId2, childGameId2, childPlanId, useMasterBilling, CLIENTIPADDRESS)
        child3Username, childRegResult = self.toolBox.registerNewUsername(8, child1Username+"@brainquake.com")
        childId3 = childRegResult['user']['id']
        childGameId3 = childRegResult['user']['gameUserId']
        addChildResult = self.toolBox.addChildAccount(parentId, child3Username, "password")
        useMasterBilling = "false"
        child3Billing = self.toolBox.addChildBillingAccount(billingId, childId3, childGameId3, childPlanId, useMasterBilling, CLIENTIPADDRESS, 
                                                            BILLINGTYPE, NEWFIRSTNAME, NEWLASTNAME, NEWADDRESS1, CITY, STATE, COUNTRY, NEWZIP,
                                                            address2='address2', gameUrl = GAMEURL)
        sessionId = child3Billing['account']['inSessionID']
        flowId = child3Billing['account']['flowID']
        self.ariaHostedPage(sessionId, flowId)
        self.toolBox.scriptOutput("addChildBillingAccount - Child Association Check", {"Parent": parentUsername, "Child1": child1Username, "Child2":child2Username, "Child3":child3Username})
        
            
    #################
    ###  Helper Methods  ###
    #################
    
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
        sel.click("//a[@onclick='document.forms.Action.submit();']")
        sel.wait_for_page_to_load("90000")
        self.assertEqual("Gazillion Entertainment", sel.get_title())
        time.sleep(1)
        
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
        
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
        
    def billingLists(self):
        billingLists = self.toolBox.getBillingParentChildIdFromDb()
        childId = billingLists[2]
        childGameId = billingLists[4]
        parentGameId = billingLists[3]
        parentId = billingLists[1]
        billingId = billingLists[0]
        return childId, childGameId, parentGameId, parentId, billingId
        
    def createParentChildInfo(self):
        parentUsername, parentRegResult = self.toolBox.registerNewParent()
        childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        childGameId = parentRegResult['user']['childAccounts']['userBrief']['gameUserId']
        parentGameId = parentRegResult['user']['gameUserId']
        parentId = parentRegResult['user']['id']
        return childId, childGameId, parentGameId, parentId
        
    def childBillingLists(self):
        billingLists = self.toolBox.getChildBillingIdMasterBillingChildIDChildGameIdFromDb()
        childId = billingLists[0]
        childGameId = billingLists[1]
        childBillingId = billingLists[2]
        masterBillingId = billingLists[3]
        return childId, childGameId, childBillingId, masterBillingId
        
    def parameterValuesCheck(self, resultDict, billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&service=addChildBillingAccount' + '&planId=' + planId + 
                                    '&useMasterBilling=' + useMasterBilling + '&clientIpAddress=' + clientIpAddress + 
                                    '&masterBillingAcctId=' + billingId + '&childAcctId=' + childId + 
                                    '&childGameAcctId=' + childGameId, 'Add child billing parameters not matching')   

    def parameterValuesCheckWithAddress(self, resultDict, billingId, childId, childGameId, planId, useMasterBilling, clientIpAddress, billingType, 
                                        firstName, lastName, address1, city, state, country, zip, gameUrl, titleCode = 'KFPW'):
        '''Error XML validations specific to this Web Services'''
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&city=' + city + '&billingType=' + billingType +
                                    '&firstName=' + firstName + '&service=addChildBillingAccount' + '&lastName=' + lastName + 
                                    '&planId=' + planId + '&zipCode=' + zip + '&gameUrl=' + gameUrl + '&country=' + country + 
                                    '&useMasterBilling=' + useMasterBilling + '&state=' + state + '&clientIpAddress=' + clientIpAddress +
                                    '&address1=' + address1 + '&masterBillingAcctId=' + billingId + '&childAcctId=' + childId +
                                    '&childGameAcctId=' + childGameId, 'Add child billing parameters with address not matching') 

    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertFalse("errors" in resultDict, "Error XML displayed")
        self.assertTrue("account" in resultDict, "Account tag not found")
        self.assertTrue("accountId" in resultDict['account'], "Account Id tag not found")
        childBillingAcctId = resultDict['account']['accountId']
        return childBillingAcctId
