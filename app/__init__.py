from flask import Flask
from werkzeug.utils import import_string

from .config.dev import DevConfig
from .config.home import HomeConfig
from .config.test import TestConfig
from .config.prod import ProdConfig

from .cross_cutting.exceptions.handler import register_errors

import logging
from logging.handlers import RotatingFileHandler

CONFIG_MAPPER = {
    'dev': DevConfig,
    'home': HomeConfig,
    'prod': ProdConfig,
    'test': TestConfig
}

BLUE_PRINT_MAPPER = [
    'app.user:consumer',
    'app.user:seller',
]

def register_blueprint(app):
    for bp_name in BLUE_PRINT_MAPPER:
        bp = import_string(bp_name)
        app.register_blueprint(bp)

def register_logging(app):
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

    file_handler = RotatingFileHandler('logs/bluelog.log', maxBytes=10 * 1024 * 1024, backupCount=10)
    file_handler.setFormatter(formatter)
    file_handler.setLevel(logging.INFO)

    if not app.debug:
        app.logger.addHandler(file_handler)

def create_app(config_name=None):
    app = Flask(__name__)
    if config_name is None:
        config_name = 'dev'
    app.config.from_object(CONFIG_MAPPER[config_name])
    register_blueprint(app)
    register_logging(app)
    register_errors(app)
    return app