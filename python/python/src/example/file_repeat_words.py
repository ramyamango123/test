import pprint

file_open = open("repeatwords.txt", 'r')
x = file_open.read()
dict = {}
count = 0
for i in x :
    splitted_words = i.split()
    pprint.pprint(splitted_words)
#    join_sentences = "".join(splitted_words)
#    print join_sentences
#    result= join_sentences.split(" ")
#    print result
    for j in range(len(splitted_words)):
        key = splitted_words[j]
        print key
        if dict.has_key(key):
            dict[key] += 1
        else:
            dict[key] = 1
            
  
# or
    for j in splitted_words:
        if dict.has_key(j):
            dict[j] += 1
        else:
            dict[j] = 1
print dict      
    
    
       
file_open.close()