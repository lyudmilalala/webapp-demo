from app.cross_cutting.model.base_entity import BaseEntity 
import datetime
from sqlalchemy import Column, String, DateTime

class ConsumerEntity(BaseEntity):
    __tablename__ = 'consumer'
    name = Column(String(100), nullable=False)
    email = Column(String(50), nullable=False)
    password = Column(String(50), nullable=False)
    sign_up_time = Column(DateTime, default=datetime.datetime.now, nullable=False)
    latest_login_time = Column(DateTime)
    
    def __init__(self, name, email, passowrd):
        super().__init__()
        self.name = name
        self.email = email
        self.passowrd = passowrd
        self.sign_up_time = datetime.datetime.now