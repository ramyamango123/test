import re

p = 'Python is a #scripting   language edited new I am learning to? test it!'

#x = 'Python is a #scripting   language edited new\n', 'I am learning to? test it!'

#pattern = re.search("(\w+)\s+(\w+)", p)
#pattern = re.search("(.*)", p)
pattern = re.search("(\w+.*)\#(\w+)\s+(.*)\?(\s+\w+\s+\w+)\!", p)
result =  pattern.group(1)+ pattern.group(2)+ " " + pattern.group(3) + pattern.group(4)
print result
#print pattern.group(1) + pattern.group(2) + pattern.group(3)