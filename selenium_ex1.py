from selenium import selenium
import unittest, time, re


class barnesNobleEx(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", "http://www.deanza.edu/")
        self.selenium.start()
        
       
                
    def test1(self): 
        sel = self.selenium
        sel.open("http://www.deanza.edu/")
        time.sleep(3)
        sel.click("link=Faculty Websites")
        time.sleep(3)
        sel.click("link=M")
        time.sleep(3)
        sel.click("link=MADIGAN, JULIE")
        time.sleep(3)
        try: self.failUnless(sel.is_text_present("madiganjulie@fhda.edu"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        email = sel.get_text("//body/table[4]/tbody/tr[2]/td[2]/table/tbody/tr/td/table/tbody/tr/td/table/tbody/tr/td[1]/p[8]/a")
        print email
        print email.upper()
        print(sel.get_eval("'" + email + "'. toUpperCase()"))
        
        
                   
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()
