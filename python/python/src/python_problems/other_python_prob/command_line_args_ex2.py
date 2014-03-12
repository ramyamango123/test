import sys

print len(sys.argv)

'''if len(sys.argv) > 2:
    print int(sys.argv[1]) + int(sys.argv[2])
else:
    print "Not enough command line arguments"'''



# From cmd enter C:\Python26\python.exe command_line_args_ex2.py "Enter name: ". sys.argv is an array that holds all command line arguments of the program. So sys.argv[0] outputs C:\Python26\python.exe command_line_args_ex2.py and sys.argv[1] outputs  "Enter name: "

'''data = raw_input(sys.argv[1]);

print data + ", good morning"'''




fp = open(sys.argv[1], 'r')

for lines in fp:
    print lines
    
    






