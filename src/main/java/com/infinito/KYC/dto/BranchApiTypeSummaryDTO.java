package com.infinito.KYC.dto;

import lombok.Data;

@Data
public class BranchApiTypeSummaryDTO {
    private String branchName;
    private long aadharGenerateOtpCount;
    private long aadharVerifyOtpCount;
    private long panVerificationCount;
    private long passportVerificationCount;
    private long voterIdVerificationCount;
    private long drivingLicenseVerificationCount;
    private long electricityVerificationCount;

    // Ensure that the constructor matches these fields
    public BranchApiTypeSummaryDTO(String branchName, long aadharGenerateOtpCount, long aadharVerifyOtpCount,
                                   long panVerificationCount, long passportVerificationCount,
                                   long voterIdVerificationCount, long drivingLicenseVerificationCount,
                                   long electricityVerificationCount) {
        this.branchName = branchName;
        this.aadharGenerateOtpCount = aadharGenerateOtpCount;
        this.aadharVerifyOtpCount = aadharVerifyOtpCount;
        this.panVerificationCount = panVerificationCount;
        this.passportVerificationCount = passportVerificationCount;
        this.voterIdVerificationCount = voterIdVerificationCount;
        this.drivingLicenseVerificationCount = drivingLicenseVerificationCount;
        this.electricityVerificationCount = electricityVerificationCount;
    }
}
