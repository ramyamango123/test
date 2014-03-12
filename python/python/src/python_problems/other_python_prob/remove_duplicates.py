list1 = ['err', 'try', 'err', 'create', 'ramya', 'try', 'err']
list2 = []
count = 0
for i in list1:
    if i not in list2:
        list2.append(i)
        count += 1
print count
list2.sort()
print list2