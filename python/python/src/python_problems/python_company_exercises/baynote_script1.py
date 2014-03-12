#
# This program parses input Baynote_input.txt test results in a (particular
# format) and outputs 
#       1. A sumary of passed and failed tests - test_summary.txt
#       2. List of all passed test cases       - test_passed.txt
#       3. List of all failed test cases       - test_failed.txt
#

#
# Import required modules and classes
#
import re
import time
from time import strftime
from datetime import datetime, timedelta

#
# File names that this script takes as input
#
input_file = "Baynote_input.txt"

#
# Files that this scripts generates output
#
output_file_summary  = "test_summary.txt"
output_file_passed   = "test_passed.txt"
output_file_failed   = "test_failed.txt"

#
# Global output file handle
#
output_file_handle   = None

#
# get_tests_count_and_execution_time
#
# Return number of tests of a specific type "Passed" or "Failed"
# Also return total test time execution
#
def get_tests_count_and_execution_time (input_type):

    #
    # Declare globals that we use such as input file name and output file handle
    #
    global output_file_handle
    global input_file

    #
    # Open input file for reading, exit if we can't do so
    #
    try:
        file_input = open(input_file, 'r')
    except:
        print input_file + " does not exists!\n"
        exit(0)

    #
    # Initialize number of tests - count to 0
    #
    count = 0

    #
    # Iterate through each line in the file and look for desired pattern
    #
    for eachline in file_input:

        #
        # Get rid of trailing '\n'
        #
        eachline = eachline.rstrip()

        #
        # Check if this line matches expected pattern to track number of tests
        # and the test excution start/end time.
        #
        if re.search("^\*\*\*" + input_type + "\!\*\*\*$", eachline):

            # 
            # Increment the counter accordingly
            #
            count += 1
        elif re.search("^Execution started ", eachline):

            #
            # Note down execution time start
            #
            pattern1 = eachline
        elif re.search("^Execution completed ", eachline):

            #
            # Note down execution time end
            #
            pattern2 = eachline

    #
    # Compute total time of execution based on start and end time
    #
    et = get_time(pattern1, pattern2)

    #
    # Close the input file handle
    #
    file_input.close()

    #
    # Return the computed number of tests and the total time of execution
    #
    return count, et

#
# parse_test_output
#
# Parse test output and retrieve more details on passed or failed cases as
# requested through input_type ("Passed" or "Failed")
#
def parse_test_output (input_type):

    #
    # Declare global variables
    #
    global output_file_handle
    global input_file

    #
    # Get total number of passed/failed tests and the execution time
    #
    count, et = get_tests_count_and_execution_time(input_type)

    #
    # Print number passed/failed tests to output file
    #
    print >> output_file_handle, "\nTotal number of " + input_type + " tasks is:", count

    #
    # Open input file for reading, exit if there is any error
    #
    try:
        file_input = open(input_file, 'r')
    except:
        print input_file + " does not exists!\n"
        exit(0)

    #
    # Iterate through each line in the input file
    #
    for eachline in file_input:
        eachline = eachline.rstrip()
        if re.search("^\*\*\*" + input_type + "\!\*\*\*$", eachline):

            #
            # We found the "Passed" or "Failed" line. Print rest of the info
            # already retrieved so far (Test case and URL)
            #
            print >> output_file_handle, "=======================================\n"
            print >> output_file_handle, case
            print >> output_file_handle, reg_url, "\n"
        elif re.search("^\[Case ", eachline):
            
            #
            # Retrieve test case information
            #
            case = eachline
        elif re.search("^\[REQ\-URL\]\: ", eachline):

            #
            # Retrieve test URL
            #
            reg_url = eachline
        elif input_type == "Failed":

            #
            # For "Failed" test cases, print the Differences as well
            #
            if re.search("^>>>Differences:", eachline):
                print >> output_file_handle, eachline
            elif re.search("^Total ", eachline):
                print >> output_file_handle, eachline
            elif re.search("^Expected: ", eachline):
                print >> output_file_handle, eachline
            elif re.search("^Actual: ", eachline):
                print >> output_file_handle, eachline
                print >> output_file_handle, "\n"    
    file_input.close()

