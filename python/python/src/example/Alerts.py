from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class Alerts(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        #self.driver.implicitly_wait(30)
        
        
#    def test1(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk")
#        driver.find_element_by_xpath("html/body/div[2]/ul/li[2]/a").click()





#
#    def test2(self):
#        driver = self.driver
#        driver.get("https://twitter.com/login")
#        driver.find_element_by_xpath("(//input[@name='session[username_or_email]'])[2]").clear()
#        driver.find_element_by_xpath("(//input[@name='session[username_or_email]'])[2]").send_keys("ramarus")
#        driver.find_element_by_xpath("(//input[@name='session[password]'])[2]").clear()
#        driver.find_element_by_xpath("(//input[@name='session[password]'])[2]").send_keys("mtsassay123")
#
#        toggle_box = driver.find_element_by_xpath("//*[@id='page-container']/div[2]/div[1]/form/div[2]/fieldset/label/input")
#        toggle_box.click()
#        driver.find_element_by_xpath("(//button[@type='submit'])[2]").click()
#        self.driver.implicitly_wait(5)
#        print driver.title
#        self.assertEqual("Twitter / Home", driver.title)
        
#    def test3(self):
#        # Clicks Ok on alert window
#        driver = self.driver
#        driver.get("http://www.javascriptcity.com/scripts/alerts/salert3.htm")
#        driver.find_element_by_css_selector("input[type=\"submit\"]").click()
#        alert = driver.switch_to_alert()
#        print alert.text
#        #time.sleep(5)
#        alert.accept()
        
#    def test4(self):
#        # Enters name in prompt window
#        driver = self.driver
#        driver.get("http://www.javascriptcity.com/scripts/alerts/sprompt1.htm")
#        driver.find_element_by_link_text("Example Link").click()
#        alert = driver.switch_to_alert()
#        time.sleep(5)
#        alert.dismiss()
       # alert.send_keys("ramyarrrrrrr")
       # print alert.text
        #time.sleep(5)
       # alert.accept()
       # time.sleep(5)

#    def test5(self):
#        # Enters 
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk/chapter4")
#        inputs = driver.find_elements_by_tag_name("input")
#        print inputs
#        for input in inputs:
#            print input.get_attribute("id")
            

    def test6(self):
        # Enters 
        driver = self.driver
        search_string = "Selenium Grid"
        driver.get("http://book.theautomatedtester.co.uk/chapter4")
        bodytext = driver.page_source
        print bodytext
        result = self.is_text_present(search_string, bodytext)
        print "result: ", result
       # inputs = driver.find_elements_by_tag_name("input")
        
    def is_text_present(self, string, bodytext):
        
        if string in bodytext:
            return True
        else:
            return False
        
    
     
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
