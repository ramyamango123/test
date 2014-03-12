import re;

p = "//html/body/table[4]/tbody/tr/td/div[1]/p/table/tbody/tr"

#q = "//html/body/table[4]/tbody/tr/td"

pattern = re.search("//html/body/table\[4\]/tbody/tr/td/div((\[[1-9]\])?)/p/table", p)

print pattern.group(1)