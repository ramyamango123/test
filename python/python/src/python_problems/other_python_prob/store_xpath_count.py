from selenium import selenium
import unittest, time, re

class store_xpath_count(unittest.TestCase):
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.thefreedictionary.com/")
        self.selenium.start()
    
    '''def test_store_xpath_count(self):
        sel = self.selenium
        sel.open("/")
        time.sleep(3)
        sel.click("f1_tfd_start")
        time.sleep(3)
        sel.type("f1Word", "respect")
        sel.click("Search")
        time.sleep(6)
        result = sel.get_xpath_count("//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        print(result)
        #list1 = []
        for i in range(1, int(result)):
            cmd = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[" + str(i) + "]"
            text = str(sel.get_text(cmd))
            print text
           # list1.append(text)
            #list1.append('')
        #print "list1", list1
        #for j in list1:
          #  p = j.split()
           # print p'''
        
    def test2(self):
        sel = self.selenium
        sel.open("/")
        time.sleep(3)
        sel.click("f1_tfd_start")
        time.sleep(3)
        sel.type("f1Word", "generation")
        sel.click("Search")
        time.sleep(6)
        result = sel.get_xpath_count("//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        print(result)
        #list1 = []
        for i in range(1, int(result)):
            r1 = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[i]"
            for j in range(1,4):
                cmd = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[" + str(i) + "]" + "/td[" + str(j) + "]"
                print cmd
                text = str(sel.get_text(cmd))
                print text
                
          
    '''def test3(self):
        sel = self.selenium
        sel.open("/")
        time.sleep(3)
        sel.click("f1_tfd_start")
        time.sleep(3)
        sel.type("f1Word", "fuck")
        sel.click("Search")
        time.sleep(6)
        pattern = re.search("(//html/body/table\[4\]/tbody/tr/td/div\[[1-9]?\]/p/table)", p)
        result = sel.get_xpath_count("//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr")
        print(result)
        #list1 = []
        for i in range(1, int(result)):
            r1 = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[i]"
            for j in range(1,4):
                cmd = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[" + str(i) + "]" + "/td[" + str(j) + "]"
                #print cmd
                text = str(sel.get_text(cmd))
                print text'''
          
    
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
