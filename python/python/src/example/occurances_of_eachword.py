''' No of occurances of each word in a list using dictionary'''

list1 = ['ramya','ram','sow','ramu','ramya','ramya','sow','yash','ramya','ramya','ramya','ramya','ramya']

list2 = []
#dict1 = {}
#
#for i in list1:
#    if dict1.has_key(i):
#        dict1[i] = dict1[i] + 1
#    else:
#        dict1[i] = 1
#print dict1
#
#for eachkey in dict1.keys():
#    print  eachkey + " has occured : " + str(dict1[eachkey]) + " time(s)"
#    


'''No of occurances of each word in a list using lists'''

for i in list1:
    print i
    if i not in list2:
        list2.append(i)
print list2

for eachname in list2:
    count = 0
    for i in list1:
        if eachname == i:
            count += 1
    print eachname + " has occured " + str(count) + " times"
            

        