package it.distributedsystems.model.ejb;

//import it.distributedsystems.model.logging.OperationLogger;

import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.ProducerDAO;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Local(ProducerDAO.class)
//@Remote(ProducerDAO.class) //-> TODO: serve nella versione clustering???
public class EJB3ProducerDAO implements ProducerDAO {

    @PersistenceContext(unitName = "distributed-systems-demo")
    EntityManager em;

    @Override
    @Interceptors(OperationLogger.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int insertProducer(Producer producer) {
        em.persist(producer);
        em.flush();
        return producer.getId();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int removeProducerByName(String name) {

        Producer producer;
        if(name != null && !name.equals("")) {
            producer = (Producer) em.createQuery("SELECT p FROM Producer p WHERE p.name = :producerName")
                    .setParameter("producerName", name)
                    .getSingleResult();
            em.remove(producer);
            return producer.getId();
        } else
            return 0;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int removeProducerById(int id) {
        Producer producer = em.find(Producer.class, id);
        if (producer!=null){
            em.remove(producer);
            return id;
        }
        else
            return 0;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Producer findProducerByName(String name) {
        if(name != null && !name.equals("")) {
            return (Producer) em.createQuery("SELECT p FROM Producer p where p.name = :producerName").
                    setParameter("producerName", name).getSingleResult();
        } else
            return null;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Producer findProducerById(int id) {
        Producer result = em.find(Producer.class, id);
        System.out.println("* * * * * Ho cercato producer per id * * * * *");
        return result;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Producer> getAllProducers() {
        return em.createQuery("SELECT p FROM Producer p").getResultList();
    }
}
