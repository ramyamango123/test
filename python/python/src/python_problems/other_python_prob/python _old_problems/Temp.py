Centi = input("Enter a centigrade temp")
F = 9/5*Centi + 32
print F
if F > 100:
    print "Very high temperature"
elif F > 50 and F < 99: 
    print "Moderate temperature"
else:
    print "Low temperature"
