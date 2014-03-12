from selenium import selenium
import unittest, time, re



class TimesOfIndiaaddnums(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium( \
            "localhost", 4333, "*firefox /Applications/Firefox.app/Contents/MacOS/firefox",
                    "http://timesofindia.indiatimes.com/")
        #"*firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", \
        #self.selenium = selenium("localhost", 4444, "*chrome", "http://timesofindia.indiatimes.com/")
        self.selenium.start()
        

        #self.selenium.deleteAllVisibleCookies();
        #self.selenium.refresh();
        
       
                
    def test1(self): 
        sel = self.selenium
        sel.open("http://timesofindia.indiatimes.com/")
        sel.wait_for_page_to_load("30000")
        
        #bodytext = sel.get_body_text()
        #htmlsource = sel.get_html_source()
       # print bodytext
        #print "htmlsource", htmlsource 
        import re;
        
        
        
        sel.click("//span[@id='mathq2']")
        quiz_numbers_xpath = sel.get_text("//span[@id='mathq2']")
        real_num = str(quiz_numbers_xpath);
        print real_num
        Pattern1 = "(\d+)?\s*\+\s*(\d+)\s*\=";
        T = re.search(Pattern1, real_num)
        result = int(T.group(1)) + int(T.group(2))
        print "result of a + b:", result
        sel.type("id=mathuserans2", result)
        #time.sleep(10000)
        
             
    def tearDown(self):
        time.sleep(5)
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()


        
        