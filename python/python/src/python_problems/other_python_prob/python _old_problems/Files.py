# Reading and writing a file
fileHandle = open ( "ram.txt",'w')
fileHandle.write ("This is a test message")
print fileHandle.read()
fileHandle.close()
