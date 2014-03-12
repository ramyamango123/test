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
        
        
#    def test1(self):
#        driver = self.driver
#        driver.get("http://www.quikr.com/")
#        driver.find_element_by_id("query").clear()
#        driver.find_element_by_id("query").send_keys("selenium")
#        driver.find_element_by_css_selector("input.search_button_sprite").click()
#        q = driver.find_element_by_id("query")
#        input_value = q.get_attribute("value")
#        print input_value
#        assert input_value == "selenium"


# Pop-up windows

    def test2(self):
        driver = self.driver
        driver.get("http://www.htmlcodetutorial.com/linking/linking_famsupp_70.html")
        url = driver.current_url
        print url
        main_page_handles = driver.window_handles
        print main_page_handles 
        driver.find_element_by_link_text("this link").click()
        pop_up_handles = driver.window_handles
        print pop_up_handles
        
        driver.switch_to_window(pop_up_handles[1])
        self.driver.implicitly_wait(30)
        page_source = driver.page_source
        self.assertIn("This is an example of a popup window.", page_source, "This text not present in the page")
        driver.switch_to_window(main_page_handles[0])
        self.assertEqual("HTML Popup Windows - HTML Code Tutorial", driver.title)
    
       
        


        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()