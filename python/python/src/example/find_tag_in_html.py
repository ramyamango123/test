from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class find_tag_in_html(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        
        
#    def test1(self):
#        # Enters 
#        driver = self.driver
#        driver.get("http://www.jobserve.com/us/en/Job-Search/")
#        driver.find_element_by_xpath("//*[@id='ddcl-selInd']/span/span").click()
#        #driver.find_element_by_xpath("//*[@id='ddcl-selInd-ddw']/div/div[4]/label").click()
#        #time.sleep(5)
#        xpath_start = "//*[@id='ddcl-selInd-ddw']/div/div["
#        xpath_end = "]/label"
#        for i in range(3, 8, 2):
#            print i
#            driver.find_element_by_xpath(xpath_start + str(i) + xpath_end).click()
#        time.sleep(6)
        
#    def test2(self):
#        # Enters 
#        driver = self.driver
##        driver.get("http://www.jobserve.com/us/en/Job-Search/")
##        result = driver.find_elements_by_tag_name("input")
##        print result
#        in_file = open("C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\other_python_prob\data1.txt")
#        html_file = in_file.read()
#        print html_file
#        in_file.close()
#        re_pattern = re.search("\<a.*?\</a>", html_file)

#    def test3(self):
#        driver = self.driver
#        driver.get("http://timesofindia.indiatimes.com/")
#        self.driver.implicitly_wait(12)
#        quiz_xpath = driver.find_element_by_id("mathq2").click()
#        path =  driver.find_element_by_xpath("//span[@id='mathq2']")
#        print "path", path
#        print quiz_xpath.text
#        #driver.find_element_by_id("mathuserans2").send_keys("4")
#        time.sleep(17)
        #print result
        
#        driver.get("http://www.foodnetwork.com/snacks/package/index.html")
##        self.driver.implicitly_wait(12)
#        driver.find_element_by_xpath("//*[@id='hd-search-input']")
#        driver.find_element_by_id("hd-search-input").send_keys("potato")
#        driver.find_element_by_id("hd-search-submit").click()
    
     
    
     
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
