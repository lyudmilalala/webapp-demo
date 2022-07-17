from .base import BaseConfig

class ProdConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = ''
    USER_NAME = 'prod'