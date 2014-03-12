# The following code is supposed to remove numbers less than 5 from list n, but there is a bug. Fix the bug

n = [1,2,5,10,3,100,9,24]
m = []

#print n
#print m

for i in n:
    if i > 5:
        m.append(i)
print m
n = m
print n

# To swap two numbers

a = 5
b = 10
c = ""

c = a
a = b
b = c

print "a", a
print "b", b
    
    