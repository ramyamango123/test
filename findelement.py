from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import unittest, time


        
class findelement(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        
        
    def test1(self):
        driver = self.driver
        driver.get("http://www.google.co.in")
        browser = self.driver
        print "Open the google page"
        #browser.find_element_by_name("q").send_keys("selenium RC")
        print "Enter the search test Selenium RC"
        browser.find_element_by_id("gbqfq").send_keys("selenium rc")
        browser.find_element_by_id("gbqfb").click()
        #browser.implicitly_wait(40)
#        time.sleep(10)
#        browser.find_element_by_name("q").send_keys(Keys.RETURN)
        print "Click on Google Search button"
       # browser.close()

    def tearDown(self):
            self.driver.quit()
        #self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
