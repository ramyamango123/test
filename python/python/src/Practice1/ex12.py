import re

# Match IP address each number can between 0-255
#ex:245:45:89:12

\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}

# Match between 1-10
[1-9]|10

# Match between 1- 50
[1-9]|([1-4][0-9])|50

# Match between 1-100
[1-9]|([1-9][0-9])|100

# Match date month year in the format: 31/1/2012

days_1_31 = "([1-9]|[1-2][0-9]|30|31)"
days_1_30 = "([1-9]|[1-2][0-9]|30)"
days_1_28 = "([1-9]|[1-2][0-8]|19)"

month_31_days = "(1|3|5|7|8|10|12)"
month_30_days = "(4|6|9|11)"
month_28_days = "(2)"

year = "20[0-9][0-9]"

day_month = "(" + days_1_31 + "\/" + month_31_days + ")" + "|" + \
            "(" + days_1_30 + "\/" + month_30_days + ")" + "|" + \
            "(" + days_1_28 + "\/" + month_28_days + ")"
pattern = "(" + day_month + ")" + "\/" + year

print pattern
print re.search("^" + pattern + "$", "29/2/2012")


