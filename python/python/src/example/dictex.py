

file_open = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\agedata.csv", 'r')
dict = {}
for i in file_open:
    print i
    x = i.rstrip('\n').split(',')
    print "x", x 
    key = x[0]
    value = x[1]
   # print key, value
    if dict.has_key(key):
        dict[key].append(value)
    else:
        dict[key] = [value]
print dict
file_open.close()
  
     
