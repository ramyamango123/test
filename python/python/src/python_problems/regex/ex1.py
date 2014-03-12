
import re

x = "254.255.255.258"
#x = "345"

#pattern = re.search("^([0-9]{1,3}\.){3}[0-9]{1,3}$", x)
pattern = re.search("^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$",x)
p =  pattern
print p
