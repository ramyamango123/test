import pprint

file_open = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\agedata.csv", 'r')

d = { }
for line in file_open.readlines():
    print "line", line
    l = line.rstrip("\n").split(",")
    print l
    key = l[0]
    print "key", key
    value = l[1]
    
    if key == "name":
        continue
    if d.has_key(key):
        d[key].append(value)
    else:
        d[key] = [ value ]
#for i in file_open:
#    print i
pprint.pprint(d)
file_open.close()