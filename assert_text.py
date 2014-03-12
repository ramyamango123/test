from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re
import MySQLdb

class assert_text(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        #self.profile = driver.FirefoxProfile("c:\\")
        
        
    def test1(self):
        driver = self.driver
        driver.get("http://www.quikr.com/")
        
        
        p = driver.find_element_by_xpath("//*[@id='query']")
#        print "p", p.get_attribute("value")
        
        driver.find_element_by_id("query").send_keys("selenium test")
        driver.find_element_by_css_selector("input.search_button_sprite").click()
        
        q = driver.find_element_by_xpath("//*[@id='query']")
        input_value =  q.get_attribute("value") 
        print input_value
        assert input_value == "selenium test"
        
 #       print "q", q.get_attribute("value")
#        driver.find_element_by_css_selector("input.search_button_sprite").click()
        
#        self.assertIn("classifieds", driver.find_element_by_id("welcome_text").text)
#                
#        print driver.find_element_by_id("welcome_text").text
#        
#   #     e = driver.find_element_by_id("query").send_keys("selenium test")
#        e = driver.find_element_by_id("query")
#        print e.text

#       # print  driver.find_element_by_id("query").send_keys("selenium test").text
#        
#       
##        driver.find_element_by_css_selector("input.search_button_sprite").click()
#        driver.find_element_by_xpath("//*[@id='searchFormIndex']/input[3]").click()
#        print driver.find_element_by_xpath("//*[@id='welcome']/div/h1").text
#        self.assertIn("selenium test", driver.find_element_by_xpath("//*[@id='welcome']/div/h1").text)

    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
