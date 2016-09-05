import util
import time

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

def get_log_time(log_entry):

    epoch = int(log_entry[1])

    time_tuple = time.gmtime(epoch)

    time_readeable = time.strftime("%m:%d::%H:%M:%S",time_tuple)

    full_time_readeable = time.ctime(epoch)

    return time_readeable