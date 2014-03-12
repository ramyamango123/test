import re

file_input = open("baynote_junk.txt",'r')
#list1 = []
count = 0
for eachline in file_input:
    if eachline != '\n':
        stripped_line = eachline.rstrip()
        if re.search("\*\*\*Passed\!\*\*\*", stripped_line):
            count += 1
        #obj = re.compile("\*|\!|\=")
        #pattern = obj.sub('', stripped_line)
        #print pattern
        if pattern == "Passed":
            count += 1
            print "Total number of passed tasks is:", count
        #list1.append(stripped_line)
        
#print list1
#print result
file_input.close()