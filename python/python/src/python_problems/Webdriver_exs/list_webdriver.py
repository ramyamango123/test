from selenium import webdriver  # @UnresolvedImport
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
import unittest, time, re
import datetime
import sys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import HTMLTestRunner
import traceback
import pprint
p = pprint.pprint



class list_webdriver(unittest.TestCase):
    def setUp(self):
        ' In ff we have to initiate as webdriver.Firefox()'
        self.driver = webdriver.Firefox()
        self.driver.implicitly_wait(20) #wait 20 seconds when doing a find_element before carrying on
       
       
#        
#     def test1(self):
#         # Enters
#         driver = self.driver
#         driver.get("http://www.google.com")
#         #browser = self.driver
#         print "Open the google page"
#           
#         #driver.find_element_by_id("searchText").send_keys("selenium IDE")
#         #driver.find_element_by_id("searchSubmit").click()
#           
#         driver.find_element_by_name("q").send_keys("Selenium IDE\n")
#         el = WebDriverWait(driver, 10).until(lambda x:
#         x.find_element_by_partial_link_text("Selenium IDE")).find_element_by_tag_name("em")
#         # should print "bold"
#         print el.value_of_css_property("font-weight")
       
#     def test2(self):
#         driver = self.driver
#         driver.get("http://www.google.co.in")
#         driver.find_element_by_id("gbqfq").send_keys("selenium IDE")
#         time.sleep(1)
#        # el = driver.find_element_by_partial_link_text("Selenium IDE")
#         # To get text of find element use text
#         #result = el.find_element_by_tag_name("em").text
#         result = driver.find_element_by_partial_link_text("Selenium IDE").find_element_by_tag_name("em")
#         print result.value_of_css_property("font-weight")
       # print result
        #print result.value_of_css_property("font-weight")
       
    def test3(self):
        driver = self.driver
        driver.get("http://www.google.com")
     
    # find the element that's name attribute is q (the google search box)
        inputElement = driver.find_element_by_name("q")
     
    # type in the search
        inputElement.send_keys("Cheese!")
     
    # submit the form (although google automatically searches now without submitting)
        inputElement.submit()
     
    # the page is ajaxy so the title is originally this:
        print driver.title
            # we have to wait for the page to refresh, the last thing that seems to be updated is the title
        try:
           # element = WebDriverWait(driver, 10).until(EC.title_contains("cheese!"))
            #element = WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "gs_htif0")))
            #print element.text
            # or
            wait = WebDriverWait(driver, 10)
            #element = wait.until(EC.presence_of_element_located((By.ID, "gs_htif0")))
            element = wait.until(EC.element_to_be_clickable((By.ID,'gb_8')))
            r = element.text
            print "r", r
            
            element1 = driver.find_element_by_xpath("//*[@id='gb_8']/span[2]")
            element1.click()
            title = driver.title
            
            print title
        except:
            pass
#
                 
            #
             
            # Here you only get Cheese, first line
            #result = driver.find_element_by_tag_name("em").text
             
            # By finding by xpath, u get the whole link text
            #result = driver.find_element_by_xpath("//*[@id='rso']/li[1]/div/h3/a").text
            #print "link text", result
      
        # You should see "cheese! - Google Search"
           # print driver.title
#         except :
#             print "Error encountered", sys.exc_info()[0]
     
   # @unittest.skip("demonstrating skipping")
#     def test4(self):
#         driver = self.driver       
#         driver.get("http://www.google.com")
#         driver.find_element_by_id("gbqfq").clear()
#         driver.find_element_by_id("gbqfq").send_keys("selenium IDE")
#         driver.find_element_by_id("gbqfb").click()
#         try:
#             wait = WebDriverWait(driver, 10)
#             element = wait.until(EC.title_contains("selenium ide"))
#             print element
# #            # element = WebDriverWait(driver, 10).until(EC.title_contains("cheese!"))
#             self.assertEquals("selenium ide - Google Search", driver.title)
#        
#             #print data.text
#            # driver.save_screenshot("/Users/ramya/ramya_qa_data/Eclipse_python/PythonPrj1/screen1.png")
#             #driver.get_screenshot_as_file("/Users/ramya/ramya_qa_data/Eclipse_python/PythonPrj1/screen2.png")
#            
#         except Exception,err:
#             print "traceback.format_exc()", traceback.format_exc()
#             print "sys.exc_info()[0]", sys.exc_info()[0]
#             pprint.pprint("------pprint--------" + str(err))
   
   
    def test5(self):
        driver = self.driver
        driver.get("http://www.w3schools.com/html/html_forms.asp")
            #driver.find_element_by_name("sex").click()
            #driver.find_element_by_xpath("//input[@type='radio' and @value='female']").click()
            #driver.find_element_by_xpath("//input[@type='radio'][position() = 1]").click()
           # driver.find_element_by_xpath("//input[@type='radio'][last()-1]").click()
        d1 = driver.find_element_by_xpath("//*[@id='main']/h2[1]")
       # print d1.get_attribute("value")
        print "d1.text", d1.text
        data = driver.find_elements_by_xpath("//form[@action='']")
        p(data)
        for i in data:
            p(i.text)
            print "i.get_attributei", i.get_attribute("value")
       
        #print data
        #time.sleep(5)
       
#         labels = driver.find_elements_by_tag_name("input")
#         for i in labels:
#             print "i", i.get_attribute("id")
        
        # Finding all the input elements to the every label on a page:
        labels = driver.find_elements_by_tag_name('label')
        inputs = driver.execute_script(
        "var labels = arguments[0], inputs = [ ];" +
        "for (var i = 0; i < labels.length; i++) {" +
            "inputs.push(document.getElementById(labels[i].getAttribute('for')));" +
        " } " +
        "return inputs;", labels)
        p(inputs)
        for j in inputs:
            print j.get_attribute("id")
            j.type()
            
   
    def tearDown(self):
        self.driver.quit()      