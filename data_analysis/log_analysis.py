from itertools import groupby
import time
import log_util

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
    # print len(compact_log_list),len(log_segment)
    return compact_log_list

def compact_document_changes(log_list):

    prev_log_entry=["","","",""]
    prev_log_entry_time=0


    compact_log_list=[]
    for log in log_list:
            curr_log_entry=log[0]
            curr_log_entry_time=int(log[1])
            try:
                if not (curr_log_entry[0]=='Document Changed' and curr_log_entry[0]==prev_log_entry[0])\
                        or curr_log_entry_time-prev_log_entry_time>10:
                    compact_log_list.append(log)
                elif curr_log_entry[1] != prev_log_entry[1]:
                    compact_log_list.append(log)
                else:
                    curr_log_entry[2] = str(int(curr_log_entry[2])+int(prev_log_entry[2]))
                    compact_log_list[-1]=(curr_log_entry,curr_log_entry_time)
                prev_log_entry = curr_log_entry
                prev_log_entry_time = curr_log_entry_time
            except:
                print curr_log_entry
                exit()
    return  compact_log_list


def compact_file_entry_exit(log_list):

    if(len(log_list)<2):
        return log_list


    compact_log_list=[log_list[0],log_list[1]]
    print len(log_list),

    for log_index,log in enumerate(log_list[2:]):

        log_index+=2


        curr_log_entry_1 = log[0]
        curr_log_entry_time_1 = int(log[1])

        curr_log_entry_2 = log_list[log_index-1][0]
        curr_log_entry_time_2 = int(log_list[log_index-1][1])

        prev_log_entry = log_list[log_index-2][0]
        prev_log_entry_time = int(log_list[log_index-2][1])

        if curr_log_entry_time_1-prev_log_entry_time > 10 :
            compact_log_list.append(log)
        elif not(curr_log_entry_1[0] == 'Exiting File Editor'
                 and curr_log_entry_2[0] == "Entering File Editor"
                 and prev_log_entry[0]=="Exiting File Editor"):
            compact_log_list.append(log)
        elif curr_log_entry_1[1]!=curr_log_entry_2[1] or curr_log_entry_2[1]!=prev_log_entry[1] :
            compact_log_list.append(log)
        else:
            del compact_log_list[-1]
            del compact_log_list[-1]
            prev_log_entry[3]=str(int(prev_log_entry[3])+int(curr_log_entry_1[3]))
            compact_log_list.append((prev_log_entry,prev_log_entry_time))

        prev_log_entry=curr_log_entry_1
        prev_log_entry_time=curr_log_entry_time_1

    print len(compact_log_list)
    return compact_log_list


def remove_action_type(action_type_name,log_segment):

    log_segment = filter(lambda x : not (len(x[0]) > 1 and x[0][0]=='Action' and x[0][1]==action_type_name) ,log_segment)

    return log_segment

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


def log_analysis(log_list,user_name):

    log_list_segmented=segment_log_list_2(log_list,300);

    log_list_segmented = [remove_action_type("misc",log_segment) for log_segment in log_list_segmented]

    log_list_segmented = [remove_action_type("file_action_edit",log_segment) for log_segment in log_list_segmented]

    log_list_segmented = [remove_action_type("file_action_non_edit",log_segment) for log_segment in log_list_segmented]



    log_list_compact=[compact_document_changes(log_segment) for log_segment in log_list_segmented]

    log_list_compact=[compact_log_segment(log_segment) for log_segment in log_list_compact]

    log_list_compact=[compact_file_entry_exit(log_segment) for log_segment in log_list_compact]


    accessed_file_list=[]
    log_list_compact=[normalize_file_name(log_segment,accessed_file_list) for log_segment in log_list_compact]

    accessed_project_list=[]
    log_list_compact=[normalize_project_name(log_segment,accessed_project_list) for log_segment in log_list_compact]

    log_util.print_log_segments(log_list_compact,"Segmented_"+user_name)