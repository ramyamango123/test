d1 = {'a':43, 'd':13, 'c':23,'b':56}

d2 = {}
keys = d1.keys()
keys.sort()
# or
#p = sorted(d1.keys())
#print "result", p
print "keys", keys
values = d1.values()
#values.sort()
print values

for i in range(len(keys)):
    print keys[i], values[i], d1[keys[i]]
    d2[keys[i]] = values[i]
    
print d2
    

    