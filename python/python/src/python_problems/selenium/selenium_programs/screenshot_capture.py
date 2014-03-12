from selenium import selenium
import unittest, time, re


class screenshot(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://book.theautomatedtester.co.uk/")
        self.selenium.start()
        
       
        
        
    def test1(self): 
        sel = self.selenium
        sel.open("/chapter8")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        #sel.capture_screenshot("003.png")
        # Here screen-shot is captured and result is passed back to the string
        #screenshot = sel.capture_screenshot_to_string()
        #print screenshot
        #sel.capture_entire_page_screenshot("C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\selenium\selenium_programs\screenshots\img1.png", "")
        sel.capture_entire_page_screenshot("C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\selenium\selenium_programs\screenshots\img3.png", "CCFFDD")

        
        
        
                     
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()


        
        