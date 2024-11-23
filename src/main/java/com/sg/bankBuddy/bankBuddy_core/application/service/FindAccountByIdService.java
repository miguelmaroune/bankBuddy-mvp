package com.sg.bankBuddy.bankBuddy_core.application.service;

import com.sg.bankBuddy.bankBuddy_core.application.port.inbound.FindAccountByIdUseCase;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.AccountNotFoundException;
import com.sg.bankBuddy.bankBuddy_core.domain.exception.BankBudyErrorCodes;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import org.springframework.stereotype.Service;

@Service
public class FindAccountByIdService implements FindAccountByIdUseCase {
    private final AccountRepository accountRepository;

    public FindAccountByIdService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(BankBudyErrorCodes.ACCOUNT_NOT_FOUND));
    }
}
