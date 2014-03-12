import re

f = "ramya#nagendra&&&&&&&"

#p = re.search("(\w+)\$(\w+)\&", f)
#print p.group(1) + p.group(2)

pattern = re.sub(r'[&,$,#]',' ', f)

print pattern

                 