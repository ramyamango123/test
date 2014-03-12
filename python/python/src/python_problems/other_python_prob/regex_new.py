import re
s1 = "username=mexAPU8Cfz&titleCode=KFPW&referralUrl=http://www.gaziilion.com&service=register&referrer=&emailAddress=mexAPU8Cfz@brainquake.com&gameUserId=1091011206580855667102122&password=password"

Pattern = re.search("(username=)(\w+)(&titleCode)", s1)
print Pattern.group(2) # Printing 01/14/2010


