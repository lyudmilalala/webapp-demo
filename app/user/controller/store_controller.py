from flask import Blueprint

store = Blueprint('store', __name__, url_prefix='/store')

@store.route('/hello')
def hello():
    return '<h1>Hello World from store controller!</h1>'