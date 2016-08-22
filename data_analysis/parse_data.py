from os import walk,path
import time
import xmltodict
import unicodedata

def convert_epoch(timestamp_epoch):
    print time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(float(timestamp_epoch)))

def get_all_files(file_path):
    files=[]
    for(dirpath,dirnames,filenames) in walk(file_path):
        files.extend([path.join(file_path,filename) for filename in filenames])
    return files

def parse_eclipse(file):
    print file
    with open(file,"r") as fileFD:
        return xmltodict.parse(fileFD.read())

def parse_xml(xml_file,command_num):
    xml_file=xml_file['Events']
    start_timestamp=xml_file['@startTimestamp']
    commands=xml_file['Command']
    command_list=[]
    for command in commands:
        command_dict={}
        command_dict["command_num"]=command_num
        command_dict['command_type']=command['@_type'] # Command type
        command_dict['timestamp']=float(command['@timestamp'])/1000+float(start_timestamp)/1000 # Command timestamp
        extra_values=filter(lambda val: val not in ['@timestamp',"@_type"],command.keys())
        for values in extra_values:
            command_dict[values]=command[values]
        command_list.append(command_dict)
        command_num+=1
    return command_list

def parse_files(files,log_type):
    file_list=[]
    if(log_type=="eclipse"):
        for file in files:
            try:
                file_list.append(parse_eclipse(file))
            except:
                continue
    command_list=[]
    for file in file_list:
        command_list.extend(parse_xml(file,len(command_list)))
    return command_list
# file_path=raw_input()

def parse(file_path):
    file_path_list=get_all_files(file_path)
    command_list=parse_files(file_path_list,"eclipse")
    return command_list
