from selenium import selenium
import unittest, time, re
#from find_all_ip_text_file import IPAddress

class barnesNobleEx(unittest.TestCase):
    selenium = None
    
   
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4442, "*chrome", "http://books.barnesandnoble.com")
        self.selenium.start()
        
       
                
    def test1(self): 
        sel = self.selenium
        sel.open("http://books.barnesandnoble.com")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("search-input", "javascript")
        
        #ip = IPAddress("foo.txt")
     #   l1 = ip.find_ip_addresses()
        sel.click("quick-search-button")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.click("link=Price")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        pr1 = "//div[@id='bs-center-col']/div[3]/div[1]/div[2]/div/div/div/ul/li/div/div[2]/span[2]"
        price1 = sel.get_text(pr1)
        print price1
        #p1 = re.search("$(\d+).(/d+)", price1).group(1,2)
        #pat = re.search("$(\d+).(/d+)", price1)
        #print pat.group(1) + pat.group(2)
        a = float(price1[1:])
        print a
        pr2 = "//div[@id='bs-center-col']/div[3]/div[3]/div[2]/div/div/div/ul/li/div/div[2]/span[2]"
        price2 = sel.get_text(pr2)  
        print price2
        b = float(price2[1:])
        print b
        pr3 = "//div[@id='bs-center-col']/div[3]/div[5]/div[2]/div/div/div/ul/li/div/div[3]/span[2]"
        price3 = sel.get_text(pr3)
        print price3
        c = float(price3[1:])
        print c
                        
        if a < b and b < c:
            print "Prices are in sorted order"
        else:
            print "prices are not in sorted order"
        
    def get_money(self, pr1):
        sel = self.selenium
        price1 = sel.get_text(pr1)
        print "price 1 is " + price1
        #p1 = re.search("\$(\d+).(/d+)", price1).group(1,2)
        pat = re.search("\$(\d+).(\d+)", price1)
        var = pat.group(1) + "." + pat.group(2)  
        x = float(var)   
        print "price1 in float is x"
        return x
        
    def test2(self): 
        sel = self.selenium
        sel.open("http://books.barnesandnoble.com")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.type("search-input", "javascript")
        sel.click("quick-search-button")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        sel.click("link=Price")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        
        a = self.get_money("//div[@id='bs-center-col']/div[3]/div[1]/div[2]/div/div/div/ul/li/div/div[2]/span[2]")
        b = self.get_money("//div[@id='bs-center-col']/div[3]/div[3]/div[2]/div/div/div/ul/li/div/div[2]/span[2]")
        c = self.get_money("//div[@id='bs-center-col']/div[3]/div[5]/div[2]/div/div/div/ul/li/div/div[2]/span[2]")
                       
        if a < b and b < c:
            print "Prices are in sorted order"
        else:
            print "prices are not in sorted order"
            


    def validate_email(self, s):
        Pattern1 = "^([\w\-]+" "(\.[\w\-]+)?)" "@" "([\w\-]+)" "(((\.([a-z]{2}|com|edu|gov)){1,2}))$"
        T = re.search(Pattern1, s)
        print T
        if T:
            return 1
        else:
            return 0

           
    def test3(self):
        sel = self.selenium
        sel.open("/phase1/contact.html")
        sel.wait_for_page_to_load("30000")
        time.sleep(3)
        xpath = "//table/tbody/tr[3]/td/table/tbody/tr[1]/td[1]/table/tbody/tr[3]/td[2]/table/tbody/tr/td/table/tbody/tr[5]/td[2]/a[1]"
                        
        Email = sel.get_text(xpath)  
        print Email
        Result = self.validate_email(Email)
        print Result
        try: self.failIf(Result == 0)
        except AssertionError, e: self.verificationErrors.append(str(e))
           
                
               
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    
    unittest.main()


        
        