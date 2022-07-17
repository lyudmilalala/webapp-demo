class BaseExeption(Exception):

    def __init__(self, status=1001, message="Server side error."):
        super().__init__(" ".join(message))
        self.message = " ".join(message)
        self.status = status
    
    def __str__(self):
        return repr("Error " + str(self.status) + ": " + self.message)

class ArgumentException(BaseExeption):
    def __init__(self, status=1004, message="Argument is invalid."):
        super().__init__(status, message)

class ObjectConstraintException(BaseExeption):
    def __init__(self, status=1005, message="Object constraint does not satisfied."):
        super().__init__(status, message)
