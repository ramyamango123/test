from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re

class prac_find_tag_in_html(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()
        
        
        
#    def test1(self):
#        # Enters 
#        driver = self.driver
#        driver.get("http://www.jobserve.com/us/en/Job-Search/")
#        driver.find_element_by_xpath("//span[@id='ddcl-selInd']/span").click()
#        xpath_start = "//div[@id='ddcl-selInd-ddw']/div/div["
#        xpath_end = "]/label"
#        
#        for i in range(3,8,2):
#            print i
#            driver.find_element_by_xpath(xpath_start + str(i) + xpath_end).click()
#        time.sleep(6)
        
#    def test2(self):
#        driver = self.driver
#        list1 = []
#        driver.get("http://www.jobserve.com/us/en/Job-Search/#")
#        res = driver.find_element_by_id("lab3").text
#        print res
#        assert res == "Jobs by Industry"
#        self.assertIn("Jobs by Industry", driver.find_element_by_id("lab3").text )
#        
#        if ("Jobs by Industry" in res):
#            print "The searched text is present"
#        else:
#            print "Searched text not present"
#            
#        self.assertEqual("Jobs by Industry", driver.find_element_by_id("lab3").text, "messages not equal")
#
#        
#        print driver.current_url

        
        
#        res1 = driver.find_elements_by_tag_name("span")
#        for i in res1:
#            r = i.text.rstrip()
#            if r != 
#            list1.append(r)
#        print list1
#            
        
    def test3(self):
        driver = self.driver
        driver.implicitly_wait(40)
        
        driver.get("http://timesofindia.indiatimes.com/")
        driver.find_element_by_link_text("click here to go to timesofindia.com").click()
        driver.find_element_by_css_selector("img[alt=\"The Times of India\"]").click()
        #driver.find_element_by_link_text("City").click()
        driver.find_element_by_link_text("POLL").click()
        driver.find_element_by_xpath("(//input[@name='PRadio'])[3]").click()
        #time.sleep(3000)
        res = driver.find_element_by_id("mathq").text
        print res
        pattern = re.search("(\d+)\s+\+\s+(\d+)\s=", res)
        nums_to_add = int(pattern.group(1)) + int(pattern.group(2))
        print nums_to_add
        driver.find_element_by_id("mathuserans").clear()
        driver.find_element_by_id("mathuserans").send_keys(nums_to_add)
  #      time.sleep(3000)
        driver.find_element_by_css_selector("div.homesprite.vot").click()
        
       
        
        
        
        
            
        

        



     
    def tearDown(self):
        self.driver.quit()
        
if __name__ == "__main__":
    unittest.main()
