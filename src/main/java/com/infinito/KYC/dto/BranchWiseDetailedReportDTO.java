package com.infinito.KYC.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BranchWiseDetailedReportDTO {

    private String branch_name;

    private LocalDateTime localDateTime;

    private String api_type;

    private String message;

    private boolean status_code;

    private String api_request_body;
}
