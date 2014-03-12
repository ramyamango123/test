import sys
text_output = 0
for i in sys.argv:
    if i == "-t":
        text_output = 1
print text_output