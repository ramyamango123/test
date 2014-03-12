fileDefaultName = "data.txt"

class File_test:
    def __init__(self, filename = fileDefaultName):
        self.data = filename
    def setFilename(self, newfilename):
        self.data = newfilename
        
    def file_validity_check(self):
        try:
            result = open( self.data, 'r')
            result.readlines()
            return 1
        except IOError:
            return "Error:can\'t find file or read data"
        except TypeError:
            return "Need string or buffer"
        result.close()
        