package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.AccountEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.AccountEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.AccountRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = AccountEntityMapper.toEntity(account);
        AccountEntity savedAccount = jpaAccountRepository.save(accountEntity);
        return AccountEntityMapper.toDomain(savedAccount);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return jpaAccountRepository.findById(id)
                .map(AccountEntityMapper::toDomain);
    }
}
