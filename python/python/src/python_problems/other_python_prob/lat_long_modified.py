import math

d = {}

lat2_input = input("Enter a latitude value:")
long2_input = input("Enter a longitude value:")
#f1 = "lat_long_input.txt"

RadiusOfEarth = 6367.45 # km

loop = 1
while loop == 1:
    try:
        cvsfile_input = raw_input("Enter a valid CSV filename:")
        in_file = open(cvsfile_input, 'r')
        loop = 0
    except IOError:
        continue

def distanceCompute (lat1, lon1):
    dlon = long2_input - lon1
    dlat = lat2_input - lat1
    a = math.sin(dlat/2) * math.sin(dlat/2) + math.cos(lat1) * math.cos(lat2_input) \
                                       * math.sin(dlon/2) * math.sin(dlon/2)
    c = 2 * math.asin(min(1, math.sqrt(a)))
    d = RadiusOfEarth * c
    return d

for i in in_file:
    #i.chomp()
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
    else:
        new_list = list()
        new_list.append(i)
        d[distance] = new_list
        
    #d[distance] = i


#for eachline in d.keys().sort():
   # print eachline, d[eachline]
k = d.keys()
k.sort()
for key_distance in k:
    l1 = d[key_distance]
    for i in l1:
        print key_distance, "km - ", i
#   print eachkey, d[eachkey]
    

in_file.close()