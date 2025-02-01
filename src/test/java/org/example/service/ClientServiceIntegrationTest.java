package org.example.service;

import org.example.domain.Client;
import org.example.enumeration.ClientStatus;
import org.example.exceptions.EntityNotFoundException;
import org.example.repository.imp.ClientRepository;
import org.example.repository.IClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
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


    /**
     * Integration test to verify that client's status is updated to INACTIVE when deleted
     *
     * Context:
     * 1. Create a new Client instance
     * 2. Persistence it in the database
     * 3. Verify if your initial status is ACTIVE
     * 4. Delete the client by ID
     * 5. Retrive the client again and verify that the status has been updated to INACTIVE
     *
     */

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
    public void giveClientId_WhenItIsDeleted_ReturnsFalse() {

        Client client = new Client(null, "Taina Santos", "tcsantos@gmail,com", LocalDate.of(2005, 06, 23));

        Client clientSaved = clientService.save(client);

        Client clientSourched = clientService.findById(clientSaved.getId());

        assertNotNull(clientSourched);

        boolean isDeleted = clientService.deletePysicallyById(clientSaved.getId());
        assertTrue(isDeleted);


        Optional<Client> clientAfterUpdated = iClientRepository.findById(clientSaved.getId());

        assertFalse(clientAfterUpdated.isPresent());

    }

    @Test
    public void givenClientList_WhenFiltered_ThatResultsOnlyClientInactive () {
        Client client1 = new Client(null, "Pedro Augusto", "pedroameloborges@gmail", LocalDate.of(2024, 2, 24));
        Client client2 = new Client(null, "Vanessa Jucca", "vanju17@gmail.com", LocalDate.of(1981, 9, 30));
        Client client3 = new Client(null, "Fabio luis", "fabiolb329@gmail.com@gmail", LocalDate.of(1973, 8, 2));

        Client clienteSaved1 = clientService.save(client1);
        Client clienteSaved2 = clientService.save(client2);

        boolean isDeleted = clientService.deleteClientById(client1.getId());
        assertTrue(isDeleted);

        List<Client> clientsInactive = clientService.listInactiveClient();

        assertEquals(1, clientsInactive.size());

        clientService.deleteClientById(client2.getId());

        List<Client> listAfterAllDelete = clientService.listInactiveClient();

        assertEquals(2, listAfterAllDelete.size());

        for (Client client : listAfterAllDelete){
            assertEquals(ClientStatus.INACTIVE, client.getStatus());
        }

    }


    @Test
    public void shouldReturnAllClientsWithStatusActiveWhenQueried () {


        Client client1 = new Client(null, "Pedro Augusto", "pedroameloborges@gmail", LocalDate.of(2024, 2, 24));
        Client client2 = new Client(null, "Vanessa Jucca", "vanju17@gmail.com", LocalDate.of(1981, 9, 30));
        Client client3 = new Client(null, "Fabio luis", "fabiolb329@gmail.com@gmail", LocalDate.of(1973, 8, 2));

        clientService.save(client1);
        clientService.save(client2);
        clientService.save(client3);

        List<Client> clientsActive = clientService.listActiveClient();

        for (Client client : clientsActive){
            assertEquals(ClientStatus.ACTIVE, client.getStatus());
        }

        boolean isDeleted = clientService.deleteClientById(client3.getId());

        List<Client> clientsActiveAfterDeleted = clientService.listActiveClient();

        assertEquals(2, clientsActiveAfterDeleted.size());
    }

    @Test
    public void shouldReturnClientNotExistAfterDeletePysically () {
        Client client = new Client(null, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2001, 04, 13));

        Client clientSaved = clientService.save(client);

        List<Client> clients = clientService.listAllClient();

        assertEquals(1, clients.size());

        boolean isDeleted = clientService.deletePysicallyById(clientSaved.getId());

        assertTrue(isDeleted);

        assertThrows(EntityNotFoundException.class, () -> clientService.findById(clientSaved.getId()));
    }



}