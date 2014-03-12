# We have to replace only IP address from second set of codes where 
# name is IP address
import re
'''<xml>
<configuration>
<name>masteraddress</name>
<value>127.0.0.1</value> 
<description>IP address of the host</description>

<name>ipaddress</name>
<!-- Comment here -->
<value>127.0.0.1</value> # Replace with 10.11.68.56
<description>IP address of the host</description>

<name>slaveAddress</name>
<value>127.0.0.1</value>
<description> </description>

</configuration
</xml>'''

edit = False
already_edited = False
fp = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\ip_address_from_xml_file.xml", 'r')
res = fp.readlines()
#print res

for i in res:
     r1= i.rstrip()
     
     #print r1
     pattern = re.search("<name>ipaddress</name>", r1)
     if pattern != None:
         edit = True
         
     if not already_edited and edit and re.search("<value>127.0.0.1</value>", r1):
        # print "new string"
         replaced_data = re.sub("127.0.0.1", "10.1.2.3", r1)
         print replaced_data
         already_edited = True
         continue
        
     print r1
         
         
         
          
             



