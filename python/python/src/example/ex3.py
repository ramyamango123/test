age_check = ['ramya', 10, 'sow', 25, 'priyush', 35, 'soumya', 8, 'ramya', 37]
dict = {}

for i in range(0, len(age_check), 2):
    #print age_check[i]
    key = age_check[i]
    value = age_check[i+1]
    print key, value
    if key in dict:
        dict[key].append(value)
    else:
        list1 = []
        list1.append(value)
        dict[key]= list1
        #or
        #dict[key] = [value]
print dict


        

    