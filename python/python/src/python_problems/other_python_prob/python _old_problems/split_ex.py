t1 = raw_input("Enter a price:")
#t1 = "$2.99 after rebate"
x1 = regexpi("After rebate")
print x1
if x1 in t1:
    ts = t1.split(" ")
    r1 = ts[0]
    A1 = r1[1:]
    print A1
else:
    x1= t1[1:]
    print x1