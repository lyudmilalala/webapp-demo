from flask import Blueprint

seller = Blueprint('seller', __name__, url_prefix='/seller')

@seller.route('/hello')
def hello():
    return '<h1>Hello World from seller controller!</h1>'