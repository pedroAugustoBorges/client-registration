package org.example.service;

import org.example.domain.Client;
import org.example.exceptions.EntityNotFoundException;
import org.example.repository.IClientRepository;

import java.util.Optional;

public class ClientService {

    private IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save (Client client) {
        if (client == null){
            throw new NullPointerException("Client can't be null to save");
        }

        clientRepository.save(client);
    }

    public Client findById(Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Id can't be null ");

        }

        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));
    }

    public void update (Client client){
        if (client == null){
            throw new NullPointerException("Client can't be null to update");
        }

        clientRepository.update(client);
    }

    public boolean deleteClientById (Integer id){

        if (id == null || id < 0) {
            throw new IllegalArgumentException("Id can't be null ");
        }

        return clientRepository.deleteClientById(id);
    }

    public boolean deleteClientByName (String name){

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name can't be to delete or empty to delete");
        }

        return clientRepository.deleteClientByName(name);
    }



}
