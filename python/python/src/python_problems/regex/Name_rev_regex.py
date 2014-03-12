 # To get reverse last & first name in "Ramya Nagendra"

import re;
s = "Ramya Nagendra"

print re.search("(\D+)\s+(\D+)", s).group(2,1) # Reversing last & first name
Pattern = re.search("(\D+)\s+(\D+)", s)

print Pattern.group(1) + " " + Pattern.group(2)
print Pattern.group(2) + " " + Pattern.group(1)


 
 