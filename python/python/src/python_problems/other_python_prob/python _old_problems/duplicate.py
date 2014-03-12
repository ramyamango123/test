p = [6,8,49,6,7,1]
q = p.sort()
print p
prev = p[0]
#n1 = p[1]
del p[0]
for item in p:
    if prev == item:
        print "Duplicate of", prev,"found"
    #prev = item
        