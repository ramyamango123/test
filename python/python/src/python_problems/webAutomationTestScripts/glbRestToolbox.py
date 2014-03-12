import httplib
import urllib
import xml.etree.ElementTree as ElementTree

from xmlDictConvert import ConvertXmlToDict, ConvertDictToXml
import httpResultDict

import string
import random
import re
import MySQLdb
import datetime


DISPATCHER = '/ws_dispatcher.php'

class GlbRestToolbox:
    '''
    GlbRestToolbox is a tool box made to wrap around the Globant REST API.
    This allows a simple interface to execute api methods with one line python calls.
    Please refer to the Globant-RestArchitecture document located at https://wiki.gazillion.com/images/7/7c/GLB-RESTArchitecture.doc for postive/negative respones that these calls will generate.

    Note: All XML results are returned as a HttpResultDict which is a python dictonary that also has a .httpStatus function to return the http status of the message.
    '''
    def __init__(self, webHost, sqlDb, sqlUsername, sqlPassword):
        '''
        @type webHost: string
        @param webHost: the web host of the webservices server
        '''
        self.sqlDb = sqlDb
        self.sqlUsername = sqlUsername
        self.sqlPassword = sqlPassword
        self.webHost = webHost
        self.setTitleCodeParam ("KFPW")
        self.dispatcher = '/ws_dispatcher.php'

    def setTitleCodeParam(self, titleCode):
        self.titleCodeString = titleCode

    def _httpGet(self, paramDict):
        ''' '''
        # insert titlecode , if not supplied
        if not paramDict.has_key("titleCode") and self.titleCodeString != None:
            paramString = "titleCode="+self.titleCodeString
        else :
            paramString = ""

        for key, value in paramDict.iteritems():
            paramString += '&'+key+'='+value
        serverConnection = httplib.HTTPConnection(self.webHost)
        serverConnection.request('GET',DISPATCHER+'?'+paramString)
        serverResponse = serverConnection.getresponse()

        textResponse = serverResponse.read()
        #print textResponse, type(textResponse), len(textResponse)
        try:
            return ElementTree.fromstring(textResponse), serverResponse.status
        except:
            print "Error with response on :",textResponse

    def _httpPost(self, paramDict):
        ''' '''

        # insert titlecode , if not supplied
        if not paramDict.has_key("titleCode") and self.titleCodeString != None:
            paramDict['titleCode'] = self.titleCodeString
        headers = {"Content-type": "application/x-www-form-urlencoded;charset=utf-8"}
        params = urllib.urlencode(paramDict)
        serverConnection = httplib.HTTPConnection(self.webHost)
        serverConnection.request("POST", DISPATCHER, params, headers)
        serverResponse = serverConnection.getresponse()

        textResponse = serverResponse.read()
        #print textResponse, type(textResponse), len(textResponse)
        try:
            return ElementTree.fromstring(textResponse), serverResponse.status
        except:
            print "Error with response on :",textResponse
			
			
    def _httpXMLPost(self, paramDict):
        '''POST method for PCI compliant web services'''
        # insert titlecode , if not supplied
        paramDict = {'subscription':paramDict}
        xmlParams = ElementTree.tostring(ConvertDictToXml(paramDict))
        xmlParams = '<?xml version="1.0" encoding="utf-8"?>' + str(xmlParams)
    
        headers = {"Content-type": "application/xml;charset=utf-8"}
        serverConnection = httplib.HTTPSConnection(self.webHost)
        serverConnection.request("POST", self.dispatcher, xmlParams, headers)
        serverResponse = serverConnection.getresponse()

        textResponse = serverResponse.read()
        #print textResponse, type(textResponse), len(textResponse)
        try:
            return ElementTree.fromstring(textResponse), serverResponse.status
        except:
            print "Error with response on :",textResponse

    def validateUsername(self, username):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "validateUsername".
        To validate a username before registration. Return either success or an error with cause and alternate valid username(s).

        See "Validate Username" in REST-API documentation.

        @type username: string
        @param username: the user name being sent to the API
        '''

        paramDict = {'username':username,
                     'service':'validateUsername'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def login(self, username, password):
        '''
        Sends a POST request to the server using service "login".
        Used to login to the server.

        See "Login Service" in REST-API documentation.

        @type username: string
        @param username: the user name being sent to the API

        @type password: string
        @param password: the password being sent to the API
        '''
        paramDict = {'username':username,
                      'password':password,
                      'service':'login'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def logout(self, userId):
        '''
        Sends a POST request to the server using service "logout".
        Used to login to the server.

        See "Login Service" in REST-API documentation.

        @type userId: string
        @param userId:      The user's Gamebase Id

        '''
        paramDict = {'userId':userId,
                      'service':'logout'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def log(self, typeId, accountId=None, customData={}):
        '''
        Sends a POST request to the server using service "log".
        This webservice exposes the ActivityManager library.

        See "Logging" in REST-API documentation.

        @type typeId: string
        @param typeId:      The type of log

        @type accountId: string (not required)
        @param accountId: GameBase account id

        @type location: string (not required)
        @param location:      TBD

        @type customData: Dict (not required)
        @param customData: GameBase account id


        '''
        optionalParams = ['accountId', 'customData']

        if customData != {} :
            customData = ElementTree.tostring(ConvertDictToXml(customData))
        else :
            customData = None
        paramDict = {'typeId':typeId,
                      'service':'log'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    
     
    def register(self, username, password, emailAddress=None, gameUserId=None, referralUrl=None, referrer=None):
        '''
        Sends a POST requestto the server using the "registerUser" service.
        This will attempt to create a new user account.

        See "User Registration" in REST-API documentation.

        @type username: string
        @param username: the user name being sent to the API

        @type password: string
        @param password: the password being sent to the API

        @type emailAddress: string
        @param emailAddress: the email address being sent to the API.  Now optional.
        '''
        optionalParams = ['emailAddress', 'referralUrl', 'referrer' ]
        if not gameUserId:
            gameUserId = self.getGameIdFromUser(username)

        paramDict = {'username':username,
                     'password':password,
                     'gameUserId':gameUserId,
                     'service':'register'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
        
        
    def getUserById (self, id):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getUserById ".
        This webservice is used to get the user information by the Gamebase Id.

        See "This webservice is used to get the user information by the Gamebase Id. " in REST-API documentation.

        @type id: string
        @param id: the user name being sent to the API
        '''

        paramDict = {'id':id,
                     'service':'getUserById'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getUserByUsername  (self, username):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getUserByUsername  ".
        This webservice is used to get the user information by the Gamebase Username

        See "This webservice is used to get the user information by the Gamebase Id. " in REST-API documentation.

        @type username: string
        @param username: the user name being sent to the API
        '''

        paramDict = {'username':username,
                     'service':'getUserByUsername'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def getMasterBillingAccount(self, accountId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getMasterBillingAccount".
        This webservice retrieves the existing master billing account ID

        @type accountId: string
        @param accountId: GameBase account id
        '''

        paramDict = {'accountId':accountId,
                     'service':'getMasterBillingAccount'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getChildBillingAccounts(self, accountId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getChildBillingAccounts".
        This webservice retrieves the existing master billing account ID

        @type accountId: string
        @param accountId: GameBase account id
        '''

        paramDict = {'accountId':accountId,
                     'service':'getChildBillingAccounts'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getBillingContact(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getBillingContact".
        This webservice retrieves the existing master billing account ID

        @type billingAcctId: string
        @param billingAcctId: GameBase account id
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'service':'getBillingContact'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
    
    
    def createBillingAcct(self, accountId, gameAcctId, billingType, clientIpAddress, planId,
                                firstName=None, lastName=None, address1=None, city=None, state=None, 
                                country=None, zipCode=None, address2=None, gameUrl=None, promotion=None, autoConsume=None):
        '''
        Sends a POST request to the server using service "createBillingAcct ".
        This webservice creates a new master billing account

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type accountId: string
        @param accountId: GameBase account id

        @type gameAcctId: string
        @param gameAcctId: KFPW game account id
        
        @type billingType: string
        @type clientIpAddress: string
        
        @type planId: string
        @type firstName: string
        @type lastName: string
        @type address1: string
        @type city: string
        @type state: string
        @type country: string
        @type zipCode: string
        @type address2: string, optional
        @type gameUrl: string, optional
        '''
        optionalParams = ['firstName', 'lastName', 'address1', 'city', 'state', 'country', 'zipCode', 
                          'address2', 'gameUrl', 'promotion', 'autoConsume']
        paramDict = {'accountId':accountId,
                      'gameAcctId':gameAcctId,
                      'billingType':billingType,
                      'clientIpAddress':clientIpAddress,
                      'planId':planId,
                      'service':'createBillingAcct'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
    
    
    def createBillingAcctPCI(self, firstName, lastName, address1, city, state, country, zipCode, cc, cvv, expMonth, expYear,
                                planId, titleCode, accountId, gameAcctId,
                                email=None, address2=None, promotion=None, autoConsume=None):
        '''
        Sends a POST request to the server using service "createBillingAcct ".
        This webservice creates a new master billing account

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type accountId: string
        @param accountId: GameBase account id

        @type gameAcctId: string
        @param gameAcctId: KFPW game account id
        
        @type billingType: string
        @type clientIpAddress: string
        
        @type planId: string
        @type firstName: string
        @type lastName: string
        @type address1: string
        @type city: string
        @type state: string
        @type country: string
        @type zipCode: string
        @type address2: string, optional
        @type gameUrl: string, optional
        '''
        optionalParams = ['email', 'address2', 'promotion', 'autoConsume'] 
                          
        paramDict = {'firstName':firstName, 'lastName':lastName, 'address1':address1, 
                     'city':city, 'state':state, 'country':country, 'zipCode':zipCode, 
                     'cc':cc, 'cvv':cvv, 'expMonth':expMonth, 'expYear':expYear, 'planId':planId, 'titleCode':titleCode, 'service':'createBillingAcct'}
                     
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]
				
        # Set  aside host inherited from testSuiteBase and set PCI compliant host
        tempWebHost = self.webHost
        self.webHost = "pciservices-qa.gazillion.com"
		#do the same with DISPATCHER
        tempDISPATCHER = self.dispatcher
        self.dispatcher = "/pci/" + accountId + "/" + titleCode + "/" + gameAcctId
        # print "-------------------------------\n"
        # print paramDict
        # print "-------------------------------\n"
        
        resultXml, status = self._httpXMLPost(paramDict)
        #Set host and DISPATCHER back to original values for other tests        
        self.webHost = tempWebHost
        self.dispatcher = tempDISPATCHER
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
		
		
		
    def login(self, username, password):
        '''
        Sends a POST request to the server using service "login".
        Used to login to the server.

        See "Login Service" in REST-API documentation.

        @type username: string
        @param username: the user name being sent to the API

        @type password: string
        @param password: the password being sent to the API
        '''
        paramDict = {'username':username,
                      'password':password,
                      'service':'login'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def editBillingContact(self, billingAcctId, firstName, lastName, address1, city, state, country, zipCode, clientIpAddress,
                                address2=None):
        '''
        Sends a POST request to the server using service "editBillingContact".
        This webservice edits an existing billing account contact info. You can only use this function for a master plan or Level 1 child plan.


        @type billingAcctId : string
        @param billingAcctId : Billing system account id (obtained via getMasterBillingAccount or getChildBillingAccount)

        @type firstName: string
        @type lastName: string
        @type address1: string
        @type city: string
        @type state: string
        @type country: string
        @type zipCode: string
        @type clientIpAddress: string
        @type address2: string, optional
        '''
        optionalParams = ['address2']
        paramDict = {'billingAcctId':billingAcctId,
                      'firstName':firstName,
                      'lastName':lastName,
                      'address1':address1,
                      'city':city,
                      'state':state,
                      'country':country,
                      'zipCode':zipCode,
                      'clientIpAddress':clientIpAddress,
                      'service':'editBillingContact'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def editBillingType(self, billingAcctId, billingType,
                                gameUrl=None):
        '''
        Sends a POST request to the server using service "editBillingType".
        This webservice edits an existing billing account contact info. You can only use this function for a master plan or Level 1 child plan.


        @type billingAcctId : string
        @param billingAcctId : Billing system account id (obtained via getMasterBillingAccount or getChildBillingAccount)

        @type billingType : string
        @type gameUrl: string, optional
        '''
        optionalParams = ['gameUrl']
        paramDict = {'billingAcctId':billingAcctId,
                      'billingType':billingType,
                      'service':'editBillingType'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def changePlan(self, billingAcctId, planId):
        '''
        Sends a POST request to the server using service "changePlan".
        This webservice changes an existing master or child plan


        @type billingAcctId : string
        @param billingAcctId : Billing system account id (obtained via getMasterBillingAccount or getChildBillingAccount)

        @type planId: string
        '''
        paramDict = {'billingAcctId':billingAcctId,
                      'planId':planId,
                      'service':'changePlan'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getBillingAcctInfo(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getBillingAcctInfo".
        This webservice retrieves the existing master billing account ID

        @type billingAcctId: string
        @param billingAcctId: GameBase account id
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'service':'getBillingAcctInfo'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getQueuedPlan(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getQueuedPlan".
        This webservice retrieves the queued plan

        @type billingAcctId: string
        @param billingAcctId: GameBase account id
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'service':'getQueuedPlan'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getAvailablePlans(self, promocode=None):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getAvailablePlans".
        This webservice retrieves the queued plan

        @type promocode : string, optinal
        @param promocode : If specified, this will return the plans associated to this promo code
        '''


        optionalParams = ['promocode']
        paramDict = {'service':'getAvailablePlans'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def getChildAccounts(self, masterBillingAcctId ):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getChildAccounts".
        This webservice retrieves all user's child accounts.

        @type masterBillingAcctId : string
        @param masterBillingAcctId : GameBase account id
        '''

        paramDict = {'masterBillingAcctId':masterBillingAcctId,
                     'service':'getChildAccounts'}
        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def addChildAccount(self, userId, childUsername=None, childPassword=None, token=None):
        '''
        Sends a POST request to the server using service "addChildAccount".
        This webservice adds a new child account & plan.
        See "Validate Coupon/Key Service " in REST-API documentation.

        @type childUsername: string
        @param childUsername: child platform account ID

        @type childPassword: string
        @param childPassword: Password that identifies the child (required if no token)
        '''
        
        optionalParams = ['childUsername', 'childPassword', 'token']
        paramDict = { 'userId':userId,
                      'service':'addChildAccount'}
        
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]
        
        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def addChildBillingAccount(self, masterBillingAcctId, childAcctId, childGameAcctId, planId, useMasterBilling, clientIpAddress,
                                billingType=None, firstName=None, lastName=None, address1=None, city=None, state=None, country=None, zipCode=None, gameUrl=None,
                                address2=None):
        '''
        Sends a POST request to the server using service "addChildBillingAccount".
        This webservice creates a new child billing account

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type masterBillingAcctId: string
        @param masterBillingAcctId: Billing system account id (obtained via getMasterBillingAccount)

        @type childAcctId: string
        @param childAcctId:child platform account ID

        @type childGameAcctId: string
        @param childGameAcctId: child platform account ID

        @type planId: string
        
        @type useMasterBilling: string
        @param userMasterBilling: determines whether to pull down info from the associated master billing account
        
        @type clientIpAddress: string, optional
        @type billingType: string, optional
        @type firstName: string, optional
        @type lastName: string, optional
        @type address1: string, optional
        @type city: string, optional
        @type state: string, optional
        @type country: string, optional
        @type zipCode: string, optional
        @type gameUrl: string, optional
        @type address2: string, optional
        '''
        optionalParams = ['gameUrl', 'address2', 'firstName', 'lastName', 'billingType', 'address1', 'city', 'state', 'country', 'zipCode', ]
        paramDict = {'masterBillingAcctId':masterBillingAcctId,
                      'childAcctId':childAcctId,
                      'childGameAcctId':childGameAcctId,
                      'planId':planId,
                      'useMasterBilling': useMasterBilling,
                      'clientIpAddress':clientIpAddress,
                      'service':'addChildBillingAccount'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def cancelPlan(self, billingAccountId):
        '''
        Sends a POST request to the server using service "cancelPlan".
        This webservice cancels an existing billing account


        @type billingAcctId : string
        @param billingAcctId : Billing system account id (obtained via getMasterBillingAccount or getChildBillingAccount)
        '''
        paramDict = {'billingAccountId':billingAccountId,
                      'service':'cancelPlan'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
            

    def startGameSession(self, userId):
        '''
        Sends a POST request to the server using service "startGameSession".
        This webservice cancels an existing billing account


        @type userId  : string
        @param userId  : The user's title account id

        
        '''
        paramDict = {'userId':userId,
                      'service':'startGameSession'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
        
    def validateSession(self, userId):
        '''
        Sends a POST request to the server using service "validateSession".
        This webservice is used to Validate a Game session


        @type userId  : string
        @param userId  : The user's title account id

        
        '''
        paramDict = {'userId':userId,
                      'service':'validateSession'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml        
    
    

    def blankPost(self, service, paramDict={}):
        '''
        Send a POST request using the service name provided and parameters as defined by the paramDict
        This is used to test out services with mismatched parameters.

        @type service: string
        @param service: The service being sent to the POST request

        @type paramDict: dictonary
        @param paramDict: Other parameters to be sent with the request
        '''
        paramDict['service'] = service
        resultXml, status = self._httpPost(paramDict)

        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    
    def blankXMLPost(self, service, paramDict={}):
        '''
        Send a POST request using the service name provided and parameters as defined by the paramDict
        This is used to test out services with mismatched parameters.

        @type service: string
        @param service: The service being sent to the POST request

        @type paramDict: dictonary
        @param paramDict: Other parameters to be sent with the request
        '''
        paramDict['service'] = service
        resultXml, status = self._httpXMLPost(paramDict)

        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

 

    def validateCode(self, code, userId=None):
        '''
        Sends a GET request to the server using service "validateCode ".
        This webservice is used to validate a coupon or a key, and to return information about what the coupon or key grants.

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type code: string
        @param code: The string used to represent a coupon or a key.
        
        @type userId: string, optional
        @param userId: the user name being sent to the API
        '''
        optionalParams = ['userId']
        paramDict = {'code':code,
                      'service':'validateCode'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def redeemCode(self, userId, code):
        '''
        Sends a POST request to the server using service "redeemCode".
        This webservice is used to validate a coupon or a key, and to return information about what the coupon or key grants.

        This webservice is used to redeem/consume either coupon or a key.

        @type userId: string
        @param userId: the user name being sent to the API

        @type code: string
        @param code: The string used to represent a coupon or a key.
        '''
        paramDict = {'userId':userId,
                      'code':code,
                      'service':'redeemCode'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def parentLogin(self, username, password):
        '''
        Sends a POST request to the server using service "parentLogin".
        Parent Login to modify the different information of the child

        @type username: string
        @param username: the username being sent to the API

        @type password: string
        @param password: Alphanumeric value that identifies the parent user
        '''
        paramDict = {'username':username,
                      'password':password,
                      'service':'parentLogin'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def emailParentSetting(self, userId, gameNewsletterEnabled=None, accountRemainderEnabled=None):
        '''
        Sends a POST request to the server using service "emailParentSetting".
        Parent Login to modify the different information of the child

        @type userId: string
        @param userId: the userID being sent to the API


        '''
        optionalParams = ['gameNewsletterEnabled', 'accountRemainderEnabled']
        paramDict = {'userId':userId,
                      'service':'emailParentSetting'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def parentAccountRegistration(self, gameUserId, username, password, validationType,
                                  token=None, emailAddress=None, birthDate=None, childUsername=None, childPassword=None):
        '''
        Sends a POST request to the server using service "parentAccountRegistration ".
        Registers the account of the parent and includes a children to be managed by the parent

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type gameUserId: string
        @param gameUserId: Numeric value that identifies the user

        @type username: string
        @param username: User name String value of the parent that can access to modify the attributes of the child

        @type password: string
        @param password: Alphanumeric value that identifies the parent password

        @type validationType: string
        @param validationType: Identifier for method of validation: EMAIL or AGE

        @type token: string, optional
        @param token:Token from URL clicked on in email (required for EMAIL validation)

        @type emailAddress: string, optional
        @param emailAddress:Alphanumeric value that identifies the email address (required for AGE validation)

        @type birthDate: string, optional
        @param birthDate:Date format YYYY-MM-DD(required for AGE validation)


        @type childUsername: string, optional
        @param childUsername:Alphanumeric value that identifies the child account (required for AGE validation)

        @type childPassword: string, optional
        @param childPassword:Alphanumeric value that identifies the child account (required for AGE validation)

        '''
        optionalParams = ['token', 'emailAddress', 'birthDate', 'childUsername', 'childPassword']
        paramDict = {'gameUserId':gameUserId,
                      'username':username,
                      'password':password,
                      'validationType':validationType,
                      'service':'parentAccountRegistration'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def resetPassword(self, username):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "resetPassword".
        Send a link to the email account so the password can be reseted for that user

        @type username: string
        @param username: the user name being sent to the API
        '''

        paramDict = {'username':username,
                     'service':'resetPassword'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def changePassword(self, newPassword,
                                  username=None, tokenCode=None, userId=None, password=None):
        '''
        Sends a POST request to the server using service "changePassword ".
        Change password of the user

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type newPassword: string
        @param newPassword: New password value

        @type username: string, optional
        @param username: User name String value of the parent that can access to modify the attributes of the child

        @type userId: string, optional
        @param userId: the userID being sent to the API

        @type token: string, optional
        @param token:Token from URL clicked on in email (required for EMAIL validation)

        @type password: string, optional
        @param password: Alphanumeric value that identifies the parent password

        '''
        optionalParams = ['username', 'tokenCode', 'userId', 'password']
        paramDict = {'newPassword':newPassword,
                      'service':'changePassword'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def chatSetting(self, chatType,
                                  userId=None, token=None, childUserId=None):
        '''
        Sends a POST request to the server using service "changePassword ".
        Change password of the user

        See "Validate Coupon/Key Service " in REST-API documentation.

        @type chatType: string
        @param chatType: type of chat can a user have it can be FULL or CANNED

        @type userId: string, optional
        @param userId: the userID being sent to the API

        @type token: string, optional
        @param token:Token from URL clicked on in email (required for EMAIL validation)

        @type childUserId: string, optional
        @param childUserId: the userID being sent to the API


        '''
        optionalParams = ['userId', 'token', 'childUserId']
        paramDict = {'chatType':chatType,
                      'service':'chatSetting'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def gameSchedule(self, userId, childUserId, allowedPlayTimes, timeZone):
        '''
        Sends a POST request to the server using service "gameSchedule ".
        Change the game schedule

        @type userId: string
        @param userId: Numeric value that identifies the user of the parent

        @type childUserId: string
        @param childUserId:


        @type allowedPlayTimes: XML(string)
        @param allowedPlayTimes: <allowedPlayTimes>: Beginning of the tag that indicates when the user will be allowed to play
                            <inteval>: Beginning of the tag that indicates when the child is allowed to play
                            <weekday>This indicates which day the child can play, it can be 1-7 where 1 is Monday, 2 is Tuesday, 3 Wednesday, 4 Thursday, 5 Friday, 6 Saturday, 7 Sunday
                            <begin>Indicates the beginning hour when a child can play can be from 00:00-24:00, the value would be in minutes
                            <end>Indicates the end when a child can play, it can be from 00:00-24:00, the value would be in minutes

        @type timeZone: string
        @param timeZone: String value that contains the time zone of the user, possible values: US/Pacific, US/Central, US/Mountain, US/Eastern, US/Alaska, US/Hawaii

        '''
        paramDict = {'userId':userId,
                      'childUserId':childUserId,
                      'allowedPlayTimes':allowedPlayTimes,
                      'timeZone':timeZone,
                      'service':'gameSchedule'}


        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml




    def getPlayableDate(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getPlayableDate".
        This webservice retrieves the playable time left on account

        @type billingAcctId : string
        @param billingAcctId : billing account id
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'service':'getPlayableDate'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def startPaypalPlan(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "startPaypalPlan".
        This webservice starts an Paypal billing agreement

        @type billingAcctId : string
        @param billingAcctId : billing account id
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'service':'startPaypalPlan'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    def finishPaypalPlan(self, billingAcctId, paypalToken ):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "finishPaypalPlan".
        This webservice finishes an Paypal billing agreement

        @type billingAcctId : string
        @param billingAcctId : Billing system account id (obtained via getMasterBillingAccount or getChildBillingAccounts)

        @type paypalToken : string
        @param payPayPalToken : Billing system account id (obtained via startPaypalPlan)
        '''

        paramDict = {'billingAcctId':billingAcctId,
                     'paypalToken':paypalToken,
                     'service':'finishPaypalPlan'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def validateToken(self, token):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "validateToken".
        This service validates a token

        @type token : string
        @param token : billing account id
        '''

        paramDict = {'token':token,
                     'service':'validateToken'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml

    def banChildAccount(self, parentId=None, childId=None, token=None):
        '''
        Sends a POST request to the server using service "banChildAccount".
        This service allows a parent account to ban one of their child accounts

        @type parentId: string, optional (required if token is missing)
        @param parentId: Numeric value that identifies the parent

        @type childId: string, optional (required if token is missing)
        @param childId:Value that identifies the child

        @type token: string, optional (Required if childId and parentId is missing)
        @param token: Deactivation link token
        '''
        optionalParams = ['parentId', 'childId', 'token']
        paramDict = {'service':'banChildAccount'}
        for paramName in optionalParams:
            if vars()[paramName] != None:
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
        
        
    def sendChildRegistrationReminder( self, userId):
        '''
        Sends a POST request to the server using service "sendChildRegistrationReminder".
        This webservice sends an email to a parent to remind them that their child has registered a KFPW account. Has a configurable throttle so the email can't be sent too often.
        
        @type userId: string
        @param userId: PLATFORM ID
        '''
        optionalParams = []
        paramDict = {'userId':userId,
                      'service':'sendChildRegistrationReminder'}
        for paramName in optionalParams:
            if vars()[paramName] != '':
                paramDict[paramName] = vars()[paramName]

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
        
        
    def updateCoinBalance( self, avatarId, coinAmount, gameUserId):
        '''
        Sends a POST request to the server using service "updateCoinBalance".
        This webservice adds or subtracts the specified coin amount in an avatar's balance. The opposite amount is applied to the main bank.
        
        @type avatarId: string
        @param avatarId: The avatar ID to modify coin balance for.
        
        @type coinAmount: string
        @param coinAmount: The number of coins to add (positive number) or subtract (negative number) from the avatar's coin balance.
        
        @type gameUserId: string
        @param gameUserId: KFPW ID  
        '''
        
        paramDict = {'avatarId':avatarId,
                     'coinAmount':coinAmount,
                     'gameUserId':gameUserId,
                      'service':'updateCoinBalance'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
        
        
    def getBillingHist(self, billingAcctId):
        '''
        Sends a GET request to the dispatcher on the webhost currently configured, specifiying the server as "getBillingHis".          
        This webservice gets a gamebase account's billing history. This function calls Aria get_acct_trans_history 
        
        @type billingAcctId: string
        @param billingAcctId: Billing Systems Plan id
        '''
        paramDict = {'billingAcctId':billingAcctId,
                     'service':'getBillingHist'}

        resultXml, status = self._httpGet(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml    
                   
                                    
    def changeEmail( self, userId, newEmail):
        '''
        Sends a POST request to the server using service "changeEmail".
        This webservice is used to update the users Email address.
        
        @type userId: string
        @param userId: Players KFPW ID (Game ID).
        
        @type newEmail: string
        @param newEmail: New Email for the user.
        
        '''
        
        paramDict = {'userId':userId,
                     'newEmail':newEmail,
                      'service':'changeEmail'}

        resultXml, status = self._httpPost(paramDict)
        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml
		
		
    def blankGet(self, service, paramDict={}):
        '''
        Send a GET request using the service name provided and parameters as defined by the paramDict
        This is used to test out services with mismatched parameters.

        @type service: string
        @param service: The service being sent to the GET request

        @type paramDict: dictonary
        @param paramDict: Other parameters to be sent with the request
        '''
        paramDict['service'] = service
        resultXml, status = self._httpGet(paramDict)

        resultXml = ConvertXmlToDict(resultXml,httpResultDict.HttpResultDict)
        resultXml.setHttpStatus(status)
        return resultXml


    #####################################################
    ###            UTILITY FUNCTIONS FOR WEB SERVICES.                                        ###
    #####################################################

    
    def registerNewUsername(self, username_length=10, email='@brainquake.com', password='password', referralUrl=None, referrer=None):
        '''Registers a randomly generated username with a default length of 10 and other parameters with optional default values.
        Returns the username and success XML dictionary
        If no username is found after 100 attempts, returns an empty username and the last XML dictionary result.

        @type username_length: int
        @param username_length: How long the username should be.  (Default = 10)

        @type email: string
        @param email: The email address being sent to the register web service (Default = <username>@brainquake.com)

        @type password: string
        @param password: The password being sent to the register web service (Default = password)

        '''
        username = ''
        characters = string.letters + string.digits
        c = 0
        initialEmail = email

        while (not username) or 'errors' in result:
            username = ''
            for i in range(username_length):
                username += random.choice(characters)
            if initialEmail == '@brainquake.com' :
                email = username + '@brainquake.com'
            if initialEmail == None:
                if referralUrl != None:
                    result = self.register(username, password,
                                           referralUrl=referralUrl,
                                           referrer=referrer)
                else:
                    result = self.register(username, password)
            else:
                if referralUrl != None:
                    result = self.register(username, password, email,
                                           referralUrl=referralUrl,
                                           referrer=referrer)
                else:
                    result = self.register(username, password, email)
            c += 1
            if c >= 100:
                username = ''
                break

        return username, result   
      
  
    def httpParamToDict(self, httpParams) :
        '''Splits a string into a dictionary.  Uses = or & as delineators and sets adjacent elements to key:value pairs.
        Returns the new dictionary.
        If the string does not split into pairs, returns an empty dictionary.

        @type httpParams: string
        @param httpParams: The http parameters string to be converted into a dictionary
        '''
        delineate = re.compile('=|\&')
        paramList = delineate.split(httpParams)
        if (len(paramList) % 2) != 0 :
            return {}
        else :
            paramDict = {}
            while paramList:
                a = paramList.pop(0)
                b = paramList.pop(0)
                paramDict[a] = b
            return paramDict

    def getGameIdFromUser(self, username):
        '''Given a username, retruns a gameUserId, generated from the username
        @type username: string
        @param username: The user name to be converted to a gameUserId
        '''
        gameUserId = ""
        for letter in username:
            gameUserId += str(ord(letter))
        #gameUserId = str(int(gameUserId)/33)
        #while len(gameUserId) > 9:
            #gameUserId = str(int(gameUserId)/7)
        return gameUserId


    def registerNewParent(self, username_length=10, email='@brainquake.com', password='password'):
        '''Registers a new parent account with a randomly generated username of default length 10.
        Uses registerNewUsername to generate a child account.
        Returns the username and success XML dictionary
        If no username is found after 100 attempts, returns an empty username and the last XML dictionary result.

        @type username_length: int
        @param username_length: How long the username should be.  (Default = 10)

        @type email: string
        @param email: The email address being sent to the register web service (Default = <username>@brainquake.com)

        @type password: string
        @param password: The password being sent to the register web service (Default = password)

        '''
        parentUsername = ''
        characters = string.letters + string.digits
        c = 0
        childUsername, childResult = self.registerNewUsername(username_length, email, password)
        if 'errors' in childResult:
            result = childResult
        else :
            email = childResult['user']['emailAddress']

            while (not parentUsername) or 'errors' in result:
                parentUsername = ''
                for i in range(username_length):
                    parentUsername += random.choice(characters)
                parentUserId = self.getGameIdFromUser(parentUsername)
                result = self.parentAccountRegistration(parentUserId, parentUsername, 'password', 'AGE',
                                                        emailAddress=email, birthDate='1980-10-10', childUsername=childUsername, childPassword='password')
                c += 1
                if c >= 100:
                    parentUsername = ''
                    break

        return parentUsername, result
        
		
    def getChatTokenFromDb(self, id):
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT token_id FROM token_url WHERE token_vars = "account_id=%s" and token_action = "/account/settings/chat"'''%(id))
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            return None
    
    
    def getPasswordTokenFromDb(self, id):
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT token_id FROM token_url WHERE token_vars = "account_id=%s" and token_action = "/account/resetpassword/"'''%(id))
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            return None
    
    
    def getRegisterTokenFromDb(self, id):
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT token_id FROM token_url WHERE token_vars = "child_account_id=%s" and token_action = "/register"'''%(id))
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            return None
    
    
    def getDeactivateTokenFromDb(self, id):
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT token_id FROM token_url WHERE token_vars = "account_id=%s" and token_action = "/account/deactivate"'''%(id))
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            return None
    
    
    def getLatestPlatformIdFromDb(self):
        '''return latest registered platform id to the calling function (used by changePassword, chatSetting, getUserById)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_id FROM account ORDER BY account_id DESC''')
        result = cursor.fetchone()
        return result[0]
    
    
    def getLatestUsernameFromDb(self):
        '''return latest registered username to the calling function (used by changePassword, resetPassword)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT acct_username FROM account ORDER BY account_id DESC''')
        result = cursor.fetchone()
        return result[0]
        
    
    def getLatestParentPlatformIdFromDb(self):
        '''return latest registered parent platform id to the calling function (used by chatSetting, parentEmailSettings)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_id FROM account WHERE account_validation_type_id = "1" ORDER BY account_id DESC''')
        result = cursor.fetchone()
        return result[0]
        
        
    def getLatestUnconsumedKeyFromDb(self):
        '''return the latest unconsumed key to the calling function (used by validateCode, redeemCode)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_key_value FROM account_key WHERE account_key_group_id = "27" AND date_consumed IS NULL''')
        result = cursor.fetchone()
        return result[0]
        
      
    def getLatestDreamworksKeyFromDb(self):
        '''return the latest unconsumed key to the calling function (used by validateCode, redeemCode)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_key_value FROM account_key WHERE account_key_group_id = "28" AND date_consumed IS NULL''')
        result = cursor.fetchone()
        return result[0]  
        
    def getLatestConsumedKeyFromDb(self):
        '''return the latest consumed key to the calling function (used by validateCode, redeemCode)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_key_value FROM account_key WHERE date_consumed IS NOT NULL AND coupon_value = "JUSTTIME"''')
        result = cursor.fetchone()
        return result[0]
        
         
    def getLatestRedeemedKeyFromDb(self):
        '''return the latest consumed key to the calling function (used by validateCode, redeemCode)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT account_key_value FROM account_key WHERE account_key_group_id = "35" AND account_key_type_id = "34"''')
        result = cursor.fetchone()
        return result[0] 
        
    def getActivityLogCountFromDb(self, typeId):
        '''return the number of counted rows to the calling function'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        today = datetime.date.today()
        cursor.execute('''SELECT COUNT(*) AS NumOfRows FROM activity_log WHERE activity_type_id = "%s"'''%(typeId))
        result = cursor.fetchone()
        return result[0]
        
        
    def getActivityLogDataCountFromDb(self, activityKey, activityValue):
        '''return the number of counted rows to the calling function'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        today = datetime.date.today()
        cursor.execute('''SELECT COUNT(*) AS NumOfRows FROM activity_log_data WHERE activity_key = "%s" AND activity_value = "%s"'''%(activityKey, activityValue))
        result = cursor.fetchone()
        return result[0]
        
        
    def getActivityLogFromDb(self, accountId, typeId):
        '''return the number of counted rows to the calling function'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        today = datetime.date.today()
        cursor.execute('''SELECT COUNT(*) AS NumOfRows FROM activity_log WHERE account_id = "%s" AND activity_type_id = "%s"'''%(accountId, typeId))
        result = cursor.fetchone()
        return result[0]
        
    
    def getLatestBillingIdFromDb(self):
        '''returns latest billing id to the calling function (for editBillingContact, editBillingType)'''
        dbConnection = MySQLdb.connect(host=self.sqlDb, user=self.sqlUsername, passwd=self.sqlPassword, db='dwa_platform')
        cursor = dbConnection.cursor()
        cursor.execute('''SELECT third_party_account_id, account_id FROM billing_system_account ORDER BY 1 desc''')
        result = cursor.fetchone()
        return result[0]
        
        
    def scriptOutput(self, service, accountDict) :
        today = datetime.date.today()
        filename = str(today.year) + "-" + str(today.month) + "-" + str(today.day)
        f = open("\\\\hq-fs01\\dept\\Dev\\QA\\Web\\KungFuPandaWorld\\Web_Services\\Build\\Script_Output\\" + filename + ".txt", "a")
        f.write(service + ":\n")
        for accountDesc in accountDict:
            f.write(accountDesc + ": " + accountDict[accountDesc] + "\n")
        f.write("\n")
        f.close()
        