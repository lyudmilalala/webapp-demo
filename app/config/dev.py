from .base import BaseConfig

class DevConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://root:1996S05y22u!@127.0.0.1:3306/shopping'
    USER_NAME = 'dev'


