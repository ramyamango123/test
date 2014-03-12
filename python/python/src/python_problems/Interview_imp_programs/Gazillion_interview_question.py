# 
# This program calculates count of each unique word in a text file
#

#
# Initialize a dictionary with no items first.
#
count = {}

#
# Open the file.
#
f = open('Input.txt', 'r')

#
# Let us track the total number of words as well.
#
total = 0

#
# Iterate over each line in the file
#
for eachLine in f:
    
    #
    # Split the line into different words using python split into a list.
    #
    list1 = eachLine.split( )
    
    #
    # Iterate through each word in the list.
    #
    for word in list1:
        
        #
        # Check if this is the first time we are seeing this word.
        #
        if not count.has_key(word):
            
            #
            # This is the first time for this word, initialie its counter to 0
            #
            count[word] = 1
            
        else:
            #
            # Bump up the counter.
            #
            count[word] += 1

        #
        # Track the total number of words as well.
        #
        total += 1    
#
# Dictionary now contains all unique words.
#            
print "Number of unique words: " + str(len(count))

#
# Print each item in the dictionary. Key is the word encountered and its 
# value is the the number of occurances of those words.
#
for item in count:
    print item + " " + str(count[item])

#
# Print the total number of words as well.
#
print "Toal number of words: " + str(total)

#
# Close the file handle.
#
f.close()
