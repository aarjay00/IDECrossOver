import parse_data
from file_open_analysis import *


command_list,document_change_list=parse_data.parse("eclipse/V1/user2")
# for c in command_list:
#     print c
print len(command_list),len(document_change_list)

file_open_analysis(command_list,document_change_list)