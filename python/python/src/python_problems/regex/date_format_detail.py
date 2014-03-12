
# To generate "jan 2010 14th sunday" from jan/14/2010 

import re;
s = "jan/14/2010"

# Escaping a special char "/" by putting back slash in front of it or use "."(matches any single char)
print re.search("(\D+)\/(\d+).(\d+)", s).group(1, 2, 3) 

Pattern = re.search("(\D+)\/(\d+).(\d+)", s)
print Pattern.group(1) + "/" + Pattern.group(2) + "/" + Pattern.group(3) # Printing jan/14/2010
print Pattern.group(1) + " " + Pattern.group(3) + " " + Pattern.group(2)+ "th " + "sunday" # Printing jan 2010 14th sunday