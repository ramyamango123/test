import re

# Match 0-50
input = "50"


pat = re.search("^([0-9]|([1-4][0-9])|50)$", input)

#print pat

# 1-31

pat1 = re.search("([1-9]|[1-2][0-9]|30|31\/(1|3|5|7|8|10|12))|([1-9]|[1-2][0-9]|30)\/(4|6|9|11)|[1-9]|[1-2][0-8]|19\/2", input)

days_1_31 = "([1-9]|[1-2][0-9]|30|31)"
days_1_30 = "([1-9]|[1-2][0-9]|30)"
days_1_28 = "([1-9]|[1-2][0-8]|19)"

month_31_days = "(1|3|5|7|8|10|12)"
month_30_days = "(4|6|9|11)"
month_28_days = "(2)"

year = "20[0-9][0-9]"

# format: 31/1/2012

day_month = "(" + days_1_31 + "\/" + month_31_days + ")" + "|" + \
            "(" + days_1_30 + "\/" + month_30_days + ")" + "|" + \
            "(" + days_1_28 + "\/" + month_28_days + ")"
pattern = "(" + day_month + ")" + "\/" + year

print pattern
print re.search("^" + pattern + "$", "29/2/2012")

 