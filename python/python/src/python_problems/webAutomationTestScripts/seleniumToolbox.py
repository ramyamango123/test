from selenium import selenium


class SeleniumToolbox(selenium):
    '''

    '''
    def __init__(self, webHost, rcServer, rcPort, browser):
        '''
        @type webHost: string
        @param webHost: the web host of the selenium server
        '''
        self.webHost = webHost
        self.rcServer = rcServer
        self.rcPort = rcPort
        self.browser = browser

        self.verificationErrors = []
        selenium.__init__(self, rcServer, rcPort, browser, webHost)
        #self.selenium = selenium(rcServer, rcPort, browser, webHost)
        #self.start()
        #self.selenium.window_maximize()





