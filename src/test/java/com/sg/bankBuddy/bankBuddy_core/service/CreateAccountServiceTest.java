package com.sg.bankBuddy.bankBuddy_core.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.ClientRepository;
import com.sg.bankBuddy.bankBuddy_core.application.service.CreateAccountService;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.ClientNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountServiceTest {

    @InjectMocks
    private CreateAccountService createAccountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    private Client client;
    private Account account;

    @BeforeEach
    public void setUp() {
        client = new Client();
        client.setId(1L);

        account = new Account();
        account.setClient(client);
    }

    @Test
    void testCreateAccount_Success() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = createAccountService.createAccount(account);

        verify(clientRepository).findById(anyLong());
        verify(accountRepository).save(any(Account.class));
        assertNotNull(result);
        assertNotNull(result.getClient());
    }

    @Test
    void testCreateAccount_ClientNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> {
            createAccountService.createAccount(account);
        });
        assertEquals(BankBudyErrorCodes.CLIENT_NOT_FOUND.getCode(), exception.getCode());

        verify(clientRepository).findById(anyLong());
        verify(accountRepository, never()).save(any(Account.class));
    }
}
