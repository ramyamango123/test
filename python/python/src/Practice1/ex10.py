import re

input = "200"

#p = re.match("^([0-9]|1[0-9]|20)$", input)


p = re.match("^([0-9]|([1-9][0-9])|[1][0-9][0-9]|200)$", input)
print p