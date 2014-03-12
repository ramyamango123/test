# File validity check

defaultFileName = "C:\\Users\\Ramya\\eclipseWorkspace\\Practice1\\ram.txt"

class File_validity:
    
    def __init__(self, filename = defaultFileName):
        self.data = filename
        
    def set_name(self, newFileName):
        self.data = newFileName
        
    def fileTest(self):
        try:
            fp = open(self.data, 'r')
            s = fp.readlines()
            print s
            
            return 1
        except IOError:
            return "File doens't exsit"
        except TypeError:
            return "Need a string/buffer"
        fp.close()
        
        
        
        
    
    