from selenium import selenium
import unittest, time, re

class Fortune_target1(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com/home")
        self.selenium.start()
    
    def test1(self):
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com/home")
        p = sel.get_title()
        print p
        self.assertEqual(u'Fortune Online', sel.get_title())
     
           
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()