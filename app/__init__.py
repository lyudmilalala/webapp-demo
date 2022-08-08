import os
from flask import Flask
from werkzeug.utils import import_string

from app.config.dev import DevConfig
from app.config.home import HomeConfig
from app.config.test import TestConfig
from app.config.prod import ProdConfig

from app.cross_cutting.exceptions.handler import register_errors

import logging
from logging.handlers import TimedRotatingFileHandler

from flask_sqlalchemy import SQLAlchemy

CONFIG_MAPPER = {
    'dev': DevConfig,
    'home': HomeConfig,
    'prod': ProdConfig,
    'test': TestConfig
}

BLUE_PRINT_MAPPER = [
    'app.user:consumer',
    'app.user:store',
    'app.order:product',
]

basedir = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
db = SQLAlchemy()

print("basedir = " + basedir)

def register_blueprint(app):
    for bp_name in BLUE_PRINT_MAPPER:
        bp = import_string(bp_name)
        app.register_blueprint(bp)

def register_logging(app):
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

    order_logger = logging.getLogger('order_log')
    order_handler = TimedRotatingFileHandler('/Users/lyudmila/Desktop/projects/python_practice/logs/order/order.log', when='M', backupCount=3)
    order_handler.setFormatter(formatter)
    order_handler.setLevel(logging.INFO)
    order_console_handler = logging.StreamHandler()
    order_console_handler.setLevel(logging.INFO)
    order_console_handler.setFormatter(formatter)
    order_logger.addHandler(order_handler)
    order_logger.addHandler(order_console_handler)
    
    user_logger = logging.getLogger('user_log')
    user_handler = TimedRotatingFileHandler('/Users/lyudmila/Desktop/projects/python_practice/logs/user/user.log', when='M', backupCount=3)
    user_handler.setFormatter(formatter)
    user_handler.setLevel(logging.INFO)
    user_console_handler = logging.StreamHandler()
    user_console_handler.setLevel(logging.INFO)
    user_console_handler.setFormatter(formatter)
    user_logger.addHandler(user_handler)
    user_logger.addHandler(user_console_handler)

def create_app(config_name=None):
    app = Flask(__name__)
    if config_name is None:
        config_name = 'dev'
    app.config.from_object(CONFIG_MAPPER[config_name])
    print('app config = ', app.config['USER_NAME'])
    register_blueprint(app)
    register_logging(app)
    register_errors(app)
    db.init_app(app)
    with app.app_context():
        db.create_all()
    return app