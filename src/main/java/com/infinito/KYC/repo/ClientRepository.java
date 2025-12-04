package com.infinito.KYC.repo;

import com.infinito.KYC.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmailId(String email);

    Optional<Client> findByMobileNo(String mobile);


}
