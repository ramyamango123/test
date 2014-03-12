data = '''A tree is a perennial woody plant. It most often has many secondary branches 
supported clear of the ground by a single main stem or trunk.
 Compared with most other plants, trees are long-lived, some reaching several thousand years old 
growing up to 115 m (379 ft) tall. Trees have been in existence on the Earth for 370 million years 
and are found growing almost everywhere from the Arctic to the equator. 
Trees are not a taxonomic group but are a number of plant species that have independently 
adopted a woody trunk and branches as a way to tower above other plants in full sunlight. '''

fw = open("line_word.txt", 'w')
fw.write(data)
fw.close()

in_file = open("line_word.txt", 'r')



#in_file = open("C:\\Users\\Ramya\\QAdata_as_of_Feb172011\\python_problems\\other_python_prob\\Data.txt", 'r')

count = 0
total = 0

for eachline in in_file:
    #print eachline
    count += 1
    for eachword in eachline:
        #print eachword
        total += 1
print count
print total