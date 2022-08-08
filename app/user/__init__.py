from .controller import *
from .model import *  # only entity be detected can be auto created by sqlachemy
from .service import *
import logging

user_logger = logging.getLogger('user_log')
user_logger.info("This is the user module.")