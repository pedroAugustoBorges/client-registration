package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

public interface TransactionFunction <T>{

    T apply (EntityManager em) throws PersistenceException;
}
