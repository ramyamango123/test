a = [1,8,6,5,1,6,8,5,99,302,303,302,303,56,99]

sum = 0
for i in a:
    sum = sum ^ i

print sum
exit()

print a

prev = a[0]
del a[0]
for item in a:
    
    if prev == item:
        print "duplicate number", prev
    prev = item

