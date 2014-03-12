import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

'''sql = """insert into employee(p_Id, FirstName, LastName, Age, sex, income)
                      values (4,'Ram2','Nag2', 60, 'F', 2008)"""

cursor.execute(sql)
db.commit()'''


result = cursor.execute("select * from employee")

p(cursor.fetchall())


#p(output)'''



# disconnect from server
db.close()