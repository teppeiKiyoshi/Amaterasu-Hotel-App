package com.personalproj.amaterasuhotel.repository;

import com.personalproj.amaterasuhotel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<UserModel> findByEmail(String email);
}
