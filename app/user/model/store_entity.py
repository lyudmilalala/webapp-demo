from app.cross_cutting.model.base_entity import BaseEntity 
import datetime
from sqlalchemy import Column, String, DateTime

class StoreEntity(BaseEntity):
    __tablename__ = 'store'
    name = Column(String(100), nullable=False)
    email = Column(String(50), nullable=False)
    password = Column(String(50), nullable=False)
    certification = Column(String(30), nullable=False)
    state = Column(String(30), nullable=False)
    city = Column(String(30), nullable=False)
    address = Column(String(200))
    sign_up_time = Column(DateTime, default=datetime.datetime.now, nullable=False)
    latest_login_time = Column(DateTime)