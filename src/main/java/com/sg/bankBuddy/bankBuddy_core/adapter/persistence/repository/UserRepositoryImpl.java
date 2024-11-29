package com.sg.bankBuddy.bankBuddy_core.adapter.persistence.repository;

import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.entity.UserEntity;
import com.sg.bankBuddy.bankBuddy_core.adapter.persistence.mapper.UserEntityMapper;
import com.sg.bankBuddy.bankBuddy_core.application.port.outbound.UserRepository;
import com.sg.bankBuddy.bankBuddy_core.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository userRepository;

    public UserRepositoryImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntityMapper.toEntity(user);
        UserEntity savedUSer =  userRepository.save(userEntity);
        return UserEntityMapper.toDomain(savedUSer);
    }
}
