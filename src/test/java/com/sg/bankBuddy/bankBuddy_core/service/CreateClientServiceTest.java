package com.sg.bankBuddy.bankBuddy_core.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.ClientRepository;
import com.sg.bankBuddy.bankBuddy_core.application.service.CreateClientService;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateClientServiceTest {

    @InjectMocks
    private CreateClientService createClientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setId(1L);
    }

    @Test
    void testCreateClient_Success() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = createClientService.createClient(client);

        verify(clientRepository).save(any(Client.class));
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testCreateClient_ClientSaveFailure() {
        when(clientRepository.save(any(Client.class))).thenReturn(null);

        Client result = createClientService.createClient(client);

        assertNull(result);
    }
}
