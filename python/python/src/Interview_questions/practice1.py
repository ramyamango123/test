# xmltodict

# First we need to install xmltodict. 
# So from cmd look where easy install is there
# or set up the path. Currently it is in

import xmltodict
import json

e = xmltodict.parse("""<?xml version="1.0" ?>
<root>
<person>
  <name>john</name>
  <age>20</age>
</person>
<person2>
  <name>john</name>
  <age>20</age>
</person2>
</root>""")

print json.dumps(e, indent=2)
#print e
print json.dumps(e["root"]["person"]["name"])

