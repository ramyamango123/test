from selenium import selenium
import unittest, time, re

class FO_basic_login(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com/")
        self.selenium.start()
    
    def test_f_o_basic_login(self):
        sel = self.selenium
        myUrl1 = "http://community.playfortuneonline.com"
        myUrl2 = sel.get_expression("http://www.playfortuneonline.com/playnow")
        sel.open("https://www.playfortuneonline.com")
        sel.wait_for_page_to_load("3000")
        sel.click("//div[@id='entry']/p[2]/a/span")
        sel.wait_for_page_to_load("30000")
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        sel.wait_for_page_to_load("3000")
        try: self.failUnless(sel.is_text_present("Play Free Now"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open(myUrl1)
        try: self.assertEqual("Dragon Lords Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.click("link=Log Out")
        sel.wait_for_page_to_load("3000")
        sel.click("//div[@id='entry']/p[2]/a/span")
        sel.wait_for_page_to_load("30000")
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        sel.wait_for_page_to_load("3000")
        sel.open(myUrl2)
        try: self.assertEqual(u'Playnow \xab Fortune Online', sel.get_title())
        #u'Fortune Online', sel.get_title()
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open("http://www.playfortuneonline.com/home")
        sel.wait_for_page_to_load("3000")
        sel.click("link=Logout")
        sel.wait_for_page_to_load("30000")
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
