import re

phone = "2004-959-559 #This is Phone Number"

pattern = re.sub(r'\D+', '', phone)

#pattern = re.sub(r'\#\D+', '', phone)

print pattern
