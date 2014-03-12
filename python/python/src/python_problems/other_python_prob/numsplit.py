
import unittest, time, re

q = '3 + 5=';
Pattern1 = "(\d+)?\s*\+\s*(\d+)";
T = re.match(Pattern1,  q)
var = int(T.group(1)) + int(T.group(2))
print var
#return var
