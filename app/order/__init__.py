from .controller import *
from .model import *
from .service import *
import logging

order_logger = logging.getLogger('order_log')
order_logger.info("This is the order module.")