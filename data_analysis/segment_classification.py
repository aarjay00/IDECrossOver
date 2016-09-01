import graph

def is_log_entry_debug_action(log_entry):

    if log_entry[0] == 'Action' and log_entry[1] == 'debugging':
        return True
    if log_entry[0] == 'Action' and log_entry[1] == 'run':
        return True
    if (log_entry[0] == 'Entered Tool' or log_entry[0] == 'Exited Tool') and 'debug' in  log_entry[1].lower() :
        return True

    return False


def is_log_entry_edit_action(log_entry):

    if log_entry[0]=='Document Changed' :
        return True

    return False

def is_log_entry_understand_action(log_entry):

    if log_entry[0]=='Active File' :
        return True

    if log_entry[0]=='Exiting File Editor' :
        return True

    if log_entry[0]=='Entering File Editor' :
        return True

    if log_entry[0]=='Action' and log_entry[1]=='file_navigation':
        return True

    if log_entry[0]=='Action' and log_entry[1]=='file_action_edit':
        return True

    if log_entry[0]=='Action' and log_entry[1]=='file_action_non_edit':
        return True



def get_debugging_info(log_segment):

    debugging_time = 0
    debug_action_num = 0

    for idx,log in enumerate(log_segment):
        log_entry=log[0]
        log_entry_timestamp = int(log[1])

        if is_log_entry_debug_action(log_entry):
            debug_action_num+=1
            try:
                debugging_time+=max(1,(log_entry_timestamp-int(log[idx-1][1]))/2)
            except:
                pass
            try:
                debugging_time += max(1,(int(log[idx + 1][1]) - log_entry_timestamp)/ 2)
            except:
                pass

    return debug_action_num,debugging_time


def get_editing_info(log_segment):

    editing_time = 0
    edit_action_num = 0

    for idx,log in enumerate(log_segment):

        log_entry = log[0]
        log_entry_timestamp = int(log[1])

        if is_log_entry_edit_action(log_entry):
            edit_action_num += 1
            try:
                editing_time += max(1,(int(log_segment[idx+1][1])-log_entry_timestamp)/2)
            except:
                pass

            try:
                editing_time += max(1,(log_entry_timestamp-int(log_segment[idx-1][1]))/2)
            except:
                pass

    return edit_action_num,editing_time


def get_understanding_info(log_segment):

    understand_action_num = 0
    understanding_time = 0

    for idx,log in enumerate(log_segment):

        log_entry = log[0]
        log_entry_timestamp = int(log[1])


        if is_log_entry_understand_action(log_entry):
            understand_action_num += 1
            try:
                understanding_time += max(1, (int(log_segment[idx + 1][1]) - log_entry_timestamp) / 2)
            except:
                pass

            try:
                understanding_time += max(1, (log_entry_timestamp - int(log_segment[idx - 1][1])) / 2)
            except:
                pass

    return  understand_action_num,understanding_time

def get_active_window_times(log_segment_list):

    active_window=''

    log_segment_window_times=[]

    for log_segment in log_segment_list:
        window_times={}
        start_time =  int(log_segment[0][1])

        for log in log_segment:
            log_entry=log[0]
            log_entry_timestamp = int(log[1])

            if(log_entry[0]!='active Window'):
                continue

            if active_window == '':
                active_window=log_entry[1].split("--")[0].strip().lower()

            if(active_window not in window_times):
                window_times[active_window]=0

            window_times[active_window] += log_entry_timestamp - start_time
            start_time = log_entry_timestamp
            active_window = log_entry[1].split('--')[0].strip().lower()

        if (active_window not in window_times):
            window_times[active_window] = 0

        window_times[active_window] += int(log_segment[-1][1]) - start_time

        log_segment_window_times.append(window_times)

    return log_segment_window_times

def segment_classify_basic(log_segment):

    edit_action_num,editing_time = get_editing_info(log_segment)
    debug_action_num, debugging_time = get_debugging_info(log_segment)
    understand_action_num,understanding_time = get_understanding_info(log_segment)


    # if(debug_action_num>0):
    #     print "debug!!!",debug_action_num

    if debug_action_num==0 and edit_action_num == 0 :
        if understand_action_num > 5 or understanding_time > 60:
            return 'U'
        else:
            return 'X'

    if debug_action_num > 5:
        return 'D'

    if edit_action_num >= 3:
        return 'E'

    if understand_action_num > 5:
        return 'U'
    else:
        return 'X'


def classify_log_segment_list(log_segment_list,user_name):

    # log_segment_window_times=get_active_window_times(log_segment_list)

    user_activity=[]

    global_start_time = int (log_segment_list[0][0][1])

    for log_segment in log_segment_list:

        start_time  = int(log_segment[0][1])

        end_time  = int(log_segment[-1][1])

        activity=segment_classify_basic(log_segment)
        user_activity.append(activity)

    graph.create_dev_activity_graph(user_activity,user_name)

