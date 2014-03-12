db = {}

def new_user():
    prompt = "Login desired"
    while True:
        name = raw_input(prompt)
        if db.has_key(name):
            prompt = "name taken, try another:"
            continue
        else:
            break
    pwd = raw_input("passwd:")
    db[name] = pwd
    
    
def old_user():
    name = raw_input("login:")
    pwd = raw_input("passwd:")
    passwd = db.get(name)
    if passwd == pwd:
        print "welcome back", name
    else:
        print "login incorrect"

        

def showmenu():
    prompt = """
    (N)ew User Login
    (E) existing User login
    (Q) uit"""
    
#showmenu()
new_user()
old_user()