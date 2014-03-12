from selenium import selenium
import unittest, time, re


def pprint (result):
    import pprint
    pprint.PrettyPrinter().pprint(result)

class Google(unittest.TestCase):
    selenium = None
    
      
    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.blazonry.com/")
        self.selenium.start()
        
       
    def test1(self):
        sel = self.selenium
        sel.open("http://www.google.com") # "/javascript/windows.php")
        time.sleep(3)
        title = sel.get_all_window_ids()
        pprint(title)
        pprint(sel.get_all_window_names())
        print(sel.get_all_window_names())
        

        #pprint "titles", titles

        sel.click("//p[6]/a/b")
        time.sleep(3)
        sel.wait_for_pop_up("Window1", "30000")
        sel.select_window("null")
        try: self.assertEqual("JavaScript: Popup Windows -- Creating and Working With Popups - web.blazonry", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        time.sleep(3)
        titles = sel.get_all_window_names()
        pprint(sel.get_all_window_names())	
                         
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

# Simpler way
def makeGoogleTestSuite():
    suite = unittest.TestSuite()
    suite.addTest(Google("test1"))
    return suite

def suite():
    return unittest.makeSuite(Google)
        
        
if __name__ == "__main__":
    
    unittest.main()


        