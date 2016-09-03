import abc
from .. import  util
class LogParserBase():

    __metaclass__ = abc.ABCMeta

    @classmethod
    def parse_log_dir(cls,dir_name):
        file_list = util.get_all_files(dir_name)
        log_list = []
        for file in file_list:
            log_list.extend(cls.parse_log_file(file))
        return log_list

    @classmethod
    def parse_log_collection(cls, log_collection_dir):
        log_dir_list = util.get_all_dir(log_collection_dir)
        logs_collection = {}
        for log_dir in log_dir_list:
            logs_collection[log_dir] = cls.parse_log_dir(log_dir)
        return logs_collection

    @classmethod
    @abc.abstractmethod
    def parse_log_file(cls):
        raise NotImplementedError
