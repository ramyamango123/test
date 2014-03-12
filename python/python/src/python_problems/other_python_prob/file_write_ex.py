import re

file1 = open("file_count_remove_duplicate.txt", 'r')
x = file1.readlines()
#print x
file1.close()
file_in = open("filetest.txt", 'w')
for i in x:
    file_in.write(i)

file_in.close()