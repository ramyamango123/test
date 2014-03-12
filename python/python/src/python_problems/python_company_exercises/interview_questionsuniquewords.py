



# Given the list below remove the repetition of an element. All the elements should be unique

uniquewords = ['one', 'one', 'one', 'two', 'three', 'three', 'two']
#result = set(uniquewords)
#print result


# Iterate over a list of words and use a dictionary to keep track of the frequency(count) of each word

'''dict1 = {}

for eachword in uniquewords:
    if dict1.has_key(eachword):
        dict1[eachword] +=  1
    else:
        dict1[eachword] = 1

print "dict1 :", dict1'''


# If a list of words is empty, then let the user know it's empty, otherwise let the user know it's not empty

'''a = []

print len(a)
if len(a):
    print "list in not empty"
else:
    print "List is empty"'''
    

# Demonstrate the use of exception handling in python

'''a = [1,2,3,4]

try:
    print a[5]
except Exception, e:
    #print "The list index is out of range"
    #print "Error %s" % e
    print e'''


# Print the length of each line in the file 'file.txt' not including any whitespaces 

file_open = open("file_lines_length.txt", 'r')

for i in (file_open):
    result = i.rstrip('\n')
    print len(result)


# Print the sum of digits of numbers starting from 1 to 100
print sum(range(1,3))
        
        
        
        
        
    