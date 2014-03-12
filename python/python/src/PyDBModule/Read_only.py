import MySQLdb
import pprint

p = pprint.pprint

# Open database connection
db = MySQLdb.connect("localhost","odbc","","test" )

# prepare a cursor object using cursor() method
cursor = db.cursor()

sql = "select * from employee where income < '%d'" % (5000)

try:
    result = cursor.execute(sql)
    results = cursor.fetchall()
    for i in results:
        id = i[0]
        fname = i[1]
        lname = i[2]
        age = i[3]
        sex = i[4]
        income = i[5]
        print "id = %d, fname = %s, lname = %s, age = %d, sex = %s, income = %d" % \
        (id, fname, lname, age, sex, income)

except:
    print "Unable to fetch data"

db.close()

    