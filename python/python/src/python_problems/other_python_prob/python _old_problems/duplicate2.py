a = [1,8,5,6,5,2,7]
t = a.sort()
print a
for item in a:
    prev = a[0]
    del a[0]
    if prev == item:
        print "duplicate number", prev
    prev = item

