import re

eachline1 = "Execution started at 05/25/2011 05:22:03 PM"

pattern = re.search("Execution started at \d{1,2}\/\d{1,2}\/\d{4}\s+(\d{1,2}\:\d{1,2}\:\d{1,2}\s+(AM|PM))", eachline1)

print pattern.group(1)