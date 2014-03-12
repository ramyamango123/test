import re;

eachline1 = "Marcia O'Rourke     876-345"
eachline2 = "Marcia O’Rourke 876-345"


pattern = re.match("([\w\'\`’]+\s+[\w\'\`’]+)\s+([\d\-]+)", eachline2)
name = pattern.group(1)
number = pattern.group(2)
print name, number