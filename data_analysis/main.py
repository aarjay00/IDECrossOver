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


# log_list=parse_old_intelliJ_log("log")

# print len(log_list)

# log_list[0]

import s3_client
from log_parser.parse_logs_util import *
from action_type import ActionType
from log_analysis import *
from parse_log_entry import *

ActionType.load_files()

s3_client.downloadLogs()

# old_log=parse_old_intelliJ_log('log')

# parsed_old_log=parse_logs(old_log)



# print len(old_log),len(parsed_old_log)

users=parse_all_logs('Logs')

for user_name,user_logs in users.iteritems():
    parsed_user_logs=parse_logs(user_logs)
    log_analysis(parsed_user_logs,user_name)
