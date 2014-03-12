import pprint
p = pprint.pprint 

class Employee:
    # __class__ = "Employee"
    version = 1.2
    count = 0
    __count__ = 0
    
    def __init__(self, name = "ram"):
        self.name = name
        Employee.count += 1
        self.count += 1
        __count__ = 1
        print "---", self.count
            
    def methodtwo(self):
        print self
        print "result"
        Employee.version = 5.0
        p(Employee.version)
        obj3 = Employee("test")
        
    
    @staticmethod
    def methodone():
         print Employee.version
         print "method one"
         
    @classmethod
    def methodthree(self):
         print self
         print self.version
         print "method one"
        

obj1 = Employee("rose")
obj2 = Employee("pink")

   
print obj1.__class__
print obj2.__class__

p(obj1.name)
p(obj2.name)

obj2.methodtwo()
obj1.methodtwo()
print "outside class", Employee.version
print "obj1.version", obj1.version

print Employee.methodone()
print Employee.methodthree()
print obj1.methodthree()

        

    