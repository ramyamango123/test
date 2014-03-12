# To combine 2 lists into one dictionary
a1 = [1, 2, 3, 4]

b1 = ['ab', 'cd', 'ef', 'gh']

dict1 = {}

'''for i in range(len(a1)):
    dict1[a1[i]] = b1[i]
print dict1
#or
for i, data in enumerate(a1):
    print i, data
    dict1[data] = b1[i]
print dict1

#or
d = dict(map(None, a1, b1))
print d'''

# or

d = dict(zip(a1, b1))
print d

