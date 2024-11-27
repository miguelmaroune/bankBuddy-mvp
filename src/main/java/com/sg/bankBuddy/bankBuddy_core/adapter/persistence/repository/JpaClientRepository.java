package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
}
