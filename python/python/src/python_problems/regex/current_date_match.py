import re

s = "april/13/2011"

p = re.search("(\w+)\/(\d{2})\/(\d{4})", s)

print p.group(1) + "/" + p.group(2) + "/" + p.group(3)
