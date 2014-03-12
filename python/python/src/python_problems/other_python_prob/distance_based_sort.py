#!/usr/bin/env python
# shebang
# SCRIPTING EXERCISE
# ##################
#
# Given a ASCII CSV file where Column 1 = ID, Column 2 = Latitude,
# Column 3 = Longitude:
#
# 0001,+36.96571110,-121.01632475
# 0002,+36.96656853,-120.64879434
# 0003,+36.99951561,-121.36875809
# 0004,+36.99917983,-121.37362497
# 0005,+36.98834456,-121.38186654
# 0006,+36.98807493,-121.38197663
# 0007,+36.98762167,-121.38208113
# 0008,+36.98303615,-121.38192810

# Please write a Python script which takes a latitude, longitude, and CSV filename
# as inputs, and outputs the CSV rows sorted based on distance from the input
# latitude and longitude. For distance calculations, please refer to the Haversine
# Formula.  
#
# Haversine Formula (from R.W. Sinnott, "Virtues of the Haversine", Sky and
# Telescope, vol. 68, no. 2, 1984, p. 159):
#
# dlon = lon2 - lon1
# dlat = lat2 - lat1
# a = sin^2(dlat/2) + cos(lat1) * cos(lat2) * sin^2(dlon/2)
# c = 2 * arcsin(min(1,sqrt(a)))
# d = R * c

#
# We need math module to compute the distance.
#
import math

#
# getInputData
#
# Get origin location latitude, longitude and input file (csv) name from the user
#
def getInputData ():
    global origin_latitude
    global origin_longitude
    global input_csv_file
    global distance_dict

    #
    # Ask for latitude, use 0 as default.
    #
    try:
        origin_latitude = input("Enter Origin latitude  (float)[Default: 0.0]> ")
    except:
        origin_latitude = 0.0

    #
    # Ask for longitude, use 0 as default.
    #
    try:
        origin_longitude = input("Enter Origin longitude (float)[Default: 0.0]> ")
    except:
        origin_longitude = 0.0

    #
    # Ask for the csv file name
    #
    try:
        csv_file_input = \
            raw_input("Enter a filename[Default: latitude_longitude_input.csv]> ")
        input_csv_file = open(csv_file_input, 'r')
    except:

        #
        # Use a default name, for testing convenience.
        #
        csv_file_input = "latitude_longitude_input-1.csv"
        try:
            input_csv_file = open(csv_file_input, 'r')
        except IOError:

            #
            # Exit if the file is not readable.
            #
            print "Cannot open " + csv_file_input + "for reading"
            exit()

    #
    # Initialize a dictionary used to store latitude and longitude stored in the
    # file.
    #
    distance_dict = { }

#
# distanceCompute
#
# Compute distance between two geographical location of earth, specified as
# set of latitude and longitudes.
#
# We use "Haversine Formula" to compute the distance
# 
# Return the computed distance to the caller (in km)
# 
def distanceCompute (lat1, long1, lat2, long2):

    #
    # Radius of earth is a constant.
    #
    RadiusOfEarth = 6367.45 # km

    dlon = long2 - long1
    dlat = lat2 - lat1

    a = math.sin(dlat/2) * math.sin(dlat/2) + \
        (math.cos(lat1) * math.cos(lat2)) * \
        (math.sin(dlon/2) * math.sin(dlon/2))
    c = 2 * math.asin(min(1, math.sqrt(a)))
    distance = RadiusOfEarth * c

    #
    # Return the computed distance to the caller. (in km)
    #
    return distance

#
# sortBasedOnDistance
#
# Sort the points data in the file, based on the distance from the origin.
#
# We do this using a dictionary. Key for this dictionary is the distance between
# origin location and the locations specified in the csv file. For each distance
# computed, we store in the dictionary with computed distance as the key and the
# corresponding point (from csv file) as the value. 
#
# This enables later, to walk the dictionary based on keys that are sorted and
# hence print corresponding points (values in the dictionary) in the desired
# sorted order.
#
# Note: There could be multiple points which are at exactly same distance from
# the inputted origin location. In order to handle this, in the dictionary
# value, we store the points in the form of a list, with all such locations
# accumulated.
#
def sortBasedOnDistance ():
    global distance_dict
    global origin_latitude
    global origin_longitude
    global input_csv_file

    #
    # Iterate through each line in the file.
    #
    for eachline in input_csv_file:

        #
        # Get rid of any trailing '\n'.
        #
        eachline = eachline.rstrip()

        #
        # Split the csv formatted line into index, latitude and longitude.
        #
        tokens = eachline.split(':')

        #
        # We need at least the index.
        #
        if len(tokens) < 2 :
            continue

        #
        # Check if we have the latitude specified, use 0 otherwise.
        #
        if len(tokens) > 1:
            latitude_from_file = float(tokens[1])
        else:
            latitude_from_file = 0

        #
        # Check if we have the longitude specified, use 0 otherwise.
        #
        if len(tokens) > 2:
            longitude_from_file = float(tokens[2])
        else:
            longitude_from_file = 0

        #
        # Compute the distance between inputted origin point and the specified
        # point in the file.
        #
        distance = distanceCompute(origin_latitude, origin_longitude,
                                   latitude_from_file, longitude_from_file)

        #
        # Now, store the point inside a dictionary, with distance as the key.
        # This gives us ability to sort them based on distance (the key) later
        # on.
        #
        # **** There could be more than one point at the same distance from the
        #      origin. Hence store the points inside a list.
        #
        if not distance_dict.has_key(distance):

            #
            # We are seeing this distance for the first time. Create a new list
            # with eachline as its only element and store it inside the
            # dictionary at key 'distance'
            #
            distance_dict[distance] = [ eachline ]
        else:

            #
            # We have already come across this distance. Append this new
            # location to the existing list in the dictionary at key 'distance'.
            #
            distance_dict[distance].append(eachline)
            print distance_dict[distance]
#
# displayOutput
#
def displayOutput ():
    global distance_dict

    #
    # Display origin latitude and longitude
    #
    print "\nOrigin (Latitude, Longitude): " + str(origin_latitude) + ", " + \
                                               str(origin_longitude) + "\n"
    print "   Distance         Index,Latitude,Longitude"
    print "--------------------------------------------------"

    #
    # Get the keys from the distance_dict. Keys are basically the distance from
    # origin.
    #

    #for i in distance_dict:
     #   distance_keys.append(i)
    #distance_keys.sort()
    
    
    distance_keys = distance_dict.keys()

    #
    # Sort the distances.
    #
    distance_keys.sort()

    # 
    # Walk through each distance and display the corresponding latitude and
    # longitude.
    #
    for key_distance in distance_keys:

        #
        # distance_dict[] contains a list of (latitude, longitude) points.
        #
        l1 = distance_dict[key_distance]

        #
        # Print each element in the list. Distance computed is in km, btw.
        #
        for i in l1:
            print key_distance, "km, ", i

        #
        # Separate points with different distance by a blank line for easier
        # readability.
        #
        print

#
# finish
#
def finish ():
    global input_csv_file

    #
    # Close the file that was opened earlier.
    #
    input_csv_file.close()

    exit()

#
# main method
#
def main ():

    #
    # Get origin latitude, longitude and csv file name from the user.
    #
    getInputData()

    #
    # Find the distance from the origin and sort it.
    #
    sortBasedOnDistance()

    #
    # Display latitude and longitude data from the file sorted on distance from
    # the inputted origin location.
    #
    displayOutput()

    #
    # Do clean up and exit
    #
    finish()

#
# This is the main entry point.
#
main()