#
# get_time
#
# Compute test execution time based on the start and end time
#
def get_time (pattern1, pattern2):  

    #
    # Declare global variables
    #
    global output_file_handle

    #
    # Parse test start time and retrieve the actual day and time part
    #
    obj1 = re.search("Execution started at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern1)

    #
    # Convert the time string into time object (to compute the duration)
    #
    time_string1 = obj1.group(1) + " " + obj1.group(2)
    t1 = time.strptime(time_string1,"%m/%d/%Y %H:%M:%S %p")
    t2 = time.mktime(t1)

    #
    # Parse test end time and retrieve the actual day and time part
    #
    obj2 = re.search("Execution completed at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern2)

    #
    # Convert the time string into time object (to compute the duration)
    #
    time_string2 = obj2.group(1) + " " + obj2.group(2)
    t3 = time.strptime(time_string2,"%m/%d/%Y %H:%M:%S %p")
    t4 = time.mktime(t3)

    #
    # Compute tests execution in seconds based on the difference between
    # stop time and start time
    #
    sec = timedelta(seconds = (t4 - t2))

    #
    # Convert test duration in seconds into more friendly hour/min/sec uer
    # friendly format
    #
    d = datetime(1,1,1) + sec
    execution_time = "Total time for test execution %d days, %d hours, %d mins, %d secs" % (d.day-1, d.hour, d.minute, d.second)

    #
    # Return computed execution time
    #
    return execution_time

#
# test1_get_summary
#
# Compute total tests summary with number of "Passed" test cases, number of
# "Failed" test cases and total test execution time and print them into
# output file test_summary.txt
#
def test1_get_summary ():

    #
    # Declare globals we use
    #
    global output_file_handle

    #
    # Open output file for writing, exit if there is any failure
    #
    try:
        output_file_handle = open(output_file_summary, 'w')
    except:
        print "Cannot open " + output_file_summary + " for writing!\n"
        exit(0)

    #
    # Get total number of passed/failed tests and the execution time
    #
    pass_count, et = get_tests_count_and_execution_time("Passed")
    failed_count, et = get_tests_count_and_execution_time("Failed")

    #
    # Print desired information into output file
    #
    print >> output_file_handle, "Total execution time :", et
    print >> output_file_handle, "Number of passed tasks :", pass_count
    print >> output_file_handle, "Number of failed tasks :", failed_count
    output_file_handle.close()

#
# test2_get_passed_test_cases_information
#
# Compute all "Passed" test cases information in detail with test Case info,
# and test URL and print them into output file test_passed.txt
#
def test2_get_passed_test_cases_information ():

    #
    # Declare globals we use
    #
    global output_file_handle

    #
    # Open output file for writing, exit if there is any failure
    #
    try:
        output_file_handle = open(output_file_passed, 'w')
    except:
        print "Cannot open " + output_file_passed + " for writing!\n"
        exit(0)

    #
    # Print desired information into output file
    #
    parse_test_output("Passed")
    output_file_handle.close()

#
# test2_get_failed_test_cases_information
#
# Compute all "Failed" test cases information in detail with test Case info,
# test URL, and test output differences with expected and actual, and print
# them into output file test_passed.txt
#
def test3_get_failed_test_cases_information ():

    #
    # Declare globals we use
    #
    global output_file_handle

    #
    # Open output file for writing, exit if there is any failure
    #
    try:
        output_file_handle = open(output_file_failed, 'w')
    except:
        print "Cannot open " + output_file_failed + " for writing!\n"
        exit(0)

    #
    # Print desired information into output file
    #
    parse_test_output("Failed")
    output_file_handle.close()

#
# main
#
# This is the main routine of this test script.
#
def main ():

    #
    # Parse input file and produce a summary of all tests
    #
    test1_get_summary()

    #
    # Parse input file and produce "Passed" test cases in detail
    #
    test2_get_passed_test_cases_information()

    #
    # Parse input file and produce "Failed" test cases in detail
    #
    test3_get_failed_test_cases_information()
    exit(0)

#
# Call the main routine of this script
#
main()
