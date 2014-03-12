import re

d = "oct 14th 2010"

pattern = re.search("(\D+)\s+(\d{2}\D{2})\s+(\d{4})", d)

print pattern.group(1) + " " + pattern.group(2) + " " + pattern.group(3)
