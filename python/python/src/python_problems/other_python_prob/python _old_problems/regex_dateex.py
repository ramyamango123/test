import re

todayStr = "2010121016939"
regex = re.match("(\d{4})(\d{1,2})(\d{1,2})(\d{1,2})(\d{1,2})(\d{1,2})", todayStr)
modifiedDateString =  regex.group(1) + "/" + regex.group(2) + "/" + regex.group(3)+ " " + regex.group(4) + ":"  + regex.group(5) + ":" + regex.group(6)
print modifiedDateString