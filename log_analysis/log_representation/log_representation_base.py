import abc


class LogRepresentationBase():
    __metaclass__ = abc.ABCMeta

    @classmethod
    @abc.abstractmethod
    def represent_log_entry(cls):
        raise NotImplementedError