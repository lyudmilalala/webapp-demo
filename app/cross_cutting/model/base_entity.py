from app import db
from sqlalchemy import Column, Integer, String, DateTime
import datetime

class BaseEntity(db.Model):
    __abstract__ = True
    id = Column(Integer,primary_key = True,autoincrement=True)
    status = Column(String(8), default='ACTIVE')
    latest_modification_time = Column(DateTime, default=datetime.datetime.now, onupdate=datetime.datetime.now)
    db_version = Column(Integer, default=1)
