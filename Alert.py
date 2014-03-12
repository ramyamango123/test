from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re
import MySQLdb

class assert_text(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        
        # Alert box
#    def test1(self):
#        driver = self.driver
#        driver.get("http://www.quackit.com/javascript/javascript_alert_box.cfm")
#        #driver.find_element_by_css_selector("input[type=\"button\"]").click()
#        # or
#        #driver.find_element_by_xpath("//input[@type = 'button']").click()
#        alert = driver.switch_to_alert()
#        print alert.text
#        alert.accept()
#        driver.close()
#        
      # Confirmation window  
          
#    def test2(self):
#        driver = self.driver
#        driver.get("http://www.quackit.com/javascript/codes/javascript_confirm.cfm")
#        result = driver.find_element_by_xpath("//input[@type = 'button']")
#        
#        data = result.get_attribute("value")
#        print data
#        self.assertEqual(data, "Click me...")
#        driver.find_element_by_xpath("//input[@type = 'button']").click()
#        alert = driver.switch_to_alert()
#        print alert.text
#        alert.dismiss()
##        driver.close()


    def test3(self):
        driver = self.driver
        driver.get("http://www.quackit.com/javascript/codes/javascript_prompt.cfm")
        result = driver.find_element_by_xpath("//input[@type = 'button']")
        
        data = result.get_attribute("value")
        print data
        self.assertEqual(data, "Click me...")
        driver.find_element_by_xpath("//input[@type = 'button']").click()
        alert = driver.switch_to_alert()
        alert.send_keys("ramya")
        
        #time.sleep(20)
        print alert.text
        alert.accept()
        time.sleep(9)
#        driver.close()
        
        
             
        
        
        
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()