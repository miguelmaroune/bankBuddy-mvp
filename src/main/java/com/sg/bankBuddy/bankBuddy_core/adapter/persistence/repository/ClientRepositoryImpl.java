package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.ClientEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.ClientEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.ClientRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final JpaClientRepository jpaClientRepository;

    public ClientRepositoryImpl(JpaClientRepository jpaClientRepository) {
        this.jpaClientRepository = jpaClientRepository;
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = ClientEntityMapper.toEntity(client);
        ClientEntity savedClientEntity = jpaClientRepository.save(clientEntity);
        return ClientEntityMapper.toDomain(savedClientEntity);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaClientRepository.findById(id)
                .map(ClientEntityMapper::toDomain);
    }
}
