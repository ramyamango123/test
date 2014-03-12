from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class ex1(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        #self.profile = driver.FirefoxProfile("c:\\")
        
        
#    def test1(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk")
#        driver.find_element_by_xpath("html/body/div[2]/ul/li[2]/a").click()
#        #driver.find_element_by_link_text("Chapter").click()
#        #driver.find_element_by_xpath("//*[@id='divontheleft']/input[2]").click()
#        driver.find_element_by_xpath("//input[@value='Sibling Button']").click()
#        driver.get_screenshot_as_file('C:\\result\\one.jpeg')
        
           
#    def test2(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk/chapter2")
#        result = driver.find_elements_by_tag_name("input")
#        count = 0
#        list1 = []
#        for eachinput in result:
#            print  eachinput.get_attribute("value") 
#            if eachinput.get_attribute("value") == "Random":
#                eachinput.click()
#                #time.sleep(30)
#                break
#            list1.append(eachinput.get_attribute("value"))
#            count += 1
#            
#        print count, len(list1), list1
        
            
#  q = driver.find_element_by_xpath("//*[@id='query']")
#        input_value =  q.get_attribute("value") 
#    def test2(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk/chapter4")
#        print driver.title
#        self.assertEqual(str(driver.title), "Selenium: Beginners Guide")
#        w = driver.page_source
       # print w
#        result1= driver.find_element_by_id("selecttype")
#        result2 = result1.find_elements_by_tag_name("option")
#        
#        Select(driver.find_element_by_id("selecttype").select_by_index(2))
              
                      
#        print result2
#        count = 0
#        list1 = []
#        for i in result2:
#            print i.text
#            list1.append(i.text)
#        print list1, len(list1)
#        if (len(list1) == 4):
#            print "select has correct number of options"
#        else:
#            print "something wrong"
#            
#            if i.text == "Selenium Grid":
#                i.click()
#                time.sleep(20)
#                break
#        result3 = driver.find_element_by_xpath("//*[@id='selecttype']")
##        
#    def test3(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk/chapter4")
#        print driver.title
#        self.assertEqual(str(driver.title), "Selenium: Beginners Guide", "page title doesnt match")
#        w = driver.page_source
        
#                     
#    def test4(self):
#        driver = self.driver
#        driver.get("http://book.theautomatedtester.co.uk/chapter4")
#        
        
        
        
        
        
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
