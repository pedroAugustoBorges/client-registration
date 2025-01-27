package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.example.connection.ConnectionFactory;
import org.example.domain.Client;
import org.example.util.TransactionFunction;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ClientRepository implements IClientRepository {

    public static Logger logger;


    @Override
    public void save(Client client) {
        executeTransaction(em -> {
            em.persist(client);
            return null;
        });
    }

    @Override
    public Optional<Client> findById (Integer id){
      return executeTransaction(em -> Optional.ofNullable(em.find(Client.class, id)));
    }

    @Override
    public boolean deleteClientById(Integer id) {

        return Boolean.TRUE.equals(
        executeTransaction(em -> {

            Client client = em.find(Client.class,id);

            if (client != null) {
               em.remove(client);
                return true;
            }else{
                return false;
            }
        }));
    }



    @Override
    public boolean deleteClientByName(String name) {
            String jpql = "SELECT c FROM Client c " +
                "WHERE c.name = :name";

        return executeTransaction( em -> {

            List<Client> resultListClients = em.createQuery(jpql, Client.class).setParameter("name", name)
                    .setMaxResults(1)
                    .getResultList();

            if (!resultListClients.isEmpty() ){
                Client client = resultListClients.getFirst();
                em.remove(client);
                return true;
            }else {
                return false;
            }


        });


    }

    @Override
    public void update(Client client) {

        executeTransaction(em -> {
            em.merge(client);
            return null;
        });
    }

    private  <T> T  executeTransaction (TransactionFunction <T> function){
        EntityManager em = ConnectionFactory.getEntityManager();

        try {
            em.getTransaction().begin();

            T result = function.apply(em);

            em.getTransaction().commit();

            return result;

        }catch (PersistenceException e){

            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }

            throw e;
        }finally {
            ConnectionFactory.closeEntityManager(em);
        }


    }
}
