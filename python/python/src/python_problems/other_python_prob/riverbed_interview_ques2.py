#
# Have a sample input string for testing.
#
input_str = " this is a   python's program"

#
# This is the desired output string
#
#output_str = "4 2 1 8 7"

#
# Compute numbers of letters in each word in the passed in sentence,
#
def compute_letter_count (sentence):
    
    #
    # split the sentence into separate words.
    #
    words_list = sentence.split()
    print words_list
    output_str = ""
    
    #
    # Iterate through each word in the list.
    #
    for each_word in words_list:
        count = 0
        
        #
        # Iterate through each letter in the word to get the count of letters.
        #
        for each_char in each_word:
            count = count + 1

        #
        # Form the output stirng of letter count as desired.
        #
        output_str = output_str + " " + str(count)

    #
    # Return the output string formed.
    #
    return output_str

#
# Call the method does computes letter count of each word in a sentence and print
# the returned output string.
#
out = compute_letter_count(input_str)
print out