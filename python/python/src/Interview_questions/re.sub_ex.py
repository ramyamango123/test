import re

r1 = "<tyre#>"


res = re.sub("#|<|>", " ", r1)
print res


