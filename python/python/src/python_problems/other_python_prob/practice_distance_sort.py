import math
d = {}
file_open = open("sort_test.txt", 'r')
lines = file_open.readlines()
print len(lines)
#exit()
for i in range(len(lines)):
    #print i, lines[i]
    x = lines[i].split(',')
    lat1 = float(x[1])
    lon1 = float(x[2])
    #print "latitude from file:", x[1]
    #print "longitude from file::", x[2]
    lat2 = input("Enter latitude:")
    lon2 = input("Enter longitude:")
    dlon = lon2 - lon1
    dlat = lat2 - lat1
    print "dlon and dlat are:", dlon, dlat
    
    # Measure the distance
    RadiusOfEarth = 6367.45
    a = math.sin(dlat/2) * math.sin(dlat/2) + \
        (math.cos(lat1)* math.cos(lat2)) * \
        (math.sin(dlon/2) * math.sin(dlon/2))
    c = 2 * math.asin(min(1, math.sqrt(a)))
    distance = RadiusOfEarth * c
    print "distance:", distance
    d[distance] = lines[i]
print d
distance = d.keys()
distance.sort()
print distance
for key_distance in distance:
    print key_distance, d[key_distance]

    
file_open.close()
