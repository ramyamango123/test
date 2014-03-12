# Open a file containing special characters, remove duplicate characters if any and count number of words, letters in it
import re
file_open = open("oct11_file.txt", 'r')
x = file_open.read()
print x

obj = re.compile('\,|\$|\#|\.|\!')
compiled_text = obj.sub('',x)
print compiled_text
split_words_list = compiled_text.split()
print split_words_list
count_words = 0
count_char_total = 0
for eachword in split_words_list:
    count_char = 0
    count_words += 1
    for eachchar in eachword:
        count_char += 1
        count_char_total += 1
    print "no of letters in", eachword, "is", count_char
        
print "Total no of words in the file", count_words
print "Total no of letters in the file", count_char_total
    
file_open.close()