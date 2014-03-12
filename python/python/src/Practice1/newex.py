import re

edit = False
already_edited = False
fp = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\ip_address_from_xml_file.xml", 'r')
res = fp.readlines()
#print res

for i in res:
     r1= i.rstrip()
     #print r1
     
     pattern = re.search("<name>masteraddress</name>", r1)
     if pattern != None:
         print r1
         edit = True
         continue
     
     if already_edited == False and edit == True and re.search("<value>127.0.0.1</value>", r1):
         print "new string"
         replaced_data = re.sub("127.0.0.1", "10.1.2.3", r1)
         print replaced_data
         already_edited = True
         continue
        
     print r1
     
     
     