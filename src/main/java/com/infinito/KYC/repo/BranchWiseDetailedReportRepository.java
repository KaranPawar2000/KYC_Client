package com.infinito.KYC.repo;

import com.infinito.KYC.dto.BranchApiTypeSummaryDTO;
import com.infinito.KYC.entity.BranchWiseDetailedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Repository
public interface BranchWiseDetailedReportRepository extends JpaRepository<BranchWiseDetailedReport, Long> {

    List<BranchWiseDetailedReport> findByBranchId(Long branchId);

    @Query("SELECT new com.infinito.KYC.dto.BranchApiTypeSummaryDTO(b.name, " +
            "SUM(CASE WHEN d.api_type = 'ADHAR GENRATE OTP' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'VERIFY ADHAR OTP' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'PAN_VERIFICATION' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'PASSPORT' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'VOTER ID' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'DRIVING LICENCE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN d.api_type = 'ELECTRICITY' THEN 1 ELSE 0 END)) " +
            "FROM BranchWiseDetailedReport d JOIN d.branch b " +
            "GROUP BY b.name")
    List<BranchApiTypeSummaryDTO> getBranchApiTypeSummary();
}
