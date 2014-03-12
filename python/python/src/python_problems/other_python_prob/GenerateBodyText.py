from selenium import selenium
import unittest, time, re

class GenerateBodyText(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "file:///C:/Users/Ramya/java_files/company_shipping.htm")
        self.selenium.start()
    
    def test_1(self):
        sel = self.selenium
        sel.open("file:///C:/Users/Ramya/java_files/company_shipping.htm")
        sel.wait_for_page_to_load("30000")
        pageBodyText = sel.bodyText()
        print "pageBodyText", pageBodyText
        try: self.failUnless(sel.is_text_present("Your Company"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
