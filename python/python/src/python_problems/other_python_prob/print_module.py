

def pprint (result):
    import pprint
    pprint.PrettyPrinter().pprint(result)

def pprint2 (result):
    import pprint
    pprint.PrettyPrinter().pprint(result)


class Fruit:
    def __init__(self, name):
        self.fruit_name = name
    def add(self):
        print "In add " + self.fruit_name