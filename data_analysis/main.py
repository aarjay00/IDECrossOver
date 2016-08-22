import parse_data


command_list=parse_data.parse("eclipse/V1/user2")
for c in command_list:
    print c
print len(command_list)