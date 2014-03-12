import sys

from testSuiteBase import TestSuiteBase
from selenium import selenium
import unittest, time, re

class FO_basic_sample_test(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com/")
        self.selenium.start()
    
    def test_login(self):
        sel = self.selenium
        myUrl1 = "http://community.playfortuneonline.com"
        myUrl2 = sel.get_expression("http://www.playfortuneonline.com/playnow")
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Play Free Now"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open(myUrl1)
        try: self.assertEqual("Dragon Lords Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.click("link=Log Out")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl2)
        try: self.assertEqual(u'Playnow \xab Fortune Online', sel.get_title())
        #u'Fortune Online', sel.get_title()
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open("http://www.playfortuneonline.com/home")
        time.sleep(5)
        sel.click("link=Logout")
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
