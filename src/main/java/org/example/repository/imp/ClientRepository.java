package org.example.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import org.example.connection.ConnectionFactory;
import org.example.domain.Client;
import org.example.repository.IClientRepository;
import org.example.util.TransactionFunction;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ClientRepository implements IClientRepository {

    public static Logger logger;


    @Override
    public Client save(Client client) {
       return executeTransaction(em -> {
          em.persist(client);
          em.flush();
          return client;
      });

    }

    @Override
    public Optional<Client> findById (Integer id){

        try {
            return executeTransaction(em ->  {
                Client clientById = em.createQuery("SELECT c FROM Client c WHERE c.id = :id", Client.class)
                        .setParameter("id", id)
                        .getSingleResult();


                return Optional.ofNullable(clientById);

            });
        }catch (NoResultException e){
           return Optional.empty();
        }

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

    @Override
    public List<Client> listAllClients() {

        String jpql = "SELECT c FROM Client c";
       return executeTransaction(em -> em.createQuery(jpql, Client.class).getResultList());
    }

    @Override
    public List<Client> listActiveClient() {
        String jpql = "SELECT c FROM Client c WHERE c.status = 'Active'";

        return executeTransaction( em -> {
            return em.createQuery(jpql, Client.class).getResultList();
        });
    }



    @Override
    public List<Client> listInactiveClient() {

        String jpql = "SELECT c FROM Client c WHERE c.status = 'Inactive'";

        return executeTransaction(em -> {
            return em.createQuery(jpql, Client.class).getResultList();
        });
    }

    @Override
    public boolean deletePhysicallyById(Integer id) {

        String jpql = "DELETE FROM Client c WHERE c.id = :id";
       return executeTransaction(em -> {

            Optional<Client> clientFounded = findById(id);
            if (clientFounded.isPresent()){
                int rowsAffected = em.createQuery(jpql).setParameter("id", id).executeUpdate();

                return rowsAffected > 0;
            }else {
                return false;
            }

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

    private <T> T executeQuery (TransactionFunction<T> t){
        EntityManager em = ConnectionFactory.getEntityManager();

        try {
            return t.apply(em);
        }finally {
            ConnectionFactory.closeEntityManager(em);
        }
    }
}
