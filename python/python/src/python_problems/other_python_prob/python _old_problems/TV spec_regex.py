# To get panasonic TV spec in a list

import re;

s = "Panasonic tv 32 inch 1920*1080 resolution weight 37.5 lbs NTSC"

# Special char ("*") is escaped by a backslash, to address floating number(37.5) "." is added between integers (37 & 5)
print re.search("(\D+)\s+(\D+)\s+(\d+)\s+(\D+)\s+(\d+)\*(\d+)\s+(\D+)\s+(\D+)\s+(\d+).(\d+)\s+(\D+)\s+(\D+)", s).group(1, 2,3,4,5,6,7,8,9,10,11,12)

# Pattern is saved to simplify its further use 
Pattern = re.search("(\D+)\s+(\D+)\s+(\d+)\s+(\D+)\s+(\d+)\*(\d+)\s+(\D+)\s+(\D+)\s+(\d+).(\d+)\s+(\D+)\s+(\D+)", s)

print "Panasonic TV resolution"
print "-----------------------"
print "Model - " + Pattern.group(1) + "\n" + "Screen size (Diagonal) - " + Pattern.group(3) + "\n" + "Resolution - " + Pattern.group(5) + " X " + Pattern.group(6) + "\n" + "Weight - " + Pattern.group(9) + "." + Pattern.group(10) + Pattern.group(11) +"\n" + "Video Sytem - " + Pattern.group(12)
