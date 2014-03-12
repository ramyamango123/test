#couting unique occurances using lists

list1 = ['Tomato', 'pear', 'cherry', 'Tomato', 'Tomato', 'cherry']

list2 =[]

for eachfruit in list1:
    if eachfruit not in list2:
        list2.append(eachfruit)
print list2

for eachitem in list2:
    count_unique_fruits = 0
    for eachfruit in list1:
        if eachitem == eachfruit:
            count_unique_fruits += 1
    print count_unique_fruits
    
            

