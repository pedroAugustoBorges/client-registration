package org.example.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class ConnectionFactory {

    private final static String PERSISTENCE_NAME = "myPU";

    private static EntityManagerFactory emf;

    private ConnectionFactory () {

    }

    public static EntityManager getEntityManager (){

        if (emf == null){

            try {
                emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
            }catch (PersistenceException | NullPointerException e){
              throw e;
            }

        }

        if (!emf.isOpen()){
            throw new IllegalStateException("Entity manager is closed");
        }

        return emf.createEntityManager();
    }

    public static void closeEntityMangerFactory() {
        if (emf != null && emf.isOpen()){
            emf.close();;
        }
    }

    public static void closeEntityManager (EntityManager em ) {
        if (em != null && em.isOpen()){
            em.close();
        }
    }
}
