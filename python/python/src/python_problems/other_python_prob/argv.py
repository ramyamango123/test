import sys
print sys.argv
file_open = open(sys.argv[1], 'r')

for lines in file_open:
    print lines
file_open.close()	