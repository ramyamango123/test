from selenium import webdriver
#from selenium.webdriver.common.by import By
#from selenium.webdriver.support.ui import Select
#from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class exampleone(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
    
    def test1(self):
        driver = self.driver
        driver.get("http://www.python.org")
        self.assertIn("Python", driver.title)
        driver.find_element_by_id("q").clear()
        driver.find_element_by_id("q").send_keys("selenium")
        driver.find_element_by_id("submit").click()
        self.assertIn("Google", driver.title)
        
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
