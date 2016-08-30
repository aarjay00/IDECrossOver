import abc


class LogRepresentationBase():
    __metaclass__ = abc.ABCMeta

    @abc.abstractmethod
    @classmethod
    def represent_log_entry(cls):
        raise NotImplementedError