s1 = "username=mexAPU8Cfz&titleCode=KFPW&referralUrl=http://www.gaziilion.com&service=register&referrer=&emailAddress=mexAPU8Cfz@brainquake.com&gameUserId=1091011206580855667102122&password=password"
result = s1.split("&")
dict ={}
print result
for i in result:
    x = i.split("=")
    key = x[0]
    value = x[1]
    dict[key] = value
print dict