import re

#p = "Pyth#on is 10% $scripting %language&"
#
#
#result = re.sub("\#|\%|\$|\&", '', p)
#
#print result

#obj = re.compile("\#|\%|\$|\&")
#
#pattern = obj.sub('', p)
#
#print pattern


phone = "2004-959-559 #This is Phone Number"

phnum = re.sub("\#.*$", "", phone)

print phnum