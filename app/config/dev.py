from .base import BaseConfig

class DevConfig(BaseConfig):
    PORT = 5001
    SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://root:1996S05y22u!@127.0.0.1:3306/shopping'
    USER_NAME = 'dev'
    LOGGER_CONFIG = {
        'version': 1,
        'formatters': {
            'default': {'format': '%(asctime)s - %(levelname)s - %(message)s', 'datefmt': '%Y-%m-%d %H:%M:%S'}
        },
        'handlers': {
            'console': {
                'level': 'INFO',
                'class': 'logging.StreamHandler',
                'formatter': 'default',
                'stream': 'ext://sys.stdout'
            },
            'user_rotating_file_handler': {
                'level': 'INFO',
                'class': 'logging.handlers.TimedRotatingFileHandler',
                'formatter': 'default',
                'when': 'h',
                'filename': '/Users/lyudmila/Desktop/projects/python_practice/logs/user/user.log',
                'backupCount': 5
            },
            'order_rotating_file_handler': {
                'level': 'INFO',
                'class': 'logging.handlers.TimedRotatingFileHandler',
                'formatter': 'default',
                'when': 'h',
                'filename': '/Users/lyudmila/Desktop/projects/python_practice/logs/order/order.log',
                'backupCount': 5
            }
        },
        'loggers': {
            'order_log': {
                'level': 'INFO',
                'handlers': ['console', 'order_rotating_file_handler']
            },
            'user_log': {
                'level': 'INFO',
                'handlers': ['console', 'user_rotating_file_handler']
            }
        },
        'disable_existing_loggers': True
    }
