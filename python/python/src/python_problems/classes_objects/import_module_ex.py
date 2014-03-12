from print_module import pprint, pprint2
from print_module import Fruit

dress = "jeans"
vehicle = "scooty"
message = "sorry"

def add123():
    add = 0
    for i in range(4):
        add= add + i
    return add
    #pprint("sum of these numbers:" + str(add))
        

class module_test:
    def __init__(self, dress, vehicle, message, age=20):
        self.data1 = dress
        self.data2 = vehicle
        self.data3 = message
            
    def addition(self):
        add = 0
        for i in range(4):
            add= add + i
        pprint("sum of nums:" + str(add))
        #return add
        
        
    def age_verify(self,age):
        if age < 10:
            pprint ("you are too young:" + message)
        if age > 10:
            print "welcome"
            
        

'''instance1 = module_test('pant', 'auto', 'excuse')
result = instance1.addition()
print "result is :" , result'''

'''instance2 = module_test('pant1', 'auto1', message)
result_new = instance2.age_verify(7) 
print "result_new", result_new'''
    
    
        


    