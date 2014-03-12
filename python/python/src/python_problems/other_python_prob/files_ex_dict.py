#addresses.txt
'''Carloz Alverez     551-1332
Marcia O’Rourke    523-6671
Lily McMaster      732-1921
Adam Nowicki       282-8992
Terumi Yobuko      451-3290
Jonathan Ginsberg  646-9902
Susie Swarton      951-6520
Peter Grayson      721-1205
Christine Mansouri 721-1207
John Adams         646-6521
Antonio Folerin    523-6673
Nieves Guerra      721-9937
Jose Quesada       289-7213
Brad Carlos        719-2234'''



import re;
in_text = open("addresses.txt", 'r')
#text = in_text.read()
dict = {}
for eachline in in_text:
#    print eachline
    pattern = re.match("([\w’]+\s+[\w’]+)\s+([\d\-]+)", eachline)
    pattern = re.match("([\w\'\`’]+\s+[\w\'\`’]+)\s+([\d\-]+)", eachline)

    if pattern == None:
        continue
    #print eachline
    name = pattern.group(1)
    number = pattern.group(2)
    #first_name = p[0]
    #last_name = p[1]
    #full_name = p[0] + " " + p[1]
    dict[name] = number
    
print dict
l2 = dict.keys()
l3 = sorted(l2)
print "dict is:", dict
print "L3 is:", l3
out_file = open("newadresses.txt", 'w')
for name in l3:
    print name, dict[name]
    out_file.write(name + "   " + dict[name] + '\n')
print len(l3)
out_file.close()

in_text.close()

'''otherr way

fp = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\linesnew.txt", 'r')
dict1 = {}
for i in fp:
    result = i.split()
    key = result[0] + " " + result[1]
    value = result[2]
    dict1[key] = value
print dict1
x = dict1.keys()
sorted_data = sorted(x)

fw = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\writingtoafile.txt", 'w')
for j in sorted_data:
    fw.write(j + " "  + dict1[j] + '\n')
fp.close()
fw.close()'''