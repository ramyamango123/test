import math

in_file = open("input.txt", 'r')

lat2_input = 4
long2_input = 7

def distanceCompute (lat1, lon1):
    dlon = long2_input - lon1
    dlat = lat2_input - lat1
    a = math.sin(dlat/2) * math.sin(dlat/2) + math.cos(lat1) * math.cos(lat2_input) \
                                       * math.sin(dlon/2) * math.sin(dlon/2)
    c = 2 * math.asin(min(1, math.sqrt(a)))
    distance = 6367.5 * c
    return distance

d = {}
for i in in_file:
    tokens = i.split(',')
    
    if len(tokens) > 1:
        lat1 = float(tokens[1])
    else:
        lat1 = 0
        
    if len(tokens) > 2:
        long1 = float(tokens[2])
    else:
        long1 = 0
    distance = distanceCompute(lat1, long1)
    #print lat
    
    if d.has_key(distance):
        d[distance].append(i)
        print d[distance], distance
    else:
        new_list = list()
        new_list.append(i)
        d[distance] = new_list
        print "key and distance", d[distance] , distance
        
        
k = d.keys()
print "keys", k
s = k.sort()
print "sorted keys", k
for key_distance in k:
    l1 = d[key_distance]
    print "l1 value", l1
    for i in l1:
        print "key_distance & i", key_distance, "km - ", i
#   print eachkey, d[eachkey]
    

in_file.close()
        