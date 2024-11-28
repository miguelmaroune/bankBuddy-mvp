package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.AccountEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.AccountEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Account save(Account account) {
        //todo: should we allow the creation of multiple accounts for the same client with the same Account type?
        AccountEntity accountEntity = AccountEntityMapper.toEntity(account);
        AccountEntity savedAccount = jpaAccountRepository.save(accountEntity);
        return AccountEntityMapper.toDomain(savedAccount);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return jpaAccountRepository.findById(id)
                .map(AccountEntityMapper::toDomain);
    }
}
