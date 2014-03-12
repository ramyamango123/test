import sys
import datetime
from selenium import selenium
import unittest, time, re

import HTMLTestRunner

class FO_basic_sample_test(unittest.TestCase):

    def setUp(self):
        self.verificationErrors = []
        self.selenium = selenium("localhost", 4444, "*firefox", "http://www.playfortuneonline.com/")
        self.selenium.start()
    
    def test_basic_login(self):
        sel = self.selenium
        myUrl1 = "http://community.playfortuneonline.com"
        myUrl2 = sel.get_expression("http://www.playfortuneonline.com/playnow")
        print myUrl2
        sel.open("https://www.playfortuneonline.com")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        time.sleep(5)
        try: self.failUnless(sel.is_text_present("Play Free Now"))
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open(myUrl1)
        try: self.assertEqual("Fortune Online Community Forums", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.click("link=Log Out")
        time.sleep(5)
        sel.click("//div[@id='entry']/p[2]/a/span")
        time.sleep(5)
        sel.type("Username_Field", "ramya.arus@hotmail.com")
        sel.type("Password_Field", "mtsassay")
        sel.click("submit")
        time.sleep(5)
        sel.open(myUrl2)
        try: self.assertEqual(u'Playnow \xab Fortune Online', sel.get_title())
        #u'Fortune Online', sel.get_title()
        except AssertionError, e: self.verificationErrors.append(str(e))
        sel.open("http://www.playfortuneonline.com/home")
        time.sleep(5)
        sel.click("link=Logout")
        time.sleep(5)
        try: self.assertEqual("Fortune Online", sel.get_title())
        except AssertionError, e: self.verificationErrors.append(str(e))
        
        
    def tearDown(self):
        self.selenium.stop()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    # Here we can opt either to run tests using -t or without.  
    text_output = 0
for i in sys.argv:
    if i == "-t":
        text_output = 1
        # -t must be removed from list as it may be existing from previous runs
        sys.argv.remove("-t")
#If we run the script with -t, it will print the result on cmd screen by just doing unittest.main() like before        
if text_output == 1:
    unittest.main()
# If we run the script without -t, HTML test runner report will be generated with the current date and time
else:
    suite = unittest.TestSuite()
    suite.addTests([
    unittest.defaultTestLoader.loadTestsFromTestCase(FO_basic_sample_test)])
    today = datetime.datetime.today()
    todayStr = str(today.year) + "_" + str(today.month) + "_" + str(today.day) + "_" + str(today.hour) + "_" + str(today.minute) + "_" + str(today.second)
    fp = file('my_report' + todayStr + '.html', 'wb')
    runner = HTMLTestRunner.HTMLTestRunner(
                stream=fp, verbosity=10,
                title='My unit test',
                description='This demonstrates the report output by HTMLTestRunner.'
                )
    # Use an external stylesheet.
    # See the Template_mixin class for more customizable options
    runner.STYLESHEET_TMPL = '<link rel="stylesheet" href="my_stylesheet.css" type="text/css">'

    # run the test
    runner.run(suite)
    #unittest.main()
