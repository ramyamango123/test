import re
import time
from time import strftime
from datetime import datetime, timedelta

def GetTime(s):
    sec = timedelta(seconds=s)
    d = datetime(1,1,1) + sec
    print("Total time for test execution %d days, %d hours, %d mins, %d secs" % (d.day-1, d.hour, d.minute, d.second))


pattern1 = "Execution started at 05/25/2011 05:22:03 PM"

pattern2 = "Execution completed at 05/25/2011 05:34:08 PM"
  
obj1 = re.search("Execution started at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern1)

time_string1 = obj1.group(1) + " " + obj1.group(2)
t1 = time.strptime(time_string1,"%m/%d/%Y %H:%M:%S %p")
# year in time_struct t1 can not go below 1970 (start of epoch)!
t2 = time.mktime(t1)#print int(strftime(obj1.group(1) + " " + obj1.group(2)))

obj2 = re.search("Execution completed at (\d{1,2}/\d{1,2}/\d{4})\s+(((\d{1,2}\:\d{1,2}\:\d{1,2})\s+(AM|PM)))", pattern2)

time_string2 = obj2.group(1) + " " + obj2.group(2)
t3 = time.strptime(time_string2,"%m/%d/%Y %H:%M:%S %p")
# year in time_struct t1 can not go below 1970 (start of epoch)!
t4 = time.mktime(t3)


GetTime(t4 - t2)


        