import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

'''sql = """delete from employee
      where FirstName = "Ram" and LastName = "Nag"  """
cursor.execute(sql)
db.commit()'''

cursor.execute("select * from employee")
p(cursor.fetchall())


