from .controller import *
from .model import *
from .service import *
import logging

order_logger = logging.getLogger('order_log')
order_logger.info("order module info test.")
order_logger.warn("order module warn test.")
order_logger.error("order module error test.")