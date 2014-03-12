#!/usr/bin/env python
#
#   docGenerator.py
#
#
#   Created by Luca Pellicoro
#   Copyright (c) 2009 Gazillion Entertainment. All rights reserved.
#
'''
Script to generate excel document out test suites

Usage: $python docGenerator.py <testSuiteFile | testSuitFolder> <excelfile>
testSuiteFile: a test suite file (eg login.py) 
-or-
testSuitFolder: a folder containing test suite files (eg .../testSuites/)
excelfile: file name where to output the results
'''

import types
import os
import sys
from win32com.client import Dispatch

# Offsets used to place the start of the data in excel 
# (0,0) to disable, will write data starting at cell A1
VERTICAL_OFFSET = 0
HORIZONTAL_OFFSET = 0

def writeToExcel(testSuites, location):
    '''Generate (or overwrite) a excel file of test cases.
    
    Sample Format: 
    SuiteName
    TestCaseName    TestCase Description
    TestCaseName    TestCase Description
    TestCaseName    TestCase Description
    ...
    SuiteName
    TestCaseName    TestCase Description
    TestCaseName    TestCase Description
    TestCaseName    TestCase Description
    ...
    
    @param  testSuites  List of test suites
    @param  location    Path to save the excel file to
    '''
    xlApp = Dispatch("Excel.Application")
    xlApp.Visible=False
  
    if not os.path.exists(location):
        # Create the excel file if it doesn't exist
        xlApp.Visible=False
        xlWb = xlApp.Workbooks.Add()
        xlWb.SaveAs(location)
        xlWb.Close(SaveChanges=0)
        xlApp.Quit()
  
    xlWb = xlApp.Workbooks.Open(location)
    line = VERTICAL_OFFSET + 1
    column = HORIZONTAL_OFFSET + 1
  
    for testSuite in testSuites:
        xlWb.ActiveSheet.Cells(line,column).Value = '---%s---' % testSuite
        line += 1
        for testCase in testSuites[testSuite]:
            xlWb.ActiveSheet.Cells(line,column).Value = testCase
            xlWb.ActiveSheet.Cells(line,column+1).Value = testSuites[testSuite][testCase]
            line += 1

    xlWb.Save()
    xlWb.Close(SaveChanges=0)
    xlApp.Quit()
    
    
def readComments(moduleName):
    '''Read the function name and comments 
    
    Module is expected to:
    - start with lower case letter
    - contain class with same name but with upper case first letter
    - have test methods starting with 'test_'
    
    # myModule.py
    class MyModule:
        def test_OneTest():
            function comment enclosed by triple single quotes
            pass
        def test_TwoTest():
            function comment enclosed by triple single quotes
            pass
    
    @param  moduleName  
    @return className   Name of the test class (test suite)
    @return testCases   Dict of testcases (keys) with description (values)
    '''
    testCases = {}
    if moduleName.endswith(".py"):
        moduleName = moduleName[:-3]

    # Import the module to have access to it's doc
    objectPath = moduleName.replace("\\",".")\
                           .replace("/",".")\
                           .replace('..','.')\
                           .lstrip('.')
    objectName = objectPath.split(".")[len(objectPath.split("."))-1]
    
    objectModule = __import__(objectPath)
    objectClass = getattr(objectModule, objectName)
    
    className = objectName[0].upper()+objectName[1:]
    objectClass = getattr(objectClass, className)
    
    # Retreive all functions in the module
    functions = [objectClass.__dict__.get(i) for i in dir(objectClass)\
                if isinstance(objectClass.__dict__.get(i), types.FunctionType)]
    
    for func in functions:
        functionName = func.__name__
        if functionName.startswith('test_'):
            if func.__doc__:
                description = func.__doc__.strip()
            else:
                description = ''
            testCases[functionName.replace('test_','')] = description
            
    return className , testCases
            
    
if __name__ == "__main__":
    if len(sys.argv) < 3:
        print 'Usage: $python docGenerator.py <testSuiteFile|testSuitFolder> <excelfile>'
        sys.exit(1)
    
    # Get the name of the test scripts
    testScripts = []   
    if os.path.exists(sys.argv[1]):
        if os.path.isdir(sys.argv[1]):
            for dirc, subdirs, files in os.walk(sys.argv[1]):
                for file in files:
                    if file.endswith(".py") and file != "__init__.py":
                        testScripts.append(os.path.join(dirc,file))
                        
        elif sys.argv[1].endswith('.py') and sys.argv[1] != "__init__.py" :
            testScripts.append(sys.argv[1])            
    else:
        print 'Could not locate %s' % sys.argv[1]
        sys.exit(1)
    
    # Retrieve all the comments for each file
    testSuites = {}
    for script in testScripts:
        className, testCases = readComments(script)
        testSuites[className] = testCases
    
    # Generate the excel doc
    writeToExcel(testSuites, sys.argv[2])


    
    