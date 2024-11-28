package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.CreateAccountUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.ClientRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.ClientNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import org.springframework.stereotype.Service;

@Service
public class CreateAccountService implements CreateAccountUseCase {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public CreateAccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Account createAccount(Account account) {
        //todo : Add account Factory to create multiple types of accounts.
        Client client = clientRepository.findById(account.getClient().getId())
                .orElseThrow(() -> new ClientNotFoundException(BankBudyErrorCodes.CLIENT_NOT_FOUND));
        account.setClient(client);
        return accountRepository.save(account);
    }
}
