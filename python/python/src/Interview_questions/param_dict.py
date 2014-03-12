import re
s1 = "username=mexAPU8Cfz&titleCode=KFPW&referralUrl=http://www.gaziilion.com&service=register&referrer=&emailAddress=mexAPU8Cfz@brainquake.com&gameUserId=1091011206580855667102122&password=password"
dict = {}
p1 = s1.split("&")
print p1
Stuff = [i.split("=") for i in p1]
print "stuff", Stuff
for j in Stuff:
    print "j", j
    key = j[0]
    value = j[1]
    dict[key] = value
print dict
'''
file = "<xml>  \
<configuration>  \
<name>masteraddress</name>  \
<value>127.0.0.1</value> "


res = re.compile("<|>|/")
data = res.sub(" ", file)
print data
d ={}
split_words = data.split()
print split_words
for i in range(0, len(split_words)-1, 2):
    print split_words[i], split_words[i+1]
    d[split_words[i]] = split_words[i+1]
print d'''