input = '''<xml>
<configuration>
<name>masteraddress</name> # Replace with 10.11.68.56
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
import re
import pprint
p = pprint.pprint

fw = open("debugfile1.txt", 'w')
fw.write(input)
fw.close()

fp = open("debugfile1.txt", 'r')
file_list_data = fp.readlines()
p(file_list_data)
fp.close()
l1 = []
words = [ ]
edit = False
already_edited = False

for eachline in file_list_data:
    res = eachline.rstrip()
    #print "eachline", res 
    
    obj1 = re.search("<name>ipaddress</name>", res)    
    if obj1 != None:
        edit = True   
        
        
    if re.search("# Replace with 10.11.68.56", res):
        res = re.sub("# Replace with 10.11.68.56", "", res)
       # print res
        
        
            
    if not already_edited and edit and re.search("<value>127.0.0.1</value>", res):
        replaced_data = re.sub("127.0.0.1", "345.30.10.31", res)
        pprint.pprint(replaced_data)
        already_edited = True
        continue
    
    
        
    print "res", res



    
   



