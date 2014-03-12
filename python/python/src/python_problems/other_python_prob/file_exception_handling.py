try:
   fh = open("testfile.txt", "w")
   fh.write("This is my idea!!")
except IOError:
   print "Error: can\'t find file or read data"
else:
   print "Written content in the file successfully"
   fh.close()