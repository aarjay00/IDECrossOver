import json
from util import *

def parse_old_intelliJ_log(file_path):

    log_list=[]
    file_fd=open(file_path,'r')

    for line in file_fd.readlines():
        line=line[:-1]
        if(line[0]=='-'):
            index=line.find("{")
            line=line[index:]
        try:
            log_list.append(json.loads(line))
        except:
            print "Could not load as Json",line
    return log_list

def decode_log_file(file_data):
    encodings=['utf-8','cp1252']
    for encoding in encodings:
        try:
            return file_data.decode(encoding)
        except UnicodeDecodeError:
            continue

def parse_intellij_log(file):

    print "parsing - ",file
    fileFD = open(file,'r')
    file_data=decode_log_file(fileFD.read())
    file_data_lines=file_data.split('\n')[1::2]
    file_data_clean_lines=[file_data_line.strip() for file_data_line in file_data_lines]
    file_data_clean_lines=[file_data_clean_line[file_data_clean_line.find("{"):] for file_data_clean_line in file_data_clean_lines]

    log_list=[]
    for file_data_clean_line in file_data_clean_lines:
        try:
            log_list.append(json.loads(file_data_clean_line))
        except ValueError:
            # pass
            print "Could not load as Json",file_data_clean_line

    return log_list

def parse_user_logs(dir_name):
    file_list=get_all_files(dir_name)
    user_log_list=[]
    for file in file_list:
        user_log_list.extend(parse_intellij_log(file))
    return user_log_list

def parse_all_logs(log_dir_path):
    user_dir_paths=get_all_dir(log_dir_path)
    user_logs={}
    for user_dir_path in user_dir_paths:
        user_logs[user_dir_path] = parse_user_logs(user_dir_path)
    return user_logs