from log_representation_base import LogRepresentationBase
from action_type import ActionType
import json
import re


class LogRepresentationBasic(LogRepresentationBase):

    @classmethod
    def represent_log_entry(cls,log_entry):
        log_type = log_entry['logType']

        if log_type == "CompilationStatus":
            return cls.parse_compilation_status(log_entry)
        elif log_type == "ToolEntry":
            return cls.parse_tool_entry(log_entry)
        elif log_type == "FocusEvent":
            return cls.parse_focus_entry(log_entry)
        elif log_type == "FileEditorChange":
            return cls.parse_file_editor_change(log_entry)
        elif log_type == "EditorMouseEvent":
            return cls.parse_editor_mouse_event(log_entry)
        elif log_type == "Notification":
            return cls.parse_notification(log_entry)
        elif log_type == "Action":
            return cls.parse_action(log_entry)
        elif log_type == "activeWindow":
            return cls.parse_active_window(log_entry)
        elif log_type == "projectType":
            return cls.parse_project_details(log_entry)
        elif log_type == "ProjectOpenClose":
            return cls.parse_project_details(log_entry)
        elif log_type == "FileOpenClose":
            return None
        elif log_type == "DocumentEvent":
            return cls.parse_document_event(log_entry)
        else:
            print log_entry

    @classmethod
    def parse_compilation_status(cls,log_entry):
        if int(log_entry['errors']) == 0:
            return ["Successful Compilation"]
        else:
            return ["UnSuccessFull Compilation"]

    @classmethod
    def parse_tool_entry(cls,log_entry):
        if log_entry['toolEnter'] == "true":
            return ["Entered Tool", log_entry['tooName'], log_entry['mouseMovementNum']]
        else:
            return ["Exited Tool", log_entry['tooName'], log_entry['mouseMovementNum']]

    @classmethod
    def parse_focus_entry(cls,log_entry):
        if log_entry['focusGained'] == "true":
            return ["Focus Gained Tool", log_entry['toolName']]
        else:
            return ["Focus Lost Tool", log_entry['toolName']]

    @classmethod
    def parse_file_editor_change(cls,log_entry):
        try:
            return ['Active File', log_entry['fileName']]
        except KeyError:
            return None

    @classmethod
    def parse_editor_mouse_event(cls,log_entry):
        mouse_event = json.loads(log_entry['mouseEvent'])
        try:
            if "EXITED" in mouse_event['MouseEvent']:
                return ["Exiting File Editor", log_entry['fileName'], "activity-", log_entry['mouseMovementNum']]
            else:
                return ["Entering File Editor", log_entry['fileName'], "activity-", log_entry['mouseMovementNum']]
        except:
            return None

    @classmethod
    def parse_notification(cls,log_entry):
        return ["Notification", log_entry['groupId'], log_entry['content']]

    @classmethod
    def parse_action(cls,log_entry):
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

    @classmethod
    def parse_active_window(cls,log_entry):
        return ["active Window", log_entry['windowDetails']]

    @classmethod
    def parse_project_details(cls,log_entry):
        if log_entry['logType'] == "ProjectOpenClose" and log_entry["projectAction"] == "true":
            return ["Active Project", log_entry["projectDetails"]]
        elif log_entry['logType'] == "projectType":
            return ["Active Project", log_entry["projectDetails"]]
        return None

    @classmethod
    def parse_document_event(cls,logEntry):
        old_length = len(logEntry['oldText'])
        new_length = len(logEntry['newText'])
        if  new_length == 0 and old_length == 0 :
            return None
        elif old_length == 0 and new_length > 0 :
            return ["Document Changed", "Code Added", str(new_length)]
        elif old_length > 0 and new_length == 0 :
            return ["Document Changed", "Code Removed", str(old_length)]
        elif old_length < new_length :
            return ["Document Changed", "Code Replaced", str(new_length), str(old_length)]
        else:
            return ["Document Changed", "Code Replaced", str(new_length), str(old_length)]
        return None
