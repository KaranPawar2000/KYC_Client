package com.infinito.KYC.repo;

import com.infinito.KYC.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BranchRepository extends JpaRepository<Branch, Long> {



}
