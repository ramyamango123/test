list1 = ['2010', 2, '2009', 4, '1989', 8, '2009', 7]
dict1 = {}

for i in range(0, len(list1), 2):
   # print i
    key = list1[i]
    value = list1[i+1]
    if dict1.has_key(key):
        dict1[key].append(value)
    else:
        new_list = []
        new_list.append(value)
        dict1[key] = new_list
       # or #dict1[key] = [value]
print dict1
        