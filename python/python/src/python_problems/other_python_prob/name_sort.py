out_file = open("name_sort_new.txt",'w')
i = 0
l =[]
while  i <= 2:
    name = raw_input("Enter a name:")
    l.append(name)
    i = i + 1
print l
l.sort()
for s in l:
    out_file.write(s + "\n" )
out_file.close()
