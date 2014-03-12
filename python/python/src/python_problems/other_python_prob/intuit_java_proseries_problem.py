from selenium import selenium
import unittest, time, re

class Untitled(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.deanza.edu/")
        self.selenium.start()
    
    def test_untitled(self):
        sel = self.selenium
        sel.open("file:///C:/Users/Ramya/java_files/company_shipping.htm")
        sel.sleep(3000)
        pageBodyText = sel.bodyText()
        print "pageBodyText", pageBodyText
        try: self.failUnless(sel.is_text_present("Your Company"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
