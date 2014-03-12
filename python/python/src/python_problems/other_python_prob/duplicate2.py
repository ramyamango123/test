a = [1,8,5,6,8]
#t = a.sort()
print a

prev = a[0]
del a[0]
for item in a:
    
    if prev == item:
        print "duplicate number", prev
    prev = item

