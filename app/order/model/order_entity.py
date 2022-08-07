from app.cross_cutting.model.base_entity import BaseEntity 
from sqlalchemy import Column, Integer
from sqlalchemy.orm import relationship

class OrderEntity(BaseEntity):
    __tablename__ = 'order'
    consumer_id = Column(Integer, nullable=False)
    consumer = relationship('ConsumerEntity', foreign_keys=[consumer_id], primaryjoin='ConsumerEntity.id == OrderEntity.consumer_id')
    product_id = Column(Integer, nullable=False)
    product = relationship('ProductEntity', foreign_keys=[product_id], primaryjoin='ProductEntity.id == OrderEntity.product_id')
    quantity  = Column(Integer, nullable=False)