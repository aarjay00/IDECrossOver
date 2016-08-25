class log():
    def __init__(self,logJson):
        self.log_json=logJson
        self.log_type=logJson['logType']
        self.time_stamp=logJson['timeStamp']
        del self.log_json['logType']
        del self.log_json['timeStamp']
