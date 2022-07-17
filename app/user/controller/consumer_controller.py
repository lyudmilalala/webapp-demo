from flask import Blueprint

consumer = Blueprint('consumer', __name__, url_prefix='/consumer')

@consumer.route('/hello')
def hello():
    return '<h1>Hello World from consumer controller!</h1>'