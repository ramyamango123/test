#import re;
#
##email = "nagendra.ramya@gmail.com"
#ip = "1925575.68.20.50"
#
##pattern = re.search("(\w+)\.(\w+)\@\w+\.[a-z]{2,3}", email)
##print pattern.group(2) + " " + pattern.group(1)
#
#pattern = re.search("(\d{3})\.\d{1,3}\.\d{1,3}\.\d{1,3}", ip)
#print pattern.group(1)

in_file = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\regex_data.txt", 'r')
x = in_file.readlines()
linecount = 0
wordcount = 0
for eachline in x:
    print eachline
    linecount += 1
    for eachword in eachline:
        #print eachword
        wordcount += 1
print linecount, wordcount
in_file.close()
out_file = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\new_file.txt", 'w')
for i in x:
     out_file.write(i)
out_file.close()