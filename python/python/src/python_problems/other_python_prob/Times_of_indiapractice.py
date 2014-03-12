from selenium import selenium
import unittest, time, re

class Untitled(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4333, "*chrome", "http://timesofindia.indiatimes.com/")
        self.selenium.start()
        self.selenium.window_maximize()
    
    def test_untitled(self):
        sel = self.selenium
        sel.open("http://timesofindia.indiatimes.com/")
        #sel.type("id=mathuserans2", "4")
       # sel.click("name=ob36194")
        res = sel.get_text("//*[@id='mathq2']")
        r2 = str(res)
        split_num = r2.split()
        final_num = int (split_num[0]) + int(split_num[2])
        print "final_num", final_num
        
        sel.click("name=PRadio")
        sel.type("id=mathuserans2", final_num)
        sel.click("css=div.homesprite.vot")
        sel.wait_for_page_to_load("30000")
#         try: self.failUnless(sel.is_text_present("You have successfully cast your vote."))
#         except AssertionError, e: self.verificationErrors.append(str(e))
       
      
        #time.sleep(3000)
        #self.assertTrue(sel.is_text_present("You have successfully cast your vote."), "You have already cast your vote. message not present")
        #self.assertEq(sel.is_text_present("You have successfully cast your vote."), "You have already cast your vote. message not present")
        #self.assertEqual("You have successfully cast your vote.", sel.get_text("//*[@id='polldiv']/table/tbody/tr/td/table[1]/tbody/tr/td/font/b"))
        
        try: self.failUnless(sel.is_element_present("//div[27]/div/div[2]/div[2]/ul/li[3]"))
        except AssertionError, e: self.verificationErrors.append(str(e))
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
    
   