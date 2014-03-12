
def upper_case_convert(name):
     if name == None:
          return None
     if not isinstance(name, str):
          return name
          
     res = name.upper()
     return res

