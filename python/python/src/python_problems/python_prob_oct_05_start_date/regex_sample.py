import re
email = 'NAGENDRA.RAMYA@GMAIL.UK.EDU'

pattern = re.search("(\w+)(\.\w+)?(\@\w+)((\.([a-z]{2,3})){1,2})", email, re.I)

print pattern

print pattern.group(1) + pattern.group(2)+ pattern.group(3) + pattern.group(4)