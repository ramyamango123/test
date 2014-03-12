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
print "l3 is:", l3
out_file = open("newadresses.txt", 'w')
for name in l3:
    print name, dict[name]
    out_file.write(name + "   " + dict[name] + '\n')
print len(l3)
out_file.close()

in_text.close()