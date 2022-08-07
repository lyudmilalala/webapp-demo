from flask import Blueprint
from flask import request
from app.user.service import *
import json
consumer = Blueprint('consumer', __name__, url_prefix='/consumer')

@consumer.route('/getAll', methods=['GET'])
def getAll():
    return json.dumps(getAllActive())

@consumer.route('/getByEmail', methods=['GET'])
def getByEmail():
    email = request.args['email']
    return json.dumps(getActiveByEmail(email))

@consumer.route('/create', methods=['Post'])
def create():
    data = json.loads(request.data)
    createConsumer(data['name'], data['email'], data['password'])
    return "Create successfully."