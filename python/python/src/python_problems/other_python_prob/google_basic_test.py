from selenium import selenium
import unittest, time, re
#*iexploreproxy
import print_module
from print_module import pprint2
from print_module import Fruit



class Google(unittest.TestCase):
    selenium = None
    
      
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.thefreedictionary.com")
        #self.selenium2 = selenium("localhost", 4444, "*iexploreproxy", "http://www.thefreedictionary.com")
       # self.selenium.start()
            
       
    '''def test1_common(self, sel):
        return()
        #sel = self.selenium
        sel.open("/")
        time.sleep(3)
        sel.click("f1_tfd_start")
        time.sleep(3)
        sel.type("f1Word", "respect")
        sel.click("Search")
        time.sleep(3)
        result = sel.get_xpath_count("//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        #result = sel.get_xpath_count("//table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        print result
        time.sleep(60)'''
    
    '''def test1(self, sel):
        test1_common(self.selenium)
        test1_common(self.selenium2)'''
        
        
    def test2(self):
        sel = self.selenium
        sel.open("/")
        sel.click("f1_tfd_start")
        sel.type("f1Word", "respect")
        sel.click("Search")
        time.sleep(6)
        result = sel.get_xpath_count("//table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        print(result)
    
    def test3(self):
        sel = self.selenium
        sel.open("/")
        sel.click("f1_tfd_start")
        sel.type("f1Word", "respect")
        sel.click("Search")
    
    def test4(self):
        print_module.pprint("test")
        pprint2("hello")
        f1 = Fruit("apple")
        f2 = Fruit("Orange")
        f1.add()
        f2.add()
  
                         
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

# Simpler way
def makeGoogleTestSuite():
    suite = unittest.TestSuite()
    suite.addTest(Google("test3"))
    suite.addTest(Google("test4"))
    return suite

def suite():
    return unittest.makeSuite(Google)
        
        
if __name__ == "__main__":
    
    unittest.main()


        