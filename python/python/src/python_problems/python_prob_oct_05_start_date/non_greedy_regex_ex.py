import re

p = "Another whale sighting occurred on <January 26>, <2004>"

pattern = re.search("(<.*?>)", p)

print pattern.group(1)


