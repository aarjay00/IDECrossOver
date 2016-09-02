import util
from os import path


class ActionType():


    @classmethod
    def load_files(cls,action_type_data_dir='action_type_data'):

        action_type_files = util.get_all_files(action_type_data_dir)
        setattr(cls,'action_type_dict',{})

        for action_type_file in action_type_files:
            cls.parse_action_type_file(action_type_file)

    @classmethod
    def parse_action_type_file(cls,action_type_file):

        action_list = open(action_type_file, 'r').readlines()
        action_list = [action.strip() for action in action_list]
        action_type = path.basename(action_type_file)
        action_type_dict=getattr(cls,'action_type_dict')

        for action in action_list:
            action_type_dict[action] = action_type

    @classmethod
    def get_type(cls,action):
        return getattr(cls,'action_type_dict')[action]