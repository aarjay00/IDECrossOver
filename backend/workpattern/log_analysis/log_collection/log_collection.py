from ..log_representation.log_representation_base import  LogRepresentationBase
from ..log_parser.log_parser_base import LogParserBase


class LogCollection():

    log_representation_default=None
    log_parser_default=None
    log_collection_default=None

    def __init__(self,log_parser,log_representation):

        if log_representation == LogRepresentationBase :
            raise NotImplementedError

        if log_parser == LogParserBase:
            raise NotImplementedError

        self.log_representation_default=log_representation
        self.log_parser_default=log_parser

    def set_log_parser(self,log_parser_name,log_parser_class):

        if log_parser_class == LogParserBase:
            raise NotImplementedError

        setattr(self,log_parser_name,log_parser_class)

    def set_log_representation(self,log_representation_name,log_representation_class):

        if log_representation_class == LogRepresentationBase:
            raise NotImplementedError

        setattr(self,log_representation_name,log_representation_class)

    def load_log_collection(self,log_collection_dir,log_collection_name='log_collection_default',
                            log_parser_name='log_parser_default'):

        log_parser=getattr(self,log_parser_name)

        log_collection=log_parser.parse_log_collection(log_collection_dir)

        setattr(self,log_collection_name,log_collection)

    def convert_log_collection_representation(self,log_collection_name='log_collection_default',
                                log_collection_represented_name='log_collection_represented_default',
                                log_representation_name='log_representation_default'):

        setattr(self, log_collection_represented_name, {})

        log_representation = getattr(self,log_representation_name)

        log_collection = getattr(self,log_collection_name)

        log_collection_represented = getattr(self,log_collection_represented_name)

        for user_log_name in log_collection.keys():

            user_log=log_collection[user_log_name]

            user_log_represented=[]

            for log_entry in user_log:
                log_entry_represented=log_representation.represent_log_entry(log_entry)
                if(log_entry_represented!=None):
                    user_log_represented.append(log_entry_represented)

            log_collection_represented[user_log_name] = user_log_represented
