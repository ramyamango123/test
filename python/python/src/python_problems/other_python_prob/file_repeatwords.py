#repeatwords.txt = QA testing is unique. Python is used for automation testing. Python is used for scripting.

file_open = open("repeatwords.txt", 'r')
#lines = file_open.readlines()
#print lines
l1 = []
d1 = {}
for eachline in file_open:
    print eachline
    #exit()
    seperatelines = eachline.split('.')
    print seperatelines
    p =  " " .join(seperatelines)
    print p
    q = p.split(" ")
    print "q", q
    for i in range(len(q)):
        #print p[i]
        key = q[i]
        print "key", key
        if key in d1.keys():
            d1[key] += 1
        else:
            d1[key] = 1
print d1
        
        
file_open.close()