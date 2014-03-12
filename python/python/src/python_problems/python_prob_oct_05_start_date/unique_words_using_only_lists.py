# No of occurances of each word in a list using only lists

list1 = ['ramya','ram','sow','ramu','ramya','ramya','sow','yash','ramya','ramya','ramya','ramya','ramya',]

list2 = []

for eachword in list1:
    if eachword not in list2:
        list2.append(eachword)
print list2
unique_words = ""
for eachname in list2:
    count = 0
    for i in list1:
        if eachname == i:
            count += 1
    unique_words = unique_words + " " + str(count)
print unique_words
        