package com.infinito.KYC.service.impl;


import com.infinito.KYC.dto.BranchApiTypeSummaryDTO;
import com.infinito.KYC.dto.BranchWiseDetailedReportDTO;
import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.BranchWiseDetailedReport;
import com.infinito.KYC.repo.BranchRepository;
import com.infinito.KYC.repo.BranchWiseDetailedReportRepository;
import com.infinito.KYC.service.interfac.IBranchWiseDetailedReport;
import com.infinito.KYC.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchWiseDetailedReportService implements IBranchWiseDetailedReport {

    @Autowired
    Utils utils;

    @Autowired
    BranchWiseDetailedReportRepository branchWiseDetailedReportRepository;

    @Autowired
    BranchRepository branchRepository;


    @Override
    public Response addReport(BranchWiseDetailedReport branchWiseDetailedReport) {

        branchWiseDetailedReportRepository.save(branchWiseDetailedReport);
        Response response = new Response();
        response.setStatusCode(200);
        response.setMessage("report successfully stored");


        return response;
    }

    @Override
    public Response getReport(Long branchId) {
        List<BranchWiseDetailedReport> branchWiseDetailedReports = branchWiseDetailedReportRepository.findByBranchId(branchId);

        List<BranchWiseDetailedReportDTO> branchWiseDetailedReportDTOList = utils.convertBranchWiseDetailedReportListToBranchWiseDetailedReportDTOList(branchWiseDetailedReports);

        Response response = new Response();
        if (!branchWiseDetailedReports.isEmpty()) {
            response.setBranchWiseDetailedReportDTOList(branchWiseDetailedReportDTOList);
            response.setStatusCode(200);
            response.setMessage("Reports found for branch ID: " + branchId);
        } else {
            response.setStatusCode(404);
            response.setMessage("No reports found for branch ID: " + branchId);
        }

        return response;
    }

    @Override
    public Response getBranchWiseDetailedReport() {
        // Fetching the branch-wise report summary with counts for specific API types
        List<BranchApiTypeSummaryDTO> dtoList = branchWiseDetailedReportRepository.getBranchApiTypeSummary();

        // Preparing the response
        Response response = new Response();
        if (!dtoList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Branch-wise detailed report retrieved successfully.");
            response.setData(dtoList);  // Assuming Response class has a setData() method that accepts List<BranchApiTypeSummaryDTO>
        } else {
            response.setStatusCode(404);
            response.setMessage("No branch-wise reports found.");
        }

        return response;
    }

    @Override
    public Response getBranchWiseDetailedSummaryReport() {
        Response response =new Response();
        List<BranchWiseDetailedReport> branchWiseDetailedReports = branchWiseDetailedReportRepository.findAll();
        List<BranchWiseDetailedReportDTO> branchWiseDetailedReportDTOList=  utils.convertBranchWiseDetailedReportListToBranchWiseDetailedReportDTOList(branchWiseDetailedReports);
        response.setBranchWiseDetailedReportDTOList(branchWiseDetailedReportDTOList);
        response.setMessage("success");
        response.setStatusCode(200);
        return response;
    }

}
