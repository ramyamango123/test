#list1 = ['err', 'try', 'err', 'create', 'ramya', 'try', 'err']
#list2 = []
#
#for i in list1:
#    if i not in list2:
#        list2.append(i)
#print list2
#    


p = ['horse', 'cat', 'rabbit', 'rat', 'cat', 'horse', 'snake', 'horse']

dict ={}
count = 0
for i in p:
    print i
    if i in dict:
        dict[i] += 1
    else:
        dict[i] = 1
print dict        
