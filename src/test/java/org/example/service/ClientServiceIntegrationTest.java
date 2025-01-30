package org.example.service;

import org.example.domain.Client;
import org.example.enumeration.ClientStatus;
import org.example.repository.imp.ClientRepository;
import org.example.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceIntegrationTest {

    private IClientRepository iClientRepository;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        iClientRepository = new ClientRepository();

        clientService = new ClientService(iClientRepository);
    }


    @Test
    public void shouldUpdateStatusForInactiveWhenClientItIsDeleted() {

        Client client = new Client(null, "Pedro Augusto", "pedroameloborges", LocalDate.of(2006, 02, 21));

        Client clientSaved = clientService.save(client);

        assertEquals(ClientStatus.ACTIVE, clientSaved.getStatus());

        boolean isDeleted = clientService.deleteClientById(clientSaved.getId());

        assertTrue(isDeleted);


        Client clientAfterUpdated = clientService.findById(clientSaved.getId());
        assertEquals(ClientStatus.INACTIVE, clientAfterUpdated.getStatus());
    }

    @Test
    public void giveClientId_WhenItIsDeleted_ReturnsFalse () {

        Client client = new Client(null, "Taina Santos", "tcsantos@gmail,com", LocalDate.of(2005,06,23));

        Client clienteSaved = clientService.save(client);

        Client clienteSourched = clientService.findById(clienteSaved.getId());

        assertNotNull(clienteSourched);

        boolean isDeleted = clientService.deletePysicallyById(clienteSaved.getId());
        assertTrue(isDeleted);


        Optional<Client> clienteAfterUpdated = iClientRepository.findById(clienteSaved.getId());

        assertFalse(clienteAfterUpdated.isPresent());


    }




}