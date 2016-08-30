import json
import re
from action_type import ActionType

def parse_logs(log_entry_list):
    parsed_logs=[]
    for log_entry in log_entry_list:
        parsed_log_entry=(parse_log_entry(log_entry),log_entry['timeStamp'])
        if(parsed_log_entry[0]!=None):
            parsed_logs.append(parsed_log_entry)
    return parsed_logs
def parse_log_entry(log_entry):
    log_type = log_entry['logType']

    if log_type == "CompilationStatus":
        return parse_compilation_status(log_entry)
    elif log_type == "ToolEntry":
        return parse_tool_entry(log_entry)
    elif log_type == "FocusEvent":
        return parse_focus_entry(log_entry)
    elif log_type == "FileEditorChange":
        return parse_file_editor_change(log_entry)
    elif log_type == "EditorMouseEvent":
        return parse_editor_mouse_event(log_entry)
    elif log_type == "Notification":
        return parse_notification(log_entry)
    elif log_type == "Action":
        return parse_action(log_entry)
    elif log_type == "activeWindow":
        return parse_active_window(log_entry)
    elif log_type == "projectType":
        return parse_project_details(log_entry)
    elif log_type == "ProjectOpenClose":
        return parse_project_details(log_entry)
    elif log_type == "FileOpenClose":
        return None
    elif log_type == "DocumentEvent":
        return parse_document_event(log_entry)
    else:
        print log_entry


def parse_compilation_status(log_entry):
    if int(log_entry['errors']) == 0:
        return ["Successful Compilation"]
    else:
        return ["UnSuccessFull Compilation"]


def parse_tool_entry(log_entry):
    if log_entry['toolEnter'] == "true":
        return ["Entered Tool", log_entry['tooName'], log_entry['mouseMovementNum']]
    else:
        return ["Exited Tool", log_entry['tooName'], log_entry['mouseMovementNum']]


def parse_focus_entry(log_entry):
    if log_entry['focusGained'] == "true":
        return ["Focus Gained Tool", log_entry['toolName']]
    else:
        return ["Focus Lost Tool", log_entry['toolName']]


def parse_file_editor_change(log_entry):
    try:
        return ['Active File', log_entry['fileName']]
    except KeyError:
        return None


def parse_editor_mouse_event(log_entry):
    mouse_event = json.loads(log_entry['mouseEvent'])
    try:
        if "EXITED" in mouse_event['MouseEvent']:
            return ["Exiting File Editor", log_entry['fileName'], "activity-",log_entry['mouseMovementNum']]
        else:
            return ["Entering File Editor", log_entry['fileName'], "activity-",log_entry['mouseMovementNum']]
    except:
        return None


def parse_notification(log_entry):
    return ["Notification", log_entry['groupId'], log_entry['content']]


def parse_action(log_entry):
    win_path = r"^[A-Za-z]:(/[0-9A-Za-z.]*)+$"
    lin_path = r"^(/[0-9A-Za-z.]*)+$"

    try:
        if re.match(win_path, log_entry['description']) or re.match(lin_path, log_entry['description']):
            return ["Action", "misc"]
    except:
        pass
    try:
        return ['Action', ActionType.get_type(log_entry['myText'])]
    except KeyError:
        if "myText" not in log_entry:
            return
        print "HERE", log_entry


def parse_active_window(log_entry):
    return ["active Window", log_entry['windowDetails']]


def parse_project_details(log_entry):
    if log_entry['logType'] == "ProjectOpenClose" and log_entry["projectAction"] == "true":
        return ["Active Project", log_entry["projectDetails"]]
    elif log_entry['logType'] == "projectType":
        return ["Active Project", log_entry["projectDetails"]]
    return None


def parse_document_event(logEntry):
    oldLength=len(logEntry['oldText'])
    newLength=len(logEntry['newText'])
    if(newLength==0 and oldLength==0):
        return None
    elif(oldLength==0 and newLength>0):
        return ["Document Changed","Code Added",str(newLength)]
    elif(oldLength>0  and newLength==0):
        return ["Document Changed","Code Removed",str(oldLength)]
    elif(oldLength<newLength):
        return ["Document Changed", "Code Replaced",str(newLength),str(oldLength)]
    else:
        return ["Document Changed", "Code Replaced",str(newLength),str(oldLength)]
    return None
