from flask import Blueprint

order = Blueprint('order', __name__, url_prefix='/order')

@order.route('/hello')
def hello():
    return '<h1>Hello World from order controller!</h1>'