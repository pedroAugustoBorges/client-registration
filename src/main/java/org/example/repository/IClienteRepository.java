package org.example.repository;

import org.example.domain.Client;

import java.util.Optional;

public interface IClienteRepository {

    void save (Client client);

    boolean deleteClientById (Integer id);

    boolean deleteClientByName (String name);

    void update (Client client);

    Optional<Client> findById (Integer id);

}
