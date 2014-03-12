import re

p = "Pyth#on is 10% $scripting %language&"

#x = 'Python is a #scripting   language edited new\n', 'I am learning to? test it!'

#pattern = re.search("(.*)([#,$,%,^,&,<,@,!]*.*)", p)

pattern = re.search("(^.*)([#$%^&<@!_-]+)(.*)$", p)
print pattern.group(1)
print pattern.group(2)
print pattern.group(3)
output = ""
for i in p:
    pattern = re.search("(^.*)([#$%^&<@!_-]+)(.*)$", i)
    if pattern == None:
        print i
        output += i
print output
        
        