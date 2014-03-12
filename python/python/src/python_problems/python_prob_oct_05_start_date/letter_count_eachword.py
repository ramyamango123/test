 #This is the desired output string
#
#output_str = "4 2 1 8 7"

#
# Compute numbers of letters in each word in the passed in sentence,
 
p = ['this', 'is', 'a', "python's", 'program']
output_str = ''
for eachword in p:
    print eachword
    word_count = 0
    for  i in eachword:
        word_count += 1
    output_str = output_str + " " + str(word_count)
print output_str
        