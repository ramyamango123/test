import re
data = "there is a $23 purse here!!! and pen."

x = re.compile('\$|\!|\.')
y = x.sub('', data)
print y