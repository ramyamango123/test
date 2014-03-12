from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class exampleone(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        #self.profile = driver.FirefoxProfile("c:\\")
        
        
    def test1(self):
        driver = self.driver
        #===============================================================================
        #example 1 ### Navigate to example.com, find element by tag name and ASSERT the text
        #driver.get("http://www.echoecho.com/htmlforms11.htm")
        driver.get("http://www.idocs.com/tags/linking/linking_famsupp_114.html")
        elementList = driver.find_element_by_name("gourl")
        print elementList.text
        for option in elementList.find_elements_by_tag_name("option"):
            if option.text == "Ninth Wonder":
                option.click()
                driver.find_element_by_css_selector("input[type=\"SUBMIT\"]").click()
                time.sleep(30)
                break
        
#        elementList = driver.find_elements_by_name("dropdownmenu")
#        
# #       options = elementList.find_elements_by_tag_name("option")
#        options = driver.find_elements_by_tag_name("option")
#        #print options.text
#        for eachinput in options:
#            print eachinput.text
#            print  eachinput.get_attribute("value") 
            
        
        
#        driver.maximize_window()

#        options = elementList.find_elements_by_tag_name("option")
#        print elements
#        for option in options:
#            print option.text
#            if option.text == "Fresh Milk":
#                option.click()
#                #time.sleep(30)
#                break
#        for option in options:
#            print option.text
#            if option.text == "Cheese":
#                option.click()
#                time.sleep(30)
#                break
#        clicked = False
#        for element1 in elementList:
#            print element1
#            for option in element1.find_elements_by_tag_name("option"):
#                print option.text
#                if option.text == "Cheese":
#                    option.click()
#                    clicked = True
#                    break
#            if clicked:
#                break
        #time.sleep(400)
            
#      clicked = False
#        for elements1 in elementsList: 
#            print elements1.text              
#            for option in elements1.find_elements_by_tag_name('option'):
#                print option.text
#                if (option.text == 'Milk'):
#                    option.click()
#                    clicked = True
#                    break
#            if clicked:
#                break
#        time.sleep(350)             
#                


    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
