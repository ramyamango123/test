from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class Alerts(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        
        
    def test1(self):
        # Enters 
        driver = self.driver
        driver.get("http://www.htmlcodetutorial.com/linking/linking_famsupp_70.html")
        url = driver.current_url
       # print url
        self.assertEqual("http://www.htmlcodetutorial.com/linking/linking_famsupp_70.html", driver.current_url)
       
        handles1 = driver.window_handles
        print handles1
        driver.find_element_by_link_text("this link").click()
        handles2 = driver.window_handles
        print handles2
       
        driver.switch_to_window(handles2[1])
        #time.sleep(5)
        self.driver.implicitly_wait(5)
        page_source = driver.page_source
        #print page_source
        self.assertIn("This is an example of a popup window.", page_source)
        self.assertEqual("http://www.htmlcodetutorial.com/linking/popup_test_a.html", driver.current_url)
        driver.switch_to_window(handles2[0])
        self.assertEqual("http://www.htmlcodetutorial.com/linking/linking_famsupp_70.html", driver.current_url)
            
##        
 
      
        
                
            
            
            
        
        
        
        
         
    
     
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
