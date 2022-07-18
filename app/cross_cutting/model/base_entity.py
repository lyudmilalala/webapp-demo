from app import db
import datetime

class BaseEntity(db.Model):
    __abstract__ = True
    id = db.Column(db.Integer,primary_key = True,autoincrement=True)
    status = db.Column(db.String(8))
    latest_modification_time = db.Column(db.DateTime, default=datetime.datetime.now, onupdate=datetime.datetime.now)
    db_version = db.Column(db.Integer, default=1)

    def __init__():
        status = 'ACTIVE'
        latest_modification_time = datetime.datetime.now