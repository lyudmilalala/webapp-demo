from .base import BaseConfig

class DevConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = ''
    USER_NAME = 'dev'




