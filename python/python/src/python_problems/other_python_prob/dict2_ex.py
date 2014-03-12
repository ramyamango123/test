
i = [34, 56, 89, 98]
s = [11, 44, 77, 99]
i.append(s)
d = {}
distance_input = input("enter a distance:")

if d.has_key(distance_input):
        d[distance_input].append(i)
else:
        new_list = list()
        new_list.append(i)
        d[distance_input] = new_list
print d