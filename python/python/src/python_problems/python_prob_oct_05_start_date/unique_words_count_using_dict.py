# No of occurances of each word in a list using dictionary

list1 = ['ramya','ram','sow','ramu','ramya','ramya','sow','yash','ramya','ramya','ramya','ramya','ramya']

dict1 = {}

for eachname in list1:
    # or if not dict1.has_key(eachname):
    if eachname not in dict1:
        dict1[eachname] = 1
    else:
        dict1[eachname] += 1
print dict1