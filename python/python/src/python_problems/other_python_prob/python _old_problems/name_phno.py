# write a file with name and ph no 
out_file = open("new.txt", 'w')
name = raw_input("Enter a name:")
ph_num = raw_input("Enter a ph no:")
while (name != " "):
    out_file.write(name)
    out_file.write("\n")
name = raw_input("Enter a name:")
    
while (ph_num != " "):
    out_file.write(ph_num + "\n")
    ph_num = raw_input("Enter a ph no:")
out_file.close()
    
