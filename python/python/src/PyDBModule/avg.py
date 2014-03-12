import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

sql = "select sum(OrderPrice) as OrderAverage from orders1"
          
cursor.execute(sql)
output = cursor.fetchall()
result = "\n".join(output)

p(result)

db.close()