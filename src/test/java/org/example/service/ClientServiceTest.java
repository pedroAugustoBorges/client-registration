package org.example.service;

import org.example.domain.Client;
import org.example.enumeration.ClientStatus;
import org.example.exceptions.EntityNotFoundException;
import org.example.repository.imp.ClientRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest{
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;


    @Test
    public void shouldSaveClientWhenEntityWasInstantiated() {
        Client client = new Client(1, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2024, 2, 24));

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client clientSaved = clientService.save(client);

        assertNotNull(clientSaved, "Client saved can't be null");
        assertEquals("pedroameloborges@gmail.com" ,clientSaved.getEmail(), "Check if email provided is equals as the one saved ");

    }

    @Test
    public void shouldThrowIllegalArgumentExceptionnWhenClientItIsNull () {

    assertThrows(IllegalArgumentException.class, () -> clientService.save(null));
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdItIsLessThan1() {

        assertThrows(IllegalArgumentException.class, () -> clientService.findById(0));

    }
    @Test
    public void shouldFindClientById () {
        Client client = new Client(1, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2024, 2, 24));

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Client clientFounded = clientService.findById(1);

        assertNotNull(clientFounded);

        assertEquals(clientFounded.getName(), "Pedro Augusto");

        verify(clientRepository).findById(1);
    }

    @Test
    public void givenClientList_WhenFiltered_ThatResultsOnlyClientActive () {

        Client client1 = new Client(1, "Pedro Augusto", "pedroameloborges@gmail", LocalDate.of(2024, 2, 24));
        Client client2 = new Client(2, "Vanessa Jucca", "vanju17@gmail.com", LocalDate.of(1981, 9, 30));
        Client client3 = new Client(4, "Fabio luis", "fabiolb329@gmail.com@gmail", LocalDate.of(1973, 8, 2));

        List<Client> clientList = new ArrayList<>(List.of(client1, client2, client3));

        when(clientRepository.listActiveClient()).thenReturn(clientList);


        List<Client> clientsActive = clientRepository.listActiveClient();

        for (Client client : clientsActive){
            assertEquals(ClientStatus.ACTIVE, client.getStatus());
        }
    }


    @Test
    public void findById_ThrowEntityNotFoundException_WhenClientDoesNotExit () {

        Integer clientId = 1;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.findById(clientId));

        verify(clientRepository).findById(clientId);

    }

}