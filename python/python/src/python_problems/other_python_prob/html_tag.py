import re
 
in_file = open("C:\Users\Ramya\QAdata_as_of_Feb172011\python_problems\other_python_prob\data1.txt")
html_file = in_file.readlines()
print html_file
in_file.close()
for i in html_file:
    print i
                               