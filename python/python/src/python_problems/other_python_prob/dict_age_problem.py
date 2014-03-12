# list of names and age

l1 = ['ramu', 8, 'ramya', 21, 'serra', 34, 'gladis', 47, 'meera', 25, 'krish', 39]

dict1 = {}
#for i in (0, len(l1)-1, 2):
i = 0
count = 0
list1 = [0, 0, 0, 0, 0]
while i < len(l1)-1:
    
    key = l1[i]
    value = l1[i+1]
    #print key, value
    i = i + 2
    dict1[key] = value
print dict1
values = dict1.values()
print values
for key, value in dict1.iteritems():
    #print key, value
    #list1[value/10] += 1
        
    if value > 1 and value < 10:
        print key, "is in the age group of  1-10"
        list1[0] += 1
        
    elif value > 10 and value < 20:
        print key, "is in the age group of 10-20"
        list1[1] += 1
    
    elif value > 20 and value < 30:
        print key, "is in the age group of 20-30"
        list1[2] += 1
        
    elif value > 30 and value < 40:
        print key, "is in the age group of 30-40"
        list1[3] += 1
        
    elif value > 40 and value < 50:
        print key, "is in the age group of 40-50"
        list1[4] += 1
    
print list1

    