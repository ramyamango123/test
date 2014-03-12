# To count number of lines in a file. Later check number of words and letters in it
import re
file_open =
open("multiple_lines.txt", 'r')


count = 0
l1 = []
for i in file_open:
#   i = i.strip('\n')
    if i != "\n":
        l1.append(i.rstrip())
  
#for i in l1:
#    if i == "":
#        l1.remove(i)
    
print l1
exit()

print "list of sentences", l1
l2 =[]
j = 0
while j <= len(l1)-1:
    
    #print j
    print l1[j]
    l2.append(l1[j])
    j = j + 2
    count += 1
    
print "l2 list", l2
print "Total number o flines:", count

for k in l2:
    print k
    for h in k:
        print h
    
    
file_open.close()