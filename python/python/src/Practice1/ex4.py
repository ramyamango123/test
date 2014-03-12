import datetime  

def get_date(prompt):
    while True:
        user_input = raw_input(prompt)  
        try:  
            user_date = datetime.datetime.strptime(user_input, "%m/%d/%Y")
            break
        except Exception as e:
            print "There was an error:", e
            print "Please enter a date"
    return user_date

birthday = get_date("Please enter your birthday (MM-DD-YY): ")
another_date = get_date("Please enter another date (MM-DD-YY): ")

if birthday > another_date:
    print "The birthday is after the other date"
elif birthday < another_date:
    print "The birthday is before the other date"
else:  
    print "Both dates are the same"