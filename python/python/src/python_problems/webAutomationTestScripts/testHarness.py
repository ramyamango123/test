import unittest
import sys
import os
import getopt
import HTMLTestRunner
import datetime
import re

import testSuiteBase

WEBPATH = '\\\\hq-fs01\\dept\\Dev\\QA\\Web\\'
'''
Test harness for python unittests
Usage:
testHarness.py [-h webhost] testScriptOrDirectory1 testScriptOrDirectory2 testScriptOrDirectory3  ...
'''

def suite(testList, webhost=''):
    '''
    Create a testSuite by either simply adding a pyunit test case, or running a recursive find on the given
    directory for all python files (which are assumed to be test cases).
    '''
    if webhost != '':
        testSuiteBase.webHost = webhost

    testCaseList = []
    for testPath in testList:
        #Walk the directory(if it is one).  If not, this will for loop will not execute
        #This loop is looking for python files so it can create a list of them to run as tests
        for dir, subdirs, files in os.walk(testPath):
            for file in files:
                if file.endswith(".py") and file != "__init__.py":
                    testList.append(os.path.join(dir,file))

        if testPath.endswith(".py"):
            testPath = testPath[:-3]
        else:
            #The file/folder that we're looking at isn't a python file, skip to the next item in the list
            continue


        #Create an object path to import
        objectPath = testPath.replace("\\",".").replace("/",".")

        #Create an object name, this will be used to determine the test class name
        objectName = objectPath.split(".")[len(objectPath.split("."))-1]

        #A split version of the objectPath, used to
        objectSplitPath = objectPath.split(".")

        #Import the module
        objectModule = __import__(objectPath)
        objectSplitPath.remove(objectSplitPath[0])

        #Travel down the object path until we get to the unittest class
        for objectClass in objectSplitPath:
            objectModule = getattr(objectModule, objectClass)

        className = objectName[0].upper()+objectName[1:]
        objectModule = getattr(objectModule, className)
        testCaseList.append(unittest.makeSuite(objectModule))

    return unittest.TestSuite(testCaseList)


if __name__ == "__main__":
    if len(sys.argv) == 0:
        sys.exit()
    opts, args = getopt.getopt(sys.argv[1:],"h:t", ["host=", "text"])
    web_host = ''
    useCmdOut = False
    
    for o, a in opts:
        if o in ('-h', '--host'):
            web_host = a
        elif o in ('-t', '--text'):
            useCmdOut = True
          
    #if -t option used, then run with TextTestRunner (inline).  Otherwise, use htmlTestRunner (file output)
    if useCmdOut == True:
        result = unittest.TextTestRunner(verbosity=2).run(suite(sys.argv[1:], webhost=web_host))
    else:
        dir = args[-1:][0]
        #if the path is a python file, strip the file name and retain the containing directory.  Otherwise, just use the file directory supplied.
        if dir.endswith('.py'):
            remover = re.compile(r'\\')
            splitDir = remover.split(dir)
            outdir = splitDir[0]
            for i in range(1, len(splitDir)-1):
                outdir = outdir + "\\" + splitDir[i]
        else:
            outdir = dir
        #find today's dat0e
        today = datetime.date.today()
        todayStr = str(today.year) + "_" + str(today.month) + "_" + str(today.day)
        #if the file directory already exists, do nothing.  Else, add the directory.
        if not os.path.exists(os.path.join(WEBPATH,outdir)):
            os.makedirs(os.path.join(WEBPATH,outdir))
        #run the test plan through htmlTestRunner, placing the results file in QA\Web\<directory>,  
        #File names have today's date and the full cmd line name of the test plan (if a specific plan run).
        fp = file(os.path.join(WEBPATH,outdir,
                  todayStr + "_" +dir.replace('\\','_').split('.')[0]  + '.html'), 'wb')
        runner = HTMLTestRunner.HTMLTestRunner(verbosity=2, stream=fp, 
                 title='Web Services Test Scripts')
        result = runner.run(suite(sys.argv[1:], webhost=web_host))
        fp.close()
    sys.exit(not result.wasSuccessful())
