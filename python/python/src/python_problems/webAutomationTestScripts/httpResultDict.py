class HttpResultDict(dict):

    def __init__(self, optionalDict={}):
        dict.__init__(self, optionalDict)
        self.httpStatusCode = ""

    def setHttpStatus(self, statusCode):
        self.httpStatusCode = statusCode

    def httpStatus(self):
        return self.httpStatusCode

