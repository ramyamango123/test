# Program to find no of lines, words, letters in a file

import re
file_open = open("multiple_lines.txt", 'r')
x = file_open.readlines()
list1 = []
newlist = []
letterlist =[]
count = 0
wordcount = 0
lettercount = 0
# Here we are counting no of lines
for lines in x:
    if '#' in lines:
        print "It's a commented line"
        continue
    if lines != "\n":
        list1.append(lines.rstrip())
        count += 1
        
print "Total no of lines", count

# In this block, we are counting no of words present in these lines
# We can count no of words using len(list) or by using a counter like wordcount and increment it inside the loop
for eachline in list1:
    print "eachline", eachline
    # Here we counting no of letters in all the words
    for letters in eachline:
        print "letters", letters
        if letters != " ":
            letterlist.append(letters)
            # Lettercount can be done by incrementing by 1 or by finding the length of list after appending these letters to the list
            lettercount += 1
         
    words = eachline.split(" ")
    #print "words are", words
    for eachword in words:
        print "eachword", eachword
        wordcount += 1
        print wordcount
        if eachword != " ":
            newlist.append(eachword.rstrip())
print "lettercount", lettercount
print "letterlist", letterlist   
# Finding the length of list after appending these letters to the list
print len(letterlist)
print "newlist", newlist
print len(newlist)

# In this block, we are counting no of letters present in these words


file_open.close()