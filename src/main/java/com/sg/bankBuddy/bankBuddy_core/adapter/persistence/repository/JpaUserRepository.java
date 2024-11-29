package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
}
