#! /usr/bin/env python
# Author: Manuel Gallas (Manuel.Gallas@cern.ch) February 2003

import os
import sys 

class RunPyUnitRegressionError(Exception) :
      def __init__(self,value) :
          self.value=value
      def __str__(self) :
          return 'self.value'
try:
	print "Run the PyUnit regression tests"
	rc=os.system('python2.2 PyUnitRegression.py')
        if rc!=0:
           raise RunPyUnitRegressionError('python2.2 PyUnitRegression.py')
        if rc==0:
           print '[OVAL] PyUnitRegressionTestsCode=0'
           print "End of the PyUnit regresion tests"
except os.error, value:
       print 'module os error:',value[0],value[1]
except RunPyUnitRegressionError, mya:
       print 'Non-zero error code from ', mya
       print '[OVAL] PyUnitRegressionTestsCode=1'
except:
      print "Unexpected error", sys.exec_info()[0]
      raise 

