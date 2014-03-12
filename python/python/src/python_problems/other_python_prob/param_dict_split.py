import re
s1 = "username=mexAPU8Cfz&titleCode=KFPW&referralUrl=http://www.gaziilion.com&service=register&referrer=&emailAddress=mexAPU8Cfz@brainquake.com&gameUserId=1091011206580855667102122&password=password"
dict = {}
p1 = s1.split("&")
print p1
Stuff = [i.split("=") for i in p1]
print Stuff
for j in Stuff:
    print "j", j
    key = j[0]
    value = j[1]
    dict[key] = value
print dict
    
