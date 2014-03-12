import re
 
s= "1001"
# match until 1000
 
obj = re.search("\\b([0-9]|([1-9][0-9])|([1-9][0-9][0-9])|1000)\\b", s)
print obj

#res = re.search("\\b" + octet + "\\b", "fdjds34 54dfdss  fgjdfg    200? jfkjds")

#octet = "([0-9]|([1-9][0-9])|(1[0-9][0-9])|(2[0-5][0-5]))"