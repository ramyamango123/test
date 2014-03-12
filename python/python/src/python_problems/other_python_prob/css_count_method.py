from selenium import selenium
import unittest, time
from datetime import datetime

class Csscount(unittest.TestCase):
    selenium = None
    
    def count_css_matches(self, css_locator):
        java_script_code = '''
        var cssMatches = eval_css("%s", window.document);
        cssMatches.length;''' % css_locator
        return int(self.selenium.get_eval(java_script_code))


    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*chrome", "http://www.guestlistnation.com/")
        self.selenium.start()

    def test1(self):
        sel = self.selenium
        sel.window_maximize()
        sel.open("/Events.aspx?Location=SAN FRANCISCO")
        sel.click("css=[id='EventDates_ctl00_NestedEvents_ctl01_btnDetails']")
        sel.wait_for_page_to_load("30000")
        print self.count_css_matches("[id='listGuests'] > option") #
    #prints number of options in dropdown with 'id=listGuests'
        print self.count_css_matches("*") #prints all on page
    
                          
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

    
if __name__ == "__main__":
    unittest.main()
