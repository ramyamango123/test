# No of occurances of unique words in a file
import re
file_open = open("unique_words_file.txt", 'r')

list1 = []

for eachline in file_open:
    #print eachline
       
    obj = re.compile('\.|\\n')
    compiled_lines = obj.sub('', eachline)
    split_words_list = compiled_lines.split()
    print "split_words_list", split_words_list 
    count_words = 0
    for eachword in split_words_list:
        #print eachword
        count_words += 1
        if eachword  not in list1:
            list1.append(eachword)
            
            
        total_count = ''
    for eachfileword in list1:
            unique_word_occurance_count = 0
            for eachsplitword in split_words_list:
                if eachfileword == eachsplitword:
                    unique_word_occurance_count += 1
            print "Total occurances of", eachfileword, "is", total_count + " " + str(unique_word_occurance_count)
                    
                    
    print list1
            
            
     #list1 =   ['Intuit', 'is', 'in', 'Menlo', 'park', 'Gazillion', 'company', 'san', 'mateo', 'was', 'a', 'very', 'good', 'I', 'like', 'to', 'work', 'there', 'again', 'and', 'againmy', 'close', 'friends', 'were', 'sharmila']  '''  
     
     #split_words_list ['Intuit', 'is', 'in', 'Menlo', 'park', 'Gazillion', 'company', 'is', 'in', 'san', 'mateo', 'Gazillion', 'was', 'a', 'very', 'good', 'company', 'I', 'like', 'to', 'work', 'there', 'again', 'and', 'againmy', 'close', 'friends', 'were', 'sharmila', 'and', 'sharmila']
   
    
file_open.close()