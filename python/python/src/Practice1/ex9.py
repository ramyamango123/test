import re
import time
from time import strftime
from datetime import datetime, timedelta
#requested through input_type ("Passed" or "Failed")
def get_tests_count_and_execution_time(input_type):
    fp = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\filewrite.txt", 'r')
    count = 0
    for i in fp:
        i = i.rstrip()
        if re.search("^\*\*\*" + input_type + "\!\*\*\*$", i):
            count += 1
            print i
        elif re.search("^Execution started.*", i):
            pattern1 = i
            #print "pattern1", pattern1
        elif re.search("^Execution completed", i):
           pattern2 = i
           #print "pattern2", pattern2
    et = get_time(pattern1, pattern2)
    return count, et
    
 
 
def get_time(pattern1, pattern2):
    print "pattern1", pattern1
    print "pattern2", pattern2
    obj1= re.search("Execution started at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern1)
    
    print "obj1.group(1)", obj1.group(2)
    time_string1 = obj1.group(1) + " " + obj1.group(2)
    print "time_string1", time_string1
    t1 = time.strptime(time_string1, "%m/%d/%Y %H:%M:%S %p")
    t2 = time.mktime(t1)
    print "t2", t2
     
    obj2 = re.search("Execution completed at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern2)
    #obj2 = re.search("Execution completed at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern2)
    print "obj2.group(1)", obj2.group(2)
    time_string2 = obj2.group(1) + " " + obj2.group(2)
    print "time_string2", time_string2
    t3 = time.strptime(time_string2, "%m/%d/%Y %H:%M:%S %p")
    t4 = time.mktime(t3)
    print "t4", t4
    
    sec = timedelta(seconds = (t4 - t2))
    
    print "sec", sec

    d = datetime(1,1,1) + sec
    execution_time = "Total time for test execution %d days, %d hours, %d mins, %d secs" % (d.day-1, d.hour, d.minute, d.second)
    
    print "d", d
    return execution_time
 
 
 
 
passed_tests = get_tests_count_and_execution_time("Passed")
failed_tests = get_tests_count_and_execution_time("Failed")
print "passed_tests", passed_tests
print "failed_tests", failed_tests
        
    

