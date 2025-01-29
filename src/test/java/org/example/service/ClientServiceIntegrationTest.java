package org.example.service;

import org.example.repository.ClientRepository;
import org.example.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceIntegrationTest {

    private IClientRepository iClientRepository;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        iClientRepository = new ClientRepository();

        clientService = new ClientService(iClientRepository);
    }
}