from selenium import selenium
import unittest, time, re


class attributevalue(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", "http://www.quikr.com/")
        #"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"
        self.selenium.start()
        #self.window_maximize()
        #sel.setSpeed("2000")
       
                
    def test1(self): 
        sel = self.selenium
        sel.window_maximize()
        sel.set_speed("2000")
        sel.open("http://www.quikr.com/")
        sel.wait_for_page_to_load("30000")
        p1 = sel.get_value("query")
        print p1
        sel.type("id=query", "selenium")
        p2 = sel.get_value("query")
        print "p2", p2
        sel.click("css=input.search_button_sprite")
        
    
           
                
               
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()


        
        