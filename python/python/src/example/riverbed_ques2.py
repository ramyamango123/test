#
# Have a sample input string for testing.
#
input_str = " this is a   python's program"

#
# This is the desired output string
#
#output_str = "4 2 1 8 7"

r = input_str.split()
#print r
dict1 = {}
value = 0
count = 0
#for i in range(len(r)):
#    #print r[i]
#    key = r[i]
#    value = len(r[i]) 
#    #print key, value
#    dict1[key] = value
#print dict1

#or
output_str = ""
for each_word in r:
    count = 0
    #print each_word
    for each_char in each_word:
       # print each_char
        count += 1
    print count
    output_str = output_str + " " +  str(count)
print output_str
    
       