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