from flask import Blueprint

product = Blueprint('product', __name__, url_prefix='/product')

@product.route('/hello')
def hello():
    return '<h1>Hello World from product controller!</h1>'