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
        #        #example 1 ### Navigate to example.com, find element by tag name and ASSERT the text
#        driver.get("http://www.example.com")
#        element = driver.find_element_by_tag_name('h1')
#        print "element", element.text
#        assert element.text == "Example Domains"

        ## or
        
#        driver.get("http://www.example.com")
#        element = driver.find_element_by_tag_name("h1")
#        print element.text
##        mytext = element.text
#        if ("Example Domains" in element.text):
#            print "The searched text is present"
#        else:
#            print "Searched text not present"
#            
#        if element.text != u'Example Domains':
#            print "Verify Failed: element text is not %r" % element.text
#
#        print "element", element.text
#        assert element.text == "Example Domains"
        
#===============================================================================

#===============================================================================
#       ### example 2 ### ctrl + 3 (comment) and ctrl +shift + 3(uncomment)
# #        driver.get("http://www.python.org")
# #        print "driver.title1", driver.title
# #        self.assertIn("Python", driver.title)
# #        driver.find_element_by_id("q").clear()
# #        driver.find_element_by_id("q").send_keys("selenium")
# #        driver.find_element_by_id("submit").click()
# #        print "driver.title2", driver.title
# #        self.assertIn("Google", driver.title)
#===============================================================================
#===============================================================================
# #        ### example 3
# #        driver.get("http://www.echoecho.com/htmlforms11.htm")
# #        print "title", driver.title
# #        self.assertIn("Forms", driver.title)
# #        Select(driver.find_element_by_name("dropdownmenu")).select_by_visible_text("Cheese")
# #       
#===============================================================================
        
    ### example 5
    
        driver.get("http://www.echoecho.com/htmlforms11.htm")
        elementsList = driver.find_elements_by_name("dropdownmenu")

#        elements = driver.find_element_by_name("dropdownmenu")
##       print len(elements)
#
#        e = driver.find_elements_by_tag_name("option")

        #print  "find_element_by_tag_name_elements", elements.find_element_by_tag_name("option").text
        
#        print elements
#        for i in e:
#            print i
#            if i.text == "Cheese":
#                i.click()
#                break
#        time.sleep(350)

        clicked = False
        for elements1 in elementsList: 
            print elements1.text              
            for option in elements1.find_elements_by_tag_name('option'):
                print option.text
                if (option.text == 'Milk'):
                    option.click()
                    clicked = True
                    break
            if clicked:
                break
        time.sleep(350)
    
    def tearDown(self):
        self.driver.quit()
        #self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
