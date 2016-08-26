import json

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
            print line
    return log_list


def decode_log_file(file_data):
    encodings=['utf-8','cp1252']
    for encoding in encodings:
        try:
            return file_data.decode(encoding)
        except UnicodeDecodeError:
            continue

def parse_intellij_logs(file):
    fileFD = open(file,'r')
    file_data=decode_log_file(fileFD.read())
    print len(file_data)
    file_data_lines=file_data.split('\n')[1::2]
    print len(file_data_lines)
    file_data_clean_lines=[file_data_line.strip()[6:] for file_data_line in file_data_lines]
    print len(file_data_clean_lines)
    log_list=[]
    for file_data_clean_line in file_data_clean_lines:
        try:
            log_list.append(json.loads(file_data_clean_line))
        except ValueError:
            continue
    return log_list