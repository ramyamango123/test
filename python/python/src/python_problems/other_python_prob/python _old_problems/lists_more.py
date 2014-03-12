demo = ["Life",42,"the universe",6,"and",7]
print "demolist = ", demo
demo.append('fine')
print len(demo)
print demo.index(42)
print demo[1]
num = 0
while num <= len(demo):
    print "demolist:", demo[num-1]
    num = num + 1
demo.sort()
print demo