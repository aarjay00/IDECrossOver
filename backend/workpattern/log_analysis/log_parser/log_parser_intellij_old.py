from log_parser_base import LogParserBase
from .. import util
import json

class LogParserIntellijOld(LogParserBase):

    @classmethod
    def parse_log_file(cls,file_name):
        log_list = []
        with open(file_name, 'r') as fileFD:

            for line in fileFD.readlines():
                line = line[:-1]
                if  line[0] == '-' :
                    index = line.find("{")
                    line = line[index:]
                try:
                    log_list.append(json.loads(line))
                except:
                    print "Could not load as Json", line

        return log_list

    @classmethod
    def parse_log_dir(cls,log_dir):
        return super(LogParserIntellijOld, cls).parse_log_dir(cls, log_dir)


    @classmethod
    def parse_log_collection(cls, log_collection_dir):
        return super(LogParserIntellijOld, cls).parse_log_collection(cls, log_collection_dir)