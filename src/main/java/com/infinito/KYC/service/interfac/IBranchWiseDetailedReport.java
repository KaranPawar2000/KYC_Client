package com.infinito.KYC.service.interfac;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.BranchWiseDetailedReport;

public interface IBranchWiseDetailedReport {

    Response addReport(BranchWiseDetailedReport branchWiseDetailedReport);

    Response getReport(Long branchId);

    Response getBranchWiseDetailedReport();

    Response getBranchWiseDetailedSummaryReport();

}
