package org.example.service;

import org.example.domain.Client;
import org.example.enumeration.ClientStatus;
import org.example.repository.ClientRepository;
import org.example.repository.IClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientRepository clientRepository;


    @Test
    public void shouldSaveClientWhenEntityWasInstantiated() {
        Client client = new Client(1, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2024, 2, 24));

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client clientSaved = clientService.save(client);

        Assertions.assertNotNull(clientSaved);
        Assertions.assertEquals(clientSaved.getEmail(), "pedroameloborges@gmail.com");

    }

    @Test
    public void shouldThrowNullPointerExceptionWhenClientItIsNull () {

        Assertions.assertThrows(NullPointerException.class, () -> clientService.save(null));
    }


    @Test
    public void shouldThrowIllegalArgumentExceptionWhenIdItIsLessThan1() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.findById(0));

    }
    @Test
    public void shouldFindClientById () {
        Client client = new Client(1, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2024, 2, 24));

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Client clientFounded = clientService.findById(1);

        Assertions.assertTrue(clientFounded != null);

        Assertions.assertEquals(clientFounded.getName(), "Pedro Augusto");

        Mockito.verify(clientRepository).findById(1);
    }




}