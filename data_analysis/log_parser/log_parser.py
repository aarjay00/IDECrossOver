import abc
import util
class log_parser():

    __metaclass__ = abc.ABCMeta

    @classmethod
    def parse_log_dir(cls,dir_name):
        file_list = util.get_all_files(dir_name)
        user_log_list = []
        for file in file_list:
            user_log_list.extend(cls.parse_log_file(file))
        return user_log_list

    @classmethod
    def parse_log_collection(cls,log_dir_collection):
        log_dir_paths = util.get_all_dir(log_dir_collection)
        logs_collection = {}
        for log_dir in log_dir_collection:
            logs_collection[log_dir] = cls.parse_log_dir(log_dir)
        return logs_collection

    @abc.abstractmethod
    def parse_log_file(self):
        raise NotImplementedError