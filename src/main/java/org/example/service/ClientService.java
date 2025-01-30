package org.example.service;

import org.example.domain.Client;
import org.example.exceptions.EntityNotFoundException;
import org.example.repository.IClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save (Client client) {
        if (client == null){
            throw new IllegalArgumentException("Client can't be null to save");
        }

        return clientRepository.save(client);
    }

    public Client findById(Integer id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id can't be null ");

        }

        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));
    }

    public void update(Client client) {
        if (client == null) {
            throw new NullPointerException("Client can't be null to update");
        }

        clientRepository.update(client);
    }

    public boolean deleteClientById(Integer id) {

        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id can't be null ");
        }

        return clientRepository.deleteClientById(id);
    }

    public boolean deleteClientByName(String name) {

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name can't be to delete or empty to delete");
        }

        return clientRepository.deleteClientByName(name);
    }

    public List<Client> listAllClient() {
        List<Client> clients = clientRepository.listAllClients();

        return clients.isEmpty() ? new ArrayList<>() : clients;

    }

    public List<Client> listActiveClient() {
        List<Client> clients = clientRepository.listActiveClient();

        return clients.isEmpty() ? new ArrayList<>() : clients;
    }

    public List<Client> listInactiveClient() {
        List<Client> clients = clientRepository.listInactiveClient();

        return clients.isEmpty() ? new ArrayList<>() : clients;
    }

    public boolean deletePysicallyById (Integer id){
        if (id < 0 || id == null){
            throw new IllegalArgumentException("Id invalid");
        }

       return clientRepository.deletePhysicallyById(id);
    }


}
