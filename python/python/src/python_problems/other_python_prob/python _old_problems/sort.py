p = [1,5,6,2,4,5,8]
w = p[:]
w.sort()
print w
Bef = w[0]
del w[0]
for num in w:
    if Bef == num:
        print "Duplicate of", Bef,"found"
    Bef = num
       