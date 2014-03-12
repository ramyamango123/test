# No of occurances of unique words using dictionary

import re
file_open = open("unique_words2.txt", 'r')

x = file_open.readline()

#print x



dict1 = {}

compiled_obj = re.compile('\.|\\n')
split_words = compiled_obj.sub('', x)
seperate_words_list = split_words.split()
print seperate_words_list

for eachword in seperate_words_list:
    if eachword not in dict1:
        dict1[eachword] = 1
    else:
        dict1[eachword] += 1
print dict1
