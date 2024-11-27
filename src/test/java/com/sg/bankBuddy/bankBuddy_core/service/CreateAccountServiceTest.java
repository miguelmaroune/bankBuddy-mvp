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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        MockitoAnnotations.openMocks(this);

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
        assert (result != null);
        assert (result.getClient() != null);
    }

    @Test
    void testCreateAccount_ClientNotFound() {

        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());


        try {
            createAccountService.createAccount(account);
        } catch (ClientNotFoundException e) {

            assert (Objects.equals(e.getCode(), BankBudyErrorCodes.CLIENT_NOT_FOUND.getCode()));
        }


        verify(clientRepository).findById(anyLong());

        verify(accountRepository, never()).save(any(Account.class));
    }
}
