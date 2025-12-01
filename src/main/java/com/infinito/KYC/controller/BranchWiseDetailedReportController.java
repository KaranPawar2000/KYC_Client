package com.infinito.KYC.controller;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.BranchWiseDetailedReport;
import com.infinito.KYC.service.interfac.IBranchWiseDetailedReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class BranchWiseDetailedReportController {

    @Autowired
    IBranchWiseDetailedReport iBranchWiseDetailedReport;

     @PostMapping("/add")
    public ResponseEntity<Response> addReport(@RequestBody BranchWiseDetailedReport branchWiseDetailedReport){
        Response response = iBranchWiseDetailedReport.addReport(branchWiseDetailedReport);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }


    @GetMapping("/get/{branchId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getReport(@PathVariable Long branchId){
        Response response = iBranchWiseDetailedReport.getReport(branchId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get_branch_wise_summary")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getBranchWiseDetailedSummary(){
        Response response = iBranchWiseDetailedReport.getBranchWiseDetailedReport();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get_branch_wise_report")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getBranchWiseDetailedReport(){
        Response response = iBranchWiseDetailedReport.getBranchWiseDetailedSummaryReport();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
