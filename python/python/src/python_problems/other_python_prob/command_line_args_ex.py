import sys

print "len(sys.argv)", len(sys.argv)
print "sys.argv: ", sys.argv

if (len(sys.argv) > 2):
    result = int(sys.argv[1]) + int(sys.argv[2])
    print "Adding sys.argv[1] and sys.argv[2]: ", result
else:
    print "Not enough command line arguments !"
    