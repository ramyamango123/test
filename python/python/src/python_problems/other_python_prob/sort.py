p = [1,5,5,2,4,8,8]
w = p[:]
w.sort()
print w
Bef = w[0]
del w[0]
for num in w:
    if Bef == num:
        print "Duplicate of", Bef,"found"
    Bef = num
       