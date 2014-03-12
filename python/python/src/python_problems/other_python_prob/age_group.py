age_check = ['ram', 10, 'sow', 25, 'priyush', 35, 'anar', 8, 'rrr', 37]
dict1 = {}
list1 = [0,0,0,0]
for i in range(0, len(age_check)-1, 2):
    #print i
    key = age_check[i] 
    value = age_check[i+1]
    print key, value
    dict1[key] = value
print dict1
for key, value in dict1.iteritems():
    if value > 0 and value < 10:
        print key + "is in the range of 1-10"
        list1[0] += 1
    elif value > 10 and value < 20:
        print key + "is in the range of 10-20"
        list1[1] += 1
    elif value > 20 and value < 30:
        print key + "is in the range of 20-30"
        list1[2] += 1
    elif value > 30 and value < 40:
        print key + "is in the range of 30-40"
        list1[3] += 1
print list1
        
        
   
