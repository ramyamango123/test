
for i in range(1, int(result)):
        r1 = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr[i]"
      
        for j in range(1,4):
                cmd = "//html/body/table[4]/tbody/tr/td/div[2]/p/table/tbody/tr/td[" + str(i) + "]"
                text = str(sel.get_text(cmd))
                print text