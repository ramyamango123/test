
# GO to cmd and to the respective folder where the python file is saved. In cmd say for ex:create a new file with name sys.py. In cmd give python sys.py sort_test.txt
#here sort_test.py already exists in a folder. You can give multiple parameters following file name and that can be grabbed using argv. In cmd whatever u 
# give following file name that will get added to the argvlist. ex:In cmd u give python sys.py sort_test.txt ram.txt. Here sort_test.txt is at position 1 and ram.txt is at position 2
# So u get it by using sys.argv[1] here in file opn command instead of give direct file name. So here it reads the data in sort_test.txt (which is at position 1) and prints out file
# ex: when u print sys.argv it prints out ['sys_test.py', 'sort_test.txt', 'ram.txt']

import sys

file_open = open(sys.argv[1], 'r')

for lines in file_open:
    print "lines", lines
print "sys.argv", sys.argv
file_open.close() 