import json
def parse_log_entry(log_entry):
    log_type = log_entry['logType']

    if(log_type=="CompilationStatus"):
        return parse_compilation_status(log_entry)
    elif(log_type=="ToolEntry"):
        return parse_tool_entry(log_entry)
    elif(log_type=="FocusEvent"):
        return parse_focus_entry(log_entry)
    elif(log_type=="FileEditorChange"):
        return parse_file_editor_change(log_entry)
    elif(log_type=="EditorMouseEvent"):
        return parse_editor_mouse_event(log_entry)
    elif(log_type=="Notification"):
        return parse_notification(log_entry)
    elif(log_type=="Action")
        return parse_action(log_entry)

def parse_compilation_status(log_entry):

    if(int(log_entry['errors'])==0):
        return ["Successful Compilation"]
    else:
        return ["UnSuccessFull Compilation"]

def parse_tool_entry(log_entry):

    if(log_entry['toolEnter']=="true"):
        return ["Entered Tool",log_entry['tooName'],log_entry['mouseMovementNum']]
    else:
        return ["Exited Tool",log_entry['tooName'],log_entry['mouseMovementNum']]

def parse_focus_entry(log_entry):
    if(log_entry['focusGained']=="true"):
        return ["Focus Gained Tool" ,log_entry['toolName']]
    else:
        return ["Focus Lost Tool" ,log_entry['toolName']]

def parse_file_editor_change(log_entry):
    return ['File Opened ',log_entry['fileName']]

def parse_editor_mouse_event(log_entry):

    mouse_event=json.loads(log_entry['mouseEvent'])
    if("EXITED" in mouse_event['MouseEvent']):
        return ["Exiting File Editor",log_entry['fileName'],log_entry['mouseMovementNum']]
    else:
        return ["Entering File Editor",log_entry['fileName'],log_entry['mouseMovementNum']]
def parse_notification(log_entry):
    return ["Notification",log_entry['groupId'],log_entry['content']]



def parse_action(log_entry):