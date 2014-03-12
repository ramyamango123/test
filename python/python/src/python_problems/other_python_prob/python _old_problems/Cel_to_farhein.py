def cel_far(tem1):
    t1 = 9.0/5.0 * tem1 + 32.0
    return(t1)
def far_cel(tem2):
    t2 = (tem2 - 32.0) * 5.0 / 9.0
    return(t2)
def options():
    print "'C' to convert cel to fahrenheit"
    print "'F' to convert far to celsius"
    print "'q' quit the program"
Choice = 'p'
while Choice != "q":
    if Choice == "c":
        temp1 = input("enter a temp in celsius:")
        print "celsius to fahrenheit is :", cel_far(temp1)
    elif Choice == "f":
        temp2 = input("enter a temp in fahreinheit:")
        print "fahrenheit to Celsius  is:" ,far_cel(temp2) 
    elif Choice != "q":
        print "print the list:\n",options()
    Choice = raw_input("option:")
    
    
    

