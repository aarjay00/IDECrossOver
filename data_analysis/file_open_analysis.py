def get_file_open_commands(command_list):
    return filter(lambda x: x["command_type"]=="FileOpenCommand" and x['filePath']!='null', command_list)

def file_open_analysis(command_list,document_change_list):
    file_open_commands=get_file_open_commands(command_list)
    print len(file_open_commands)


    file_activity_list=[file_activity(command_list,file_open_commands,file_command_index) for file_command_index in range(0,len(file_open_commands))]

    file_activity_list_merge=merge_file_activity_list(file_activity_list)


    file_accessed=file_access_num(file_activity_list)

    print len(file_activity_list),len(file_activity_list_merge),len(file_accessed)

    for f in file_activity_list_merge:
        print "start-",command_list[f[0]]['log_num'],"end-",command_list[f[1]]['log_num']
        print "mouse-",mouse_activity_from_file_activity(f,command_list)
        print "time-",f[3]
        print "file-",file_accessed.index(f[4])
        e=edit_actitvity_from_file_activity(f,command_list,document_change_list)
        print "edit - ",e

def file_activity(command_list,file_commands,file_command_index):

    start_index=file_commands[file_command_index]['command_num']
    try:
        end_index=file_commands[file_command_index+1]['command_num']
    except:
        end_index=len(command_list)-1
    return (start_index,end_index,end_index-start_index,command_list[end_index]['timestamp']-command_list[start_index]['timestamp'],command_list[start_index]['filePath'])


def merge_file_activity_list(file_activity_list):
    prev_file="null"
    file_activity_list_merge=[]
    for file in file_activity_list:
        if(file[4]!=prev_file):
            file_activity_list_merge.append(file)
        else:
            prev_file_activity=file_activity_list_merge[-1]
            del file_activity_list_merge[-1]
            new_file_activity=(prev_file_activity[0],file[1],prev_file_activity[2]+file[2],prev_file_activity[3]+prev_file_activity[3],prev_file_activity[4])
            file_activity_list_merge.append(new_file_activity)
        prev_file=file[4]

    return file_activity_list_merge

def file_access_num(file_activity_list):
    return list(set([file[4] for file in file_activity_list]))

def mouse_activity_from_file_activity(file_activity,command_list):
    start_index=file_activity[0]
    end_index=file_activity[1]

    return len(filter(lambda x : x['command_type']=='MoveCaretCommand',command_list[start_index:end_index+1]))

def edit_actitvity_from_file_activity(file_activity,command_list,document_change_list):
    start_index = command_list[file_activity[0]]['log_num']
    end_index = command_list[file_activity[1]]['log_num']

    edit_num=0
    for document_change in document_change_list:
        if(int(document_change['log_num'])>=int(start_index) and int(document_change['log_num'])<=int(end_index)):
            edit_num+=1
    return  edit_num
