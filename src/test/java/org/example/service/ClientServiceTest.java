package org.example.service;

import org.example.domain.Client;
import org.example.enumeration.ClientStatus;
import org.example.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;


    @Test
    public void shouldSaveClientWhenEntityWasInstantiated() {
        Client client = new Client(1, "Pedro Augusto", "pedroameloborges@gmail.com", LocalDate.of(2024, 2, 24), ClientStatus.ACTIVE);

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);

        Client clientSaved = clientService.save(client);



        Assertions.assertNotNull(clientSaved);
        Assertions.assertEquals(clientSaved.getEmail(), "pedroameloborges@gmail.com");


        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Assertions.assertEquals(clientRepository.findById(1).get().getName(), "Pedro Augusto");

    }




}