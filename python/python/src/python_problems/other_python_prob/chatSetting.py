'''User chat settings test suite
Passes parent id and child id or
token along with chatType (type - enum) and
title code to the service chatSetting
This WS modify information of the child
Created by Sharmila Janardhanan on 12/28/2009'''

from testSuiteBase import TestSuiteBase
import random, string
CHATTYPE1 = "CANNED"
CHATTYPE2 = "FULL"


class ChatSetting(TestSuiteBase):

    def setUp(self):
        self.toolBox = self.getGlbToolbox()
        self.selenium = selenium("localhost", 4444, "*firefoxproxy", "https://backoffice01.qa.dwa.sjc.adm.gazint")
        self.selenium.start()
        self.selenium.window_maximize()
    
    def test_noParametersPassed(self):
        '''No parameters passed to the Web Services function - TC1'''
        resultDict = self.toolBox.blankPost('chatSetting')
        self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=KFPW&' + 'service=chatSetting', \
                                    'Parameter value not matched')
        

    # def test_emptyParentIdChildId(self):
        # '''Pass empty parent Id, child Id to the service - TC2'''
        # parentId = childId = chatType = ''
        # resultDict = self.toolBox.chatSetting(chatType, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        # self.parameterValuesCheck1(resultDict, chatType, parentId, childId)
        
        
    # def test_emptyToken(self):
        # '''Pass empty token to the service - TC3'''
        # token = chatType = ''
        # resultDict = self.toolBox.chatSetting(chatType, token = token)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        # self.parameterValuesCheck2(resultDict, chatType, token)
        
        
    # def test_invalidChatTypeWithId(self):
        # '''Pass invalid chatType with child Id and parent Id - TC4'''
        # chatType = parentId = childId = "invalid"
        # resultDict = self.toolBox.chatSetting(chatType, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17009', 'The mute/chat value is invalid')
        # self.parameterValuesCheck1(resultDict, chatType, parentId, childId)
                
      
    # def test_invalidChatTypeWithToken(self):
        # '''Pass invalid chatType with token - TC5'''
        # chatType = token = "invalid"
        # resultDict = self.toolBox.chatSetting(chatType, token = token)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17009', 'The mute/chat value is invalid')
        # self.parameterValuesCheck2(resultDict, chatType, token)
        
        
    # def test_invalidParentIdChildIdValidChat(self):
        # '''Pass invalid child and parent Id with valid chatType - TC6'''
        # parentId = childId = "invalid"
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17000', 'Id does not match any records')
        # self.parameterValuesCheck1(resultDict, CHATTYPE2, parentId, childId)
        
       
    # def test_invalidTokenValidChat(self):
        # '''Pass invalid token and valid chatType - TC7'''
        # token = "invalid"
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, token = token)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17006', 'The Token is not valid')
        # self.parameterValuesCheck2(resultDict, CHATTYPE2, token)
        
        
    # def test_notAssociatedParentChild(self):
        # '''valid chatType with not associated parent and child - TC8'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childUsername, childRegResult = self.toolBox.registerNewUsername(8, '@brainquake.com', 'password')
        # childId = childRegResult['user']['id']
        # resultDict = self.toolBox.chatSetting(CHATTYPE1, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17005', 'Parent Id and Child Id are not associated')
        # self.parameterValuesCheck1(resultDict, CHATTYPE1, parentId, childId)
        
        
    # def test_notMatchingChildId(self):
        # '''valid chatType with not matching child Id - TC9'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = "000000"
        # resultDict = self.toolBox.chatSetting(CHATTYPE1, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17004', 'Child Id does not match any records')
        # self.parameterValuesCheck1(resultDict, CHATTYPE1, parentId, childId)
        
        
    # def test_otherActionTokenValidChat(self):
        # '''Pass other action token and valid chatType - TC10'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # token = self.toolBox.getRegisterTokenFromDb(childId)
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, token = token)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17006', 'The Token is not valid')
        # self.parameterValuesCheck2(resultDict, CHATTYPE2, str(token))
        
    
    # def test_consumedToken(self):
        # '''Pass consumed token with chatType - TC11'''
        # #can consume chat token any number of times
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # token = self.toolBox.getChatTokenFromDb(childId)
        # resultDict = self.toolBox.chatSetting(CHATTYPE1, token = token)
        # self.successCheck(resultDict)
        # resultDict2 = self.toolBox.chatSetting(CHATTYPE1, token = token)
        # self.successCheck(resultDict)

        
    # def test_validParentIdChildIdFromCannedToFull(self):
        # '''Pass valid parent and child Id with chatType FULL and then CANNED - TC12'''
        # #user created with the default chatType CANNED( mute/safeChat)
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # self.chatTypeChangeCheck(childId, "CANNED")
        # #from default chatType CANNEd to FULL
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.successCheck(resultDict)
        # self.chatTypeChangeCheck(childId, "FULL")
        # self.toolBox.scriptOutput("chatSetting - Valid ParentId ChildId", {"Child Id": childId})

     
    # def test_validTokenFromFullToCanned(self):
        # '''Pass valid token with chatType CANNED and check the change- TC13'''
        # childUsername, childRegResult = self.toolBox.registerNewUsername(8, '@brainquake.com', 'password')
        # childId = childRegResult['user']['id']
        # self.chatTypeChangeCheck(childId, "CANNED")
        # token = self.toolBox.getChatTokenFromDb(childId)
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, token = token)
        # self.successCheck(resultDict)
        # self.chatTypeChangeCheck(childId, "FULL")
        # self.toolBox.scriptOutput("chatSetting - Valid Chat Token", {"Child Id": childId})
        
    # def test_validParentIdChildIdCannedByParent(self):
        # ''' To check if the mute status changes to CANNED when muted by parent - TC14'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # self.chatTypeParentControl(childId, "CANNED")
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.chatTypeParentControl(childId, "FULL")
        # resultDict = self.toolBox.chatSetting(CHATTYPE1, parentId, childUserId = childId)
        # self.successCheck(resultDict)
        # self.chatTypeParentControl(childId, "CANNED")
        # self.toolBox.scriptOutput("chatSetting - Valid ParentId ChildId", {"Child Id": childId})
     
    # def test_validParentIdChildIdUnMutedByParent(self):
        # ''' To check if the mute status changes to FULL when unmuted by parent - TC15'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # self.chatTypeParentControl(childId, "CANNED")
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.successCheck(resultDict)
        # self.chatTypeParentControl(childId, "FULL")
        # self.toolBox.scriptOutput("chatSetting - Valid ParentId ChildId", {"Child Id": childId})
        
    # def test_validParentIdChildIdMutedByCS(self):
        # ''' To check if the mute status changes to MUTED when muted by CS - TC16'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # childUsername = parentRegResult['user']['childAccounts']['userBrief']['username']
        # self.chatTypeParentControl(childId, "CANNED")
        # # Changing  default CANNED status to FULL to get access to mute/unmute user through Gman
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.chatTypeParentControl(childId, "FULL")
        # # logging into Gman to mute the user
        # self.muteStatusChangeThroughGman(childUsername, "MUTED")
        # self.chatTypeCheckMutedByCS(childId, "MUTED")
        # self.toolBox.scriptOutput("chatSetting - Valid ParentId ChildId", {"Child Id": childId})
    
    # def test_validParentIdChildIdUnmutedByCS(self):
        # ''' To check if the mute status changes to FULL when unmuted by CS - TC17'''
        # parentUsername, parentRegResult = self.toolBox.registerNewParent(8, '@brainquake.com', 'password')
        # parentId = parentRegResult['user']['id']
        # childId = parentRegResult['user']['childAccounts']['userBrief']['id']
        # childUsername = parentRegResult['user']['childAccounts']['userBrief']['username']
        # self.chatTypeParentControl(childId, "CANNED")
        # # Changing  default CANNED status to FULL to get access to mute/unmute user in Gman
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.chatTypeParentControl(childId, "FULL")
        # # logging to Gman  to check if  user can be unmuted after parent authorizes the child to chat
        # self.muteStatusChangeThroughGman(childUsername, "MUTED")
        # self.chatTypeCheckMutedByCS(childId, "MUTED")
        # # logging to Gman again to unmute the user
        # self.muteStatusChangeThroughGman(childUsename)
        # self.chatTypeCheckUnmutedByCS(childId, "FULL")
        # self.toolBox.scriptOutput("chatSetting - Valid ParentId ChildId", {"Child Id": childId})
             
    # def test_notMatchingTitleCode(self):
        # '''Pass not matching title code - TC18'''
        # parentId = self.toolBox.getLatestParentPlatformIdFromDb()
        # childId = self.toolBox.getLatestPlatformIdFromDb()
        # titleCode = "somejunk"
        # self.toolBox.setTitleCodeParam(titleCode)           
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '17002', 'Title code does not match any records')
        # self.parameterValuesCheck1(resultDict, CHATTYPE2, str(parentId), str(childId), titleCode) 
        # self.toolBox.setTitleCodeParam('KFPW')  
        
        
    # def test_emptyTitleCode(self):
        # '''Pass empty title code - TC19'''
        # parentId = self.toolBox.getLatestParentPlatformIdFromDb()
        # childId = self.toolBox.getLatestPlatformIdFromDb()
        # titleCode = ""
        # self.toolBox.setTitleCodeParam(titleCode)   
        # resultDict = self.toolBox.chatSetting(CHATTYPE2, parentId, childUserId = childId)
        # self.assertEqual(resultDict.httpStatus(), 499, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '4003', 'Parameter values are empty for the request')
        # self.parameterValuesCheck1(resultDict, CHATTYPE2, str(parentId), str(childId), titleCode) 
        # self.toolBox.setTitleCodeParam('KFPW')
        
        
    # def test_noTitleCode(self):
        # '''Pass no title code (kfpw) to the service - TC20'''
        # childId = self.toolBox.getLatestPlatformIdFromDb()
        # token = self.toolBox.getChatTokenFromDb(childId)
        # titleCode = None
        # self.toolBox.setTitleCodeParam(titleCode)   
        # resultDict = self.toolBox.chatSetting(CHATTYPE1, token = token)
        # self.assertEqual(resultDict.httpStatus(), 400, "Http code: " + str(resultDict.httpStatus()))
        # self.errorXMLStructureCodeMessageCheck(resultDict, '4000', 'Not enough parameters to satisfy request')
        # self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    # 'chatType=' + CHATTYPE1 + '&service=chatSetting', \
                                    # 'Parameter values not matching')
        # self.toolBox.setTitleCodeParam('KFPW')
        
        
    ########################
    ###            Helper Methods          ###
    ########################
    
    def errorXMLStructureCodeMessageCheck(self, resultDict, code, message):
        '''checks error XML basic structure, error code and message'''
        self.assertTrue('errors' in resultDict, "XML structure failed, no errors tag")
        self.assertTrue('error' in resultDict['errors'], "XML structure failed, no error tag")                              
        self.assertTrue('code' in resultDict['errors']['error'], "XML structure failed, no code tag")
        self.assertTrue('message' in resultDict['errors']['error'], "XML structure failed, no message tag")
        self.assertTrue('parameters' in resultDict['errors']['error'], "XML structure failed, no parameters tag")
        self.assertEqual(resultDict['errors']['error']['code'], code, 'Error code not matched')
        self.assertEqual(resultDict['errors']['error']['message'], message, 'Error message not matched')
                                    
    def parameterValuesCheck1(self, resultDict, chatType, parentId, childId, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services passing Ids with chatType''' 
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&chatType=' + chatType +
                                    '&userId=' + parentId + '&service=chatSetting' + 
                                    '&childUserId=' + childId, 'Parameter values not matching')
    
    def parameterValuesCheck2(self, resultDict, chatType, token, titleCode = "KFPW"):
        '''Error XML validations specific to this Web Services token with chatType''' 
        self.assertEqual(resultDict['errors']['error']['parameters'], \
                                    'titleCode=' + titleCode + '&chatType=' + chatType +
                                    '&token=' + token + '&service=chatSetting', \
                                    'Parameter values not matching')
                                    
    def chatTypeChangeCheck(self, childId, chatType):
        result = self.toolBox.getUserById(childId)
        self.assertEqual(result['user']['chatType'], chatType, "chatType value is not matching")
        
                       
    
    def chatTypeParentControl(self, childId, chatType):
        result = self.toolBox.getUserById(childId)
        self.assertTrue('chatType' in result['user'], "chatType not present")
        self.assertEqual(result['user']['chatType'], chatType, "chatType value is not matching")
                        
    def chatTypeCheckMutedByCS(self, childId, chatType):
        result = self.toolBox.getUserById(childId)
        self.assertTrue('chatType' in result['user'], "chatType not present")
        self.assertTrue('MUTED' in result['user']['chatType'], "chatType message not present")   
        self.assertEqual(result['user']['chatType'], chatType, "chatType value is not matching")
        self.assertTrue('mutedUntil' in result['user'], "mutedUntil tag not presnt")
            
    def chatTypeCheckUnmutedByCS(self, childId, chatType):
        result = self.toolBox.getUserById(childId)
        self.assertTrue('chatType' in result['user'], "chatType not present")
        self.assertTrue('FULL' in result['user']['chatType'], "chatType message not present")   
        self.assertEqual(result['user']['chatType'], chatType, "chatType value is not matching")
        
        
    def successCheck(self, resultDict):
        self.assertEqual(resultDict.httpStatus(), 200, "Http code: " + str(resultDict.httpStatus()))
        self.assertTrue("success" in resultDict, "Success tag not matched")
        self.assertTrue("value" in resultDict['success'], "Value tag not matched")
        self.assertEqual(resultDict['success']['value'], 'TRUE', 'Value not matched')
        
    
    def muteStatusChangeThroughGman(self, childUsername, chatType=None):
        # Muting the user in Gman using child username. For testing purpose, CS Admin role has been selected in order to get access to mute/unmute user
        sel = self.selenium
        sel.open("https://backoffice01.qa.dwa.sjc.adm.gazint/login")
        #login to Gman (QA)
        sel.type("Login_Field", "ramya.nagendra")
        sel.type("Password_Field", "password")
        sel.click("loginBtn")
        sel.wait_for_page_to_load("30000")
        sel.click("//ol[@id='U_GUM_Nav']/li[1]/a/span[2]")
        time.sleep(3)
        sel.type("Login_Name", childUsername)
        sel.click("Search_Button")
        time.sleep(3)
        sel.click("link=" + childUsername)
        time.sleep(3)
        if chatType == "MUTED":
            sel.select("user_actions", "id=Mute_User")
            time.sleep(3)
            sel.type("Mute_Time", "30")
            sel.select("Time_Multi", "label=Minutes")
            time.sleep(3)
            sel.type("Create_User_Note", "30 minutes muted")
            sel.click("//div[3]/button[1]")
            time.sleep(3)
        else:
            sel.select("user_actions", "id=Unmute_User")
            time.sleep(3)
            sel.type("Create_User_Note", "unmuting user")
            sel.click("//div[3]/button[1]")
            time.sleep(3)
            
       
                
    def userBaseXmlCheck(self, userBaseXml):
        '''Helper function that verifies the structure of the user XML'''
        self.assertTrue('username' in userBaseXml,\
                        'XML structure unexpected: no username')
        self.assertTrue('friendFinderEnabled' in userBaseXml,\
                        'XML structure unexpected: no friendFinderEnabled')
        self.assertTrue('verifiedAdult' in userBaseXml,\
                        'XML structure unexpected: no verifiedAdult')
        self.assertTrue('chatType' in userBaseXml,\
                        'XML structure unexpected: no chatType')
        #self.assertTrue('mutedUntil' in userBaseXml,\
                       # 'XML structure unexpected: no muteUntil')
        self.assertTrue('loginCount' in userBaseXml,\
                        'XML structure unexpected: no loginCount')
        self.assertTrue('gameNewsletterEnabled' in userBaseXml,\
                        'XML structure unexpected: no gameNewsletterEnabled')
        self.assertTrue('emailAddress' in userBaseXml,\
                        'XML structure unexpected: no emailAddress')
        self.assertTrue('lastLogin' in userBaseXml,\
                        'XML structure unexpected: no lastLogin')
        self.assertTrue('remainingFreeLogins' in userBaseXml,\
                        'XML structure unexpected: no remainingFreeLogins')
        self.assertTrue('gameUserId' in userBaseXml,\
                        'XML structure unexpected: no gameUserId')
        self.assertTrue('accountReminderEnabled' in userBaseXml,\
                        'XML structure unexpected: no accountReminderEnabled')
        self.assertTrue('id' in userBaseXml,\
                        'XML structure unexpected: no id')    
    