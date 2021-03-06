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
from log_analysis import *
from log_parser.parse_logs_util import *
from log_representation.parse_log_entry import *

from log_collection.log_collection import LogCollection

from log_parser.log_parser_intellij import LogParserIntellij

from log_representation.log_representation_basic import LogRepresentationBasic

import graph

ActionType.load_files()

s3_client.downloadLogs()

# old_log=parse_old_intelliJ_log('log')

# parsed_old_log=parse_logs(old_log)



# print len(old_log),len(parsed_old_log)

# users=parse_all_logs('Logs')


log_collection = LogCollection(LogParserIntellij,LogRepresentationBasic)

log_collection.load_log_collection('Logs')

log_collection.convert_log_collection_representation(log_collection_name='log_collection_default',log_collection_represented_name='log_repr')


dev_activity_dict={}

for user_name,user_logs in log_collection.log_repr.iteritems():

     user_activity=log_analysis(user_logs,user_name)

     user_name=user_name.split('/')[-1].strip()

     dev_activity_dict[user_name] = user_activity


graph.create_all_dev_activity_graph(dev_activity_dict)