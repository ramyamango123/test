 
import pprint, sys

import re
file_open = open("multiple_lines.txt", 'r')

x = file_open.readlines()

pprint.PrettyPrinter().pprint(x)

file_open.close()