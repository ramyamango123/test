#
#   testSuiteTemplate.py
#
#
#   Created by Luca Pellicoro
#   Copyright (c) 2009 Gazillion Entertainment. All rights reserved.
'''
Hold TestSuiteTemplate class
'''

import unittest
import glbRestToolbox
import seleniumToolbox

#Web host constant
webHost = 'websvc01.qa.dwa.sjc.adm.gazint'
sqlDb = 'webdb01a.qa.dwa.sjc.adm.gazint'
sqlUsername = 'qa_full_user'
sqlPassword = 'GUQEfybC'
rcServer = 'localhost'
rcPort = '4444'
browser = '*firefox'
selHost = 'http://gazillion.com'




class TestSuiteBase(unittest.TestCase):
    '''Super class to be inherited by all test suite classes '''
    def __init__(self, *args, **kwargs):
        '''Constructor'''
        unittest.TestCase.__init__(self, *args, **kwargs)
        self.webHost = webHost
        self.sqlDb = sqlDb
        self.sqlUsername = sqlUsername
        self.sqlPassword = sqlPassword
        self.rcServer = rcServer
        self.rcPort = rcPort
        self.browser = browser
        self.selHost = selHost


    def getGlbToolbox(self):
        return glbRestToolbox.GlbRestToolbox(self.webHost, self.sqlDb, self.sqlUsername, self.sqlPassword)

    def getSeleniumToolbox(self):
        return seleniumToolbox.SeleniumToolbox(self.selHost, self.rcServer, self.rcPort, self.browser)

    def tearDown(self):
        pass


if __name__ == '__main__':
    # Sample usage
    class ConcreteTestSuite(TestSuiteTemplate):
        def test_sampleTestOne(self):
            self.assertTrue(True)

        def test_sampleTestTwo(self):
            print self.toolBox.login
            print self.toolBox.webHost

    unittest.main()
