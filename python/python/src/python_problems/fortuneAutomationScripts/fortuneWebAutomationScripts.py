'''Fortune Online Web Automation Test Scripts
Includes both positive and negative test cases
Created by Ramya Nagendra on 12.15.2010.'''


import sys
import datetime
from selenium import selenium
import unittest, time, re

Email = "fortunetester@gmail.com"
Password = "gazillion"

import HTMLTestRunner

class FO_basic_sample_test(unittest.TestCase):

    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com/")
        self.selenium.start()
    
    def test1_URLCheck(self): 
        ''' To check the functionality of FotuneOnline URL -- TC1'''
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/")
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
       
    def test2_checkRegistrationPage(self): 
        ''' To check if Register button click leads to Registration page -- TC2'''
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[1]/a/span")
        time.sleep(5)
        try: self.assertEqual(u"Registration \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Registration"))
        except AssertionError, e: self.verificationErrors.append(str(e))
                 
    def test3_checkBrowserbackFromRegistrationPage(self): 
        ''' To check if browser back button click from registration page directs to Fortune Online page -- TC3'''
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[1]/a/span")
        time.sleep(5)
        try: self.assertEqual(u"Registration \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        time.sleep(5)
        
        try: self.failUnless(sel.is_text_present("Registration"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        sel.go_back()
        time.sleep(5)
        
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test4_checkLoginPage(self):
        ''' To check if Login button click directs user to Login page -- TC4'''
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        try: self.assertEqual(u"Login \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_text_present("Login"))
        except AssertionError, e: self.verificationErrors.append(str(e))
                 
    def test5_checkBrowserbackFromLoginPage(self): 
        ''' To check if browser back button click from login page directs to Fortune Online page -- TC5'''
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        try: self.assertEqual(u"Login \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_text_present("Login"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.go_back()
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test6_checkValidLogin(self): 
        '''  To check if user can login successfully with valid values -- TC6'''
        sel = self.selenium
        myUrl = sel.get_expression("http://www.playfortuneonline.com/playnow")
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u"Home \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_text_present("Play Free Now"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def test7_checkWithInvalidEmail(self): 
        '''  To check if user gets an error message when login attempt is made with invalid email address -- TC7 '''
        InvalidEmail = "invalid"
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", InvalidEmail)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Invalid Email Address"))
        except AssertionError, e: self.verificationErrors.append(str(e))

    def test8_checkWithIncorrectEmailAddressOrPassword(self): 
        '''  To check if user gets an error message when login attempt is made with incorrect email address/password -- TC8'''
        IncorrectEmail = "incorrectemail@gazillion.com"
        IncorrectPassword = "incorrect"
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", IncorrectEmail)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Login incorrect"))
        except AssertionError, e: self.verificationErrors.append(str(e))  
        # Logging in with incorrect password
        sel.type("Username_Field", Email)
        sel.type("Password_Field", IncorrectPassword)
        sel.click("submit")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Login incorrect"))
        except AssertionError, e: self.verificationErrors.append(str(e))  
    
    def test9_checkPrivacyPolicyLinkOnLoginPage(self): 
        '''  To check the functionality of Privacy Policy link on Login Page -- TC9'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)  
        sel.click("link=Privacy Policy")
        try: self.failUnless(sel.is_text_present("Privacy Policy"))
        except AssertionError, e: self.verificationErrors.append(str(e))      
     
    def test10_checkSignupLinkOnLoginPage(self): 
        '''  To check the functionality of Signup now link on Login Page -- TC10'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)  
        sel.click("link=Sign up now!")
        sel.wait_for_page_to_load("30000")
        try: self.failUnless(sel.is_text_present("Registration"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.go_back()
        time.sleep(5)
        try: self.assertEqual(u"Login \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def test11_checkPasswordLinkOnLoginPage(self): 
        '''  To check the functionality of Reset password service  -- TC11'''
        # Email Account details :
        # Email - fortunetester@gmail.com
        # Password - gazillion
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)  
        sel.click("link=exact:password?")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Forgot Password"))
        except AssertionError, e: self.verificationErrors.append(str(e))

    def test12_checkPlayFreeNowTabFunctionality(self): 
        ''' To check the functionality of Play Free Now button on FortuneOnline home page -- TC12'''
        sel = self.selenium
        myUrl = sel.get_expression("http://www.playfortuneonline.com/playnow")
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u"Home \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Play Free Now"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open(myUrl)
        time.sleep(5)
        try: self.assertEqual(u'Playnow \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
       
    def test13_checkFortuneOnlineHomeTabs(self): 
        #''' To check the functionality of tabs Media, Game Features, Support, Community on FortuneOnline home page -- TC13'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))

        # Checking the functionality of  "Media" tab
        sel.click("link=Media")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Media Panel"))
        except AssertionError, e: self.verificationErrors.append(str(e))

        #Checking the functionality of "Game Features" tab
        sel.click("link=Game Features")
        time.sleep(5)
        try: self.assertEqual(u'Game Features \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))

        #Checking the functionality of "Support" tab
        sel.click("link=Support")
        time.sleep(5)
        try: self.assertEqual(u'Support \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try:self.failUnless(sel.is_text_present("Support"))
        except AssertionError, e: self.verificationErrors.append(str(e))

        #Checking the functionality of  "Community" tab
        sel.click("link=Community")
        time.sleep(5)
        self.assertEqual(u'Developer\u2019s Blog \xab Fortune Online', sel.get_title())
        try: self.failUnless(sel.is_text_present("Community Blog"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test14_checkFortuneOnlineHomeTabsFooterSection(self): 
        ''' To check the functionality of FO home page tabs present on the footer section -- TC14'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking the functionality of  "Media" tab present on the footer section
        sel.click("//ul[@id='footer_navigation']/li[1]/a")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Media Panel"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of "Game Features" tab present on the footer section
        sel.click("//ul[@id='footer_navigation']/li[2]/a")
        time.sleep(5)
        try: self.assertEqual(u'Game Features \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of "Support" tab present on the footer section
        sel.click("//ul[@id='footer_navigation']/li[3]/a")
        time.sleep(5)
        try: self.assertEqual(u'Support \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try:self.failUnless(sel.is_text_present("Support"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of  "Community" tab present on the footer section
        sel.click("//ul[@id='footer_navigation']/li[5]/a")
        time.sleep(5)
        self.assertEqual(u'Developer\u2019s Blog \xab Fortune Online', sel.get_title())
        try: self.failUnless(sel.is_text_present("Community Blog"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test15_checkFortuneOnlineHomeTabsOnDifferentFOWebpages(self):
        ''' To check the fucntionality of Fortune Online tabs on different fortune web pages -- TC15'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking the functionality of Fortune Online tab from "Media" web page
        sel.click("link=Media")
        time.sleep(5)
        sel.click("link=Fortune Online")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of  Fortune Online tab from "Game Features" web page
        sel.click("link=Game Features")
        time.sleep(5)
        sel.click("link=Fortune Online")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of  Fortune Online tab from "Support" web page
        sel.click("link=Support")
        time.sleep(5)
        sel.click("link=Fortune Online")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #Checking the functionality of  Fortune Online tab from "Community" web page
        sel.click("link=Community")
        time.sleep(5)
        sel.click("link=Fortune Online")
        time.sleep(5)
        try: self.assertEqual(u'Home \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e)) 
    
    def test16_checkForumTabFunctionality(self): 
        ''' To check the functionality of "Forums" tab on FortuneOnline home page -- TC16'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u"Home \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open(myUrl)
        time.sleep(5)
        try: self.assertEqual("Fortune Online Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_element_present("//img[@alt='Phorum']"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test17_checkGameFeatuesPageImages(self):
        ''' To check if the game character images are present -- TC17'''
        sel = self.selenium
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        sel.click("link=Game Features")
        time.sleep(5)
        try: self.assertEqual(u'Game Features \xab Fortune Online', sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_element_present("//img[@alt='Warrior']"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_element_present("//img[@alt='Guardian']"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        try: self.failUnless(sel.is_element_present("//img[@alt='Mage']"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def test18_checkForumPageSearchFunctionality(self): 
        ''' To check the Search text box functionality in Forums page -- TC18'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        search_string = "fortune"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl)
        time.sleep(5)
        try: self.assertEqual("Fortune Online Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #  Inputting text in search text field
        sel.type("search", search_string)
        sel.click("//input[@value='Search']")
        time.sleep(10)
        try: self.assertEqual(u'Fortune Online Community Forums :: Search - ' + search_string, sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Verifying  "Search" text
        try: self.failUnless(sel.is_text_present("Search"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Verifying "Search" Element
        try: self.failUnless(sel.is_element_present("//input[@value='Search']"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Verifying the search result text with the help javaScript  function  this.browserbot.bodyText().match()
        hits = sel.get_eval("this.browserbot.bodyText().match(/Results \\d{1,10} - \\d{1,10} of\\s\\d{1,10}.*/ )")
        try: self.failUnless(sel.is_text_present("regex: [Results 1 - \\d{1,10} of\\s\\d{1,10}.*)]"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    def test19_editPersonalProfileCommunityCenterPage(self): 
        ''' Checking if Personal Profile data can be edited successfully -- TC19'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        search_string = "fortune"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl)
        time.sleep(5)   
        # Verifying Personal Profile text
        sel.click("link=Control Center")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Personal Profile"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Editing Profile's Real name
        sel.click("link=Edit My Profile")
        time.sleep(5)  
        sel.type("real_name", "test")
        sel.click("//input[@value=' Submit ']")
        time.sleep(5)  
        try: self.failUnless(sel.is_text_present("Profile successfully updated."))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Editing Profile's Signature
        sel.click("link=Edit Signature")
        time.sleep(5) 
        sel.type("body", "selenium test")
        sel.click("//input[@value=' Submit ']")
        time.sleep(5)  
        try: self.failUnless(sel.is_text_present("Profile successfully updated."))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Editing Profile's Privacy Options
        sel.click("link=Edit My Privacy Options")
        time.sleep(5)
        sel.uncheck("hide_activity value=1")
        sel.check("hide_activity value=1")
        sel.click("//input[@value=' Submit ']")
        sel.wait_for_page_to_load("30000")
        try: self.failUnless(sel.is_text_present("Profile successfully updated."))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking functionality of View and Join Groups link
        sel.click("link=View and Join Groups")
        time.sleep(5)  
        try: self.failUnless(sel.is_text_present("View and Join Groups"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking functionality of Show Followed Topics link
        sel.click("link=Show Followed Topics")
        time.sleep(5)  
        try: self.failUnless(sel.is_text_present("Followed Topics"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking functionality of Forum Settings link 
        sel.click("link=Forum Settings")
        time.sleep(5)  
        try: self.failUnless(sel.is_text_present("Forum Settings"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # Checking functionality of Forum List link 
        sel.click("link=Forum List")
        time.sleep(5)          
        try: self.failUnless(sel.is_text_present("Fortune Online Beta"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def test20_checkprivateMessageslink(self): 
        ''' Checking the functionality of Private Messages link on Community Forums page -- TC20'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        search_string = "fortune"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl)
        time.sleep(5)   
        sel.click("link=Control Center")
        sel.wait_for_page_to_load("30000")
        
        # Verifying Private Messages link
        sel.click("link=Private Messages")
        sel.wait_for_page_to_load("30000")
        try: self.failUnless(sel.is_text_present("Private Messages"))
        except AssertionError, e: self.verificationErrors.append(str(e))    
    
 
    def test21_checkLogoutOnForumsPage(self): 
        ''' Checking the functionality of Logout link on Community Forums page -- TC21'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        search_string = "fortune"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl)
        time.sleep(5)   
        try: self.assertEqual("Fortune Online Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        #verifying Logout link on Forums page
        sel.click("link=Log Out")
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
    
    def test22_checkLogoutTabOnFOHomePage(self): 
        ''' Checking the functionality of Logout tab on FO home page-- TC22'''
        sel = self.selenium
        myUrl = "http://community.playfortuneonline.com"
        search_string = "fortune"
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", Email)
        sel.type("Password_Field", Password)
        sel.click("submit")
        time.sleep(5)
        try: self.assertEqual(u"Home \xab Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        # verifying Logout tab  on Fortune Online Home page
        sel.click("link=Logout")
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
             
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    # Here we can opt either to run tests using -t or without.  
    text_output = 0
for i in sys.argv:
    if i == "-t":
        text_output = 1
        # -t must be removed from list as it may be existing from previous runs
        sys.argv.remove("-t")
#If we run the script with -t, it will print the result on cmd screen  
if text_output == 1:
    unittest.main()
# If we run the script without -t, HTML test runner report will be generated with the current date and time
else:
    suite = unittest.TestSuite()
    suite.addTests([
    unittest.defaultTestLoader.loadTestsFromTestCase(FO_basic_sample_test)])
    today = datetime.datetime.today()
    todayStr = str(today.year) + "_" + str(today.month) + "_" + str(today.day) + "_" + str(today.hour) + "_" + str(today.minute) + "_" + str(today.second)
    fp = file('my_report' + todayStr + '.html', 'wb')
    runner = HTMLTestRunner.HTMLTestRunner(
                stream=fp, verbosity=10,
                title='Fortune Online Automation Test Scripts Output ',
                description='This report is generated by HTMLTestRunner'
                )
    # Use an external stylesheet.
    # See the Template_mixin class for more customizable options
    runner.STYLESHEET_TMPL = '<link rel="stylesheet" href="my_stylesheet.css" type="text/css">'

    # run the test
    runner.run(suite)
    #unittest.main()
