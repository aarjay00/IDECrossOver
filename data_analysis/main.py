# import parse_data
#
# from file_open_analysis import *
# from hotkey_analysis import *
#
# command_list,document_change_list=parse_data.parse("eclipse/V1/user3")
# # for c in command_list:
# #     print c
# print len(command_list),len(document_change_list)
#
# # file_open_analysis(command_list,document_change_list)
# hotkey_analysis(command_list)


from parse_logs import *
from s3_client import *

# log_list=parse_old_intelliJ_log("log")

# print len(log_list)

# log_list[0]

from parse_logs import *
from parse_log_entry import *

from action_type import ActionType

ActionType.load_files()

user_logs=parse_all_logs('Logs')

log_collec=[]
for user_name,user_log in user_logs.iteritems():
    log_collec.extend(user_log)

for log_entry in log_collec:
    parse_log_entry(log_entry)