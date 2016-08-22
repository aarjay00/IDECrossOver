

def get_file_open_commands(command_list):
    return filter(lambda x: x["command_type"]=="FileOpenCommand" , command_list)