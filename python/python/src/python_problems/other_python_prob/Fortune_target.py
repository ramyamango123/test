from selenium import selenium
import unittest, time, re

class Fortune_target(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com")
        self.selenium.start()
    
    def test1(self):
        sel = self.selenium
        sel.open("http://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        time.sleep(3)
        print sel.get_title()
        self.assertEqual(u"Home \xab Fortune Online", sel.get_title())
        time.sleep(5)
        sel.click("//div[@id='site_container']/div[@id='content_container']/div[@id='header']/ul/li[6]/a")
        #sel.click("link=Forum")
        sel.select_window("Forums")
        self.assertEqual("Dragon Lords Community Forums", sel.get_title())
        sel.close()
        sel.select_window("null")
        self.assertEqual("Fortune Online", sel.get_title())


        #p = sel.get_title()
        #print p
       
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
