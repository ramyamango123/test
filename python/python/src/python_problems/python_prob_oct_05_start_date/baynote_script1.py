import re
import time
from time import strftime
from datetime import datetime, timedelta

file_out = None

def count_tests (input_type):
    global file_out
    count = 0
    file_input = open("Baynote_input.txt",'r')
    for eachline in file_input:
        eachline = eachline.rstrip()
        if re.search("^\*\*\*" + input_type + "\!\*\*\*$", eachline):
            count += 1
        elif re.search("^Execution started ", eachline):
            pattern1 = eachline
        elif re.search("^Execution completed ", eachline):
            pattern2 = eachline
    et = get_time(pattern1, pattern2)
    file_input.close()
    return count, et


def parse_test_output (input_type):
    global file_out
    count, et = count_tests(input_type)
    print >> file_out, "\nTotal number of " + input_type + " tasks is:", count

    file_input = open("Baynote_input.txt",'r')
    for eachline in file_input:
        eachline = eachline.rstrip()
        if re.search("^\*\*\*" + input_type + "\!\*\*\*$", eachline):
            print >> file_out, "=======================================\n"
            print >> file_out, case
            print >> file_out, reg_url, "\n"
        elif re.search("^\[Case ", eachline):
            case = eachline
        elif re.search("^\[REQ\-URL\]\: ", eachline):
            reg_url = eachline
        elif input_type == "Failed":
            if re.search("^>>>Differences:", eachline):
                print >> file_out, eachline
            elif re.search("^Total ", eachline):
                print >> file_out, eachline
            elif re.search("^Expected: ", eachline):
                print >> file_out, eachline
            elif re.search("^Actual: ", eachline):
                print >> file_out, eachline
                print >> file_out, "\n"    
    file_input.close()
    
    
def get_time(pattern1, pattern2):  
    global file_out
    obj1 = re.search("Execution started at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern1)

    time_string1 = obj1.group(1) + " " + obj1.group(2)
    t1 = time.strptime(time_string1,"%m/%d/%Y %H:%M:%S %p")
# year in time_struct t1 can not go below 1970 (start of epoch)!
    t2 = time.mktime(t1)#print >> file_out, int(strftime(obj1.group(1) + " " + obj1.group(2)))

    obj2 = re.search("Execution completed at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern2)

    time_string2 = obj2.group(1) + " " + obj2.group(2)
    t3 = time.strptime(time_string2,"%m/%d/%Y %H:%M:%S %p")
    # year in time_struct t1 can not go below 1970 (start of epoch)!
    t4 = time.mktime(t3)

    sec = timedelta(seconds = (t4 - t2))
    d = datetime(1,1,1) + sec
    execution_time = "Total time for test execution %d days, %d hours, %d mins, %d secs" % (d.day-1, d.hour, d.minute, d.second)
    return execution_time

def test1():
    global file_out
    file_out = open("test_summary.txt", 'w')
    pass_count, et = count_tests("Passed")
    failed_count, et = count_tests("Failed")
    print >> file_out, "Total execution time :", et
    print >> file_out, "Number of passed tasks :", pass_count
    print >> file_out, "Number of failed tasks :", failed_count
    file_out.close()
    
def test2():
    global file_out
    file_out = open("test_passed.txt", 'w')
    parse_test_output("Passed")
    file_out.close()

def test3():
    global file_out
    file_out = open("test_failed.txt", 'w')
    parse_test_output("Failed")
    file_out.close()
    
test1()
test2()
test3()
        

