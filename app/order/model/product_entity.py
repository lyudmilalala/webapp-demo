from app.cross_cutting.model.base_entity import BaseEntity 
from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship

class ProductEntity(BaseEntity):
    __tablename__ = 'product'
    name = Column(String(100), nullable=False)
    price = Column(Integer, nullable=False)
    store_id = Column(Integer, nullable=False)
    store = relationship('StoreEntity', foreign_keys=[store_id], primaryjoin='StoreEntity.id == ProductEntity.store_id')