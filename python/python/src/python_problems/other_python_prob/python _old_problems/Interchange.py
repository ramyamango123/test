name = raw_input("Enter your name:")
if name < "y":
    if name.startswith("Mr"):
        print "Hello, " + name
    elif name.startswith("Mrs"):
        print "Hello " +name
    else:
        print "Hello stranger"
else:
    print "Name is invalid"

       
    
    