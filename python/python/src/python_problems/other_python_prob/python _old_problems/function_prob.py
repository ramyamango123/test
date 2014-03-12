a = 3
b = 12
c = 34
def num_calc(n):
    print "a:",a
    b = a+12
    print "b:",b
    c = a+b
    print "c:",c
    e = a*b
    print "e:",e
    return b+12
print "New numbers are:",num_calc(b)
print "new b:", b
print "new c:", c
print "new a:", a