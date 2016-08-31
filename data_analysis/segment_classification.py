

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
                debugging_time += max(1,(int(log[idx + 1][1]) - log_entry_timestamp) / 2)
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
                editing_time += max(1,(int(log_segment[idx+1])-log_entry_timestamp)/2)
            except:
                pass

            try:
                editing_time += max(1,(log_entry_timestamp-int(log_segment[idx-1])/2)/2)
            except:
                pass

    return editing_time,edit_action_num

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
    pass



def classify_log_segment_list(log_segment_list):

    log_segment_window_times=get_active_window_times(log_segment_list)