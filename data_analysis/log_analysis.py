from itertools import groupby
import time
import util

def sort_logs(log_list):

    sorted_log_list=sorted(log_list,key = lambda x: int(x[1]))
    return sorted_log_list


def segment_log_list(log_list,time_period=900):

    log_list=sort_logs(log_list)
    start_time=int(log_list[0][1])
    end_time=int(log_list[-1][1])

    log_divided=[]
    for time_key,log_group in groupby(log_list,lambda x : (int(x[1])-start_time)/time_period ):
        log_group=list(log_group)
        print time.ctime(time_key*time_period+start_time), len(log_group)
        log_divided.append(log_group)
        # print len(log_group)
        # exit()
    return log_divided

def segment_log_list_2(log_list,time_period):

    log_list=sort_logs(log_list)

    start_time=int(log_list[0][1])

    log_divided=[]
    log_segment=[]

    for log in log_list:
        log_time=int(log[1])
        if log_time-start_time>=time_period:
            start_time=log_time
            if log_segment!=[]:
                log_divided.append(log_segment)
            log_segment=[]
        else:
            log_segment.append(log)
    if log_segment!=[]:
        log_divided.append(log_segment)

    try:
        log_divided.index([])
        for log in log_divided:
            print log
        exit()
    except:
        pass

    return log_divided

def compact_log_segment(log_segment):
    prev_log_entry=''
    prev_log_entry_time=0
    compact_log_list=[]
    for log in log_segment:
        # print log[0],prev_log_entry,log[0]==prev_log_entry,int(log[1])-prev_log_entry_time
        if(log[0]==prev_log_entry and (int(log[1])-prev_log_entry_time)<10):
            prev_log_entry_time = int(log[1])
            continue
        compact_log_list.append(log)
        prev_log_entry=log[0]
        prev_log_entry_time=int(log[1])
    print len(compact_log_list),len(log_segment)
    return compact_log_list

def normalize_file_name(log_segment,accessed_file_list):

    for log in log_segment:

        if log[0][0] not in ['Active File','Exiting File Editor','Entering File Editor']:
            continue

        if(log[0][1] not in accessed_file_list):
            accessed_file_list.append(log[0][1])
        log[0][1] = "File"+str(len(accessed_file_list))

    return log_segment

def normalize_project_name(log_segment,accessed_project_list):


    for log in log_segment:

        if(log[0][0] != "Active Project"):
            continue
        if(log[0][1] not in accessed_project_list):
            accessed_project_list.append(log[0][1])
        log[0][1]="Project"+str(len(accessed_project_list))

    return log_segment


def get_log_time(log_entry):

    epoch = int(log_entry[1])

    time_tuple = time.gmtime(epoch)

    time_readeable = time.strftime("%m:%d::%H:%M:%S",time_tuple)

    full_time_readeable = time.ctime(epoch)

    return time_readeable

def print_log_segments(log_list_segmented,dir_path):

    util.create_directory(dir_path)

    for ind,log_segment in enumerate(log_list_segmented):

        with open(dir_path+"/"+str(ind),'w') as fd:
            fd.write("Time Start-"+get_log_time(log_segment[0])+"\n")
            time_start_epoch=int(log_segment[0][1])
            for log_entry in log_segment:
                line=str(int(log_entry[1])-time_start_epoch)+"-"+' '.join(log_entry[0])
                line=util.encode_data(line)
                fd.write(line+"\n")
            fd.write("Time End-"+get_log_time(log_segment[-1])+"\n")
def log_analysis(log_list,user_name):


    log_list_segmented=segment_log_list_2(log_list,300);

    log_list_compact=[compact_log_segment(log_segment) for log_segment in log_list_segmented]

    accessed_file_list=[]
    log_list_compact=[normalize_file_name(log_segment,accessed_file_list) for log_segment in log_list_compact]
    accessed_project_list=[]
    log_list_compact=[normalize_project_name(log_segment,accessed_project_list) for log_segment in log_list_compact]

    print_log_segments(log_list_compact,"Segmented_"+user_name)
