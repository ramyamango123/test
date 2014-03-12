import re

p = "Pyth#on is 10% $scripting %language&"
#p = "pyt3hon"

#p = "this iwss a   (sample) sentence"


output = ""
for i in p:
    #pattern = re.search("\D", i)
    #print pattern, i
    pattern = re.search("(^.*)([#$%^&<@!_-]+)(.*)$", i)
    if pattern == None:
        print i
        output += i
print output