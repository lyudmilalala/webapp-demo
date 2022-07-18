from app.user.model.consumer_entity import ConsumerEntity
from app import db

def getAllActive():
    clist = ConsumerEntity.query.filter(ConsumerEntity.status == 'ACTIVE').all()

def getActiveByEmail(email):
    c1 = ConsumerEntity.query.filter(ConsumerEntity.email == email, ConsumerEntity.status == 'ACTIVE').first()
    return c1

def createConsumer(name, email, password):
    c = ConsumerEntity(name, email, password)
    db.session.add(c)
    db.session.commit()