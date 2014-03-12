from selenium import selenium
import unittest, time, re


class sorting_ex(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.frys.com")
        self.selenium.start()
        
             
    def test1(self): 
        sel = self.selenium
        sel.open("http://www.frys.com")
        sel.wait_for_page_to_load("30000")
        sel.type("query_string", "laptop")
        sel.click("submit")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.select("sortOrder", "label=Price Low to High")
        time.sleep(3)
        item1 = "//table[4]/tbody/tr/td[3]/table[3]/tbody/tr[9]/td[3]/table/tbody/tr[1]/td/span"
        
        item2 =  "//table[4]/tbody/tr/td[3]/table[3]/tbody/tr[15]/td[3]/table/tbody/tr[1]/td/span"
        
        item3  = "//table[4]/tbody/tr/td[3]/table[3]/tbody/tr[21]/td[3]/table/tbody/tr[1]/td/span"
        
        l1 = []
        
        t1 = sel.get_text(item1)
        l1.append(self.rebateCheck(t1))
        t2 = self.rebateCheck(sel.get_text(item2))
        l1.append(t2)
        t3 = self.rebateCheck(sel.get_text(item3))
        l1.append(t3)
        print "Website sorted order", l1
        l2 = sorted(l1)
        print "python library sorted order", l2
        try: self.failIf(l1 != l2)
        except AssertionError, e: self.verificationErrors.append(str(e))
            
        
        

    def rebateCheck(self, t1):
        
        if "after rebate" in t1.lower():
            ts = t1.split(" ")
            r1 = ts[0]
            a1 = float(r1[1:])
            
        else:
            print "price for it is:", t1
            a1 = float(t1[1:])
        
        return a1
  
    #try: self.failIf(Result == 0)
    #except AssertionError, e: self.verificationErrors.append(str(e))
        
        
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()

