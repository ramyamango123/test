
list1 = ['ramya','ram','sow','ramu','ramya','ramya']
list2 = ['ramya', 'ram', 'sow', 'ramu', 'yash']

unique_words = ""
#count = 0
for eachname in list2:
    count = 0
    for i in list1:
        if eachname == i:
            count += 1
    unique_words = unique_words + " " + str(count)
print unique_words
        