from .base import BaseConfig

class HomeConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = ''
    USER_NAME = 'home-mac'