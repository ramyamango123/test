list1 = ['ramya','ram','sow','ramu','ramya','ramya','sow','yash','ramya','ramya','ramya','ramya','ramya',]

list2 = []

for i in list1:
    if i not in list2:
        list2.append(i)
print list2

for i in list2:
    count = 0
    for j in list1:
        if i == j:
            count += 1
    print "the name", i, "is present ", count , "times"

        