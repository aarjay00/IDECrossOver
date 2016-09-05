# -*- coding: utf-8 -*-
import os,django,sys
from datetime import datetime
import json
import cPickle as pickle


sys.path.append('/Users/aarjay/cnu_project_backend/backend')


os.environ.setdefault("DJANGO_SETTINGS_MODULE", "backend.settings")

django.setup()


from workpattern.models import *
from log_analysis import s3_client

from log_analysis.log_collection.log_collection import LogCollection
from log_analysis.log_parser.log_parser_intellij import LogParserIntellij
from log_analysis.log_representation.log_representation_basic import LogRepresentationBasic
from log_analysis import log_analysis
from log_analysis.action_type import ActionType
from log_analysis.log_analysis import log_analysis

ActionType.load_files()


DB_LOG_TIME_SLOT = 3600

def initialize_log_collection(log_dir_path,download_logs):

    if download_logs:
        s3_client.downloadLogs(log_dir_path)


    log_collection =  LogCollection(LogParserIntellij,LogRepresentationBasic)

    log_collection.load_log_collection(log_dir_path)

    return log_collection

def populate_users(user_list):

    for user in user_list:
        u = User()
        u.user_name = user.split('/')[-1]
        u.save()


def populate_logs(user_logs):


    for user_name,log_list in user_logs.iteritems():

            user_name = user_name.split('/')[-1]
            user_object = User.objects.get(user_name=user_name)
            start_time  = datetime.fromtimestamp(int(log_list[0]['timeStamp']))
            db_log_entry = []
            for log in log_list:

                time  = datetime.fromtimestamp(int(log['timeStamp']))
                delta = time - start_time

                if(delta.seconds > DB_LOG_TIME_SLOT):
                    db_log_entry.append(log)
                    content_raw = pickle.dumps(db_log_entry)
                    try:
                        content_raw.decode('utf-8')
                    except:
                        content_raw = content_raw.decode('cp1252').encode('utf-8')

                    log_entry = Log()
                    log_entry.time_begin = start_time
                    log_entry.time_end = time
                    log_entry.content_raw = content_raw
                    log_entry.user_id = user_object
                    try:
                        log_entry.save()
                    except Exception as e:
                        print len(db_log_entry)
                        for d in db_log_entry:
                            print d
                        print e
                        exit()
                    start_time = time
                    db_log_entry = []
                else:
                    db_log_entry.append(log)


def populate_activity(dev_activity_dict):

    for user_name,user_activity in dev_activity_dict.iteritems():

        user_name = user_name.split('/')[-1]

        user = User.objects.get(user_name = user_name)
        for activity in user_activity:

            a = Activity()
            a.user_id = user
            a.category = activity[0]
            a.time_begin = datetime.fromtimestamp(int(activity[1]))
            a.time_end = datetime.fromtimestamp(int(activity[2]))
            a.save()



s3_client.downloadLogs('log_analysis/Logs/')


log_collection = initialize_log_collection('log_analysis/Logs/',False)

log_collection.convert_log_collection_representation(log_collection_name='log_collection_default',log_collection_represented_name='log_repr')


dev_activity_dict={}
for user_name,user_logs in log_collection.log_repr.iteritems():

     user_activity=log_analysis(user_logs,user_name)

     user_name=user_name.split('/')[-1].strip()

     dev_activity_dict[user_name] = user_activity


user_logs = log_collection.log_collection_default

user_list = user_logs.keys()

populate_users(user_list)

populate_logs(user_logs)

populate_activity(dev_activity_dict)