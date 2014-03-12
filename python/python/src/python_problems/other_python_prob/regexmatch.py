import re

p = re.match("c", "abcdef")  # No match

print p
re.search("c", "abcdef")