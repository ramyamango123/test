import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

'''sql = """update employee
         set FirstName = "Rahul", sex = "M", income = 5000
         where LastName = "Nag2" and age = 60"""
cursor.execute(sql)
db.commit()'''

cursor.execute("select * from employee")
p(cursor.fetchall())


