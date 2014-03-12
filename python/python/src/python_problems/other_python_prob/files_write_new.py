in_file = open("lines.txt", 'r')
x = in_file.readlines()
in_file.close()
out_file = open("lines2.txt", 'w')
for i in x:
    out_file.write(i)
    print i
out_file.close()
