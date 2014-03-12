from selenium import selenium
import unittest, time, re

class storeex(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.deanza.edu/")
        self.selenium.start()
    
    def test_storeex(self):
        sel = self.selenium
       # name = "ramya nagendra"
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
        print email.upper()
        print(sel.get_eval("'" + email + "'. toUpperCase()"))
        #sel.click("link=exact:http://www.deanza.fhda.edu")
        #sel.wait_for_page_to_load("30000")
        #/html/body/div[@id='container']/table/tbody/tr/td[2]/form[@id='search']/span[2]/a[3]
        #print(sel.get_eval("'" + name + "'. toUpperCase()"))
       # number = "35"
      #  print(sel.get_eval("'" + number + "' * 10"))
     
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
