
# To generate date format 01/14/2010 & then change to 14/01/2010

import re;
s = "01/14/2010"

# Escaping a special char "/" by putting back slash in front of it or use "."(matches any single char

Pattern = re.search("(\d{2})\/(\d{2}).(\d{4})", s)
print Pattern.group(1) + "/" + Pattern.group(2) + "/" + Pattern.group(3) # Printing 01/14/2010
print Pattern.group(2) + "/" + Pattern.group(1) + "/" + Pattern.group(3) # Printing 14/01/2010