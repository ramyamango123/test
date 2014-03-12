import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

sql = """ select persons.LastName, persons.FirstName, persons.City, orders.OrderNo
          from persons
          left join orders
          on persons.P_Id=orders.P_Id
          order by persons.LastName """
          
cursor.execute(sql)
output = cursor.fetchall()

p(output)

db.close()
          