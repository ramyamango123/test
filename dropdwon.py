from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class exampleone(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        



#    def test1(self):
#        driver = self.driver
#        #list1 = []
#        driver.get("http://www.echoecho.com/htmlforms11.htm")
#        elementsList = driver.find_elements_by_name("dropdownmenu")
#        for i  in elementsList:
#            x = i.text
#            result = x.split('\n')
#            print "result", result
#            
#        data = [str(item) for item in result]
#        print data
#        for j in data:
#            print j
#            if i.text == "Cheese":
#                print "Found Cheese in the list"
            
        

#        elements = driver.find_element_by_name("dropdownmenu")
##       print len(elements)
#
#        e = driver.find_elements_by_tag_name("option")


    def test2(self):
        driver = self.driver
        driver.maximize_window()
        list1 = []
        driver.get("http://www.echoecho.com/htmlforms11.htm")
        elementsList = driver.find_element_by_name("dropdownmenu")
        for option in elementsList.find_elements_by_tag_name("option"):
            print option.text
            if option.text == "Milk":
                option.click()
                time.sleep(30)


    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
