



# Given the list below remove the repetition of an element. All the elements should be unique

uniquewords = ['one', 'one', 'one', 'two', 'three', 'three', 'two']
#result = set(uniquewords)
#print result





# Iterate over a list of words and use a dictionary to keep track of the frequency(count) of each word

dict1 = {}

for eachword in uniquewords:
    if dict1.has_key(eachword):
        dict1[eachword] +=  1
    else:
        dict1[eachword] = 1

print "dict1 :", dict1
        
        
        
        
        
    