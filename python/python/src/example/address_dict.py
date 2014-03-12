import re;
in_text = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\addresses.txt", 'r')
dict = {}

result = in_text.readlines()
for i in result:
    result = i.split()
    print "result", result
    key = result[0] + " " + result[1]
    #print key
    value = result[2]
   # print value
    dict[key] = value
print dict
sorted_keys = sorted(dict.keys())
print sorted_keys