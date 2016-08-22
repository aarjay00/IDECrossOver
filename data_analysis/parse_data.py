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
        data=fileFD.read()
        try:
            return  xmltodict.parse(data)
        except:
            print "issues",file
            try:
                data+="</Events>"
                return xmltodict.parse(data)
            except Exception:
                print "issues still",file
                raise Exception

def parse_xml(xml_file,command_num,document_change_num):
    xml_file=xml_file['Events']
    start_timestamp=xml_file['@startTimestamp']
    try:
        print "eee",xml_file.keys()
        commands=xml_file['Command']
        try:
            document_changes=xml_file['DocumentChange']
        except:
            document_changes=[]
    except:
        print "aaaa",xml_file.keys()
        print xml_file["Annotation"]
        exit()
    command_list=[]
    for command in commands:
        command_dict={}
        command_dict['log_num']=command['@__id']
        command_dict["command_num"]=command_num
        command_dict['command_type']=command['@_type'] # Command type
        command_dict['timestamp']=float(command['@timestamp'])/1000+float(start_timestamp)/1000 # Command timestamp
        extra_values=filter(lambda val: val not in ['@__id','@timestamp',"@_type"],command.keys())
        for values in extra_values:
            command_dict[values]=command[values]
        command_list.append(command_dict)
        command_num+=1
    document_change_list=[]
    for document_change in document_changes:
        document_dict={}
        document_dict['log_num']=document_change['@__id']
        document_dict['document_change_num']=document_change_num
        document_dict['action_type']=document_change['@_type']
        document_dict['offset']=document_change['@offset']
        document_dict['timestamp']=document_change['@timestamp']
        extra_values=filter(lambda x : x not in ['@__id','@_type','@offset','@timestamp'],document_change.keys())
        for values in extra_values:
            document_dict[values]=document_change[values]
        document_change_list.append(document_dict)
        document_change_num += 1
    return (command_list,document_change_list)

def parse_files(files,log_type):
    file_list=[]
    if(log_type=="eclipse"):
        for file in files:
            try:
                file_list.append(parse_eclipse(file))
            except:
                continue
    command_list=[]
    document_change_list=[]
    for file in file_list:
        print files[file_list.index(file)]
        c,d=parse_xml(file,len(command_list),len(document_change_list))
        command_list.extend(c)
        document_change_list.extend(d)
    return command_list,document_change_list
# file_path=raw_input()

def parse(file_path):
    file_path_list=get_all_files(file_path)
    command_list=parse_files(file_path_list,"eclipse")
    return command_list
