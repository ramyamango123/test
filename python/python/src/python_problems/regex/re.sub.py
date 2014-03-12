# Search and replace
import re
data = 'I am $arranging this again. # carry this idea again and again and sort it'
# First method
#p = re.compile('(blue|white|red)')
'''p = re.compile('(#|$)')
res = p.sub( '', 'I am arranging this again. # carry this idea again and again and sort it')
print res'''
 # Second method
#re.sub(pattern, repl, string, max=0)
p = re.sub(r'\$|#|\.', "", data)
print p