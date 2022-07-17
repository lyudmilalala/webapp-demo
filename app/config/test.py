from .base import BaseConfig

class TestConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = ''
    USER_NAME = 'test'