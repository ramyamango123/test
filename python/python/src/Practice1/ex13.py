import re

eachword = "23.3.89.2"
r1 = re.search("(^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$)", eachword)

print r1