# A program to count no of words by getting rid of special characters present in the file
import re
in_file = open("lines.txt", 'r')
x = in_file.readlines()
#print x

# Open a empty list
li = []
# Initializing the counter to zero 
count = 0
print len(x)

# Traversing through each line in the list
for eachline in x:
  # if eachline == '\n':
     # x.remove('\n')
#print x
    if eachline != "\n":
        li.append(eachline.rstrip('\n'))
print li

# Joining list of strings into one single string by using join method
joined_sentences =  " " .join(li)
print joined_sentences

# Matching eachline that has some special characters using regex
pattern = re.search("(\w+.*?)\#(\w+)\s+(.*)\?(\s+\w+\s+\w+)\!", joined_sentences)
result =  pattern.group(1)+ pattern.group(2)+ " " + pattern.group(3) + pattern.group(4)
print result

# Split eachline (which is free of special characters) into separate words
words = result.split(" ")
print words

# Counting no of words in the list
for eachword in words:
    print eachword
    count += 1
print "total no of words", count
        
# Closing the file
in_file.close()
'''out_file = open("lines1.txt", 'w')
out_file = out_file.write(x)
print out_file
out_file.close()'''

