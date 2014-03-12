import re

p = "Pyth#on is 10% $scripting %language&"

# Two ways to remove special characters
'''output = ''
for eachletter in p:
    pattern = re.search("(^.*)([#$%^&<@!_-]+)(.*)$", eachletter)
    if pattern == None:
        output = output + eachletter
print output'''

obj = re.compile("\#|\%|\$|\&")

pattern = obj.sub('', p)

print pattern

    

    
