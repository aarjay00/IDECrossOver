from log_parser_base import LogParserBase
from .. import util
import json


class LogParserIntellij(LogParserBase):

    @classmethod
    def parse_log_file(cls,file_name):
        print "parsing - ", file_name
        file_data_lines=[]
        with open(file_name, 'r') as fileFD:
            file_data = util.decode_data(fileFD.read())
            file_data_lines = file_data.split('\n')[1::2]
            file_data_lines = [file_data_line.strip() for file_data_line in file_data_lines]
            file_data_lines = [file_data_clean_line[file_data_clean_line.find("{"):] for file_data_clean_line in file_data_lines]

        log_list = []
        for file_data_line in file_data_lines:
            try:
                log_list.append(json.loads(file_data_line))
            except ValueError:
                # pass
                print "Could not load as Json", file_data_line

        return log_list

    @classmethod
    def parse_log_dir(cls,log_dir):
        return super(LogParserIntellij, cls).parse_log_dir(log_dir)


    @classmethod
    def parse_log_collection(cls, log_collection_dir):
        return super(LogParserIntellij, cls).parse_log_collection(log_collection_dir)