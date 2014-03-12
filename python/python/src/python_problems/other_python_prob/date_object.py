import datetime

birthday_date1 = "11/28/2007 15:54"


birthday_date2 = "09/23/2007 16:08"

userinput1 = datetime.datetime.strptime(birthday_date1, "%m/%d/%Y %M:%S")
userinput2 = datetime.datetime.strptime(birthday_date2, "%m/%d/%Y %M:%S")

if userinput1  > userinput2:
    print "userinput1 is older"