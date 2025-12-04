package com.infinito.KYC.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class ClientDataTransferDTO {

    private Integer id;
    private String address;
    private String pincode;
    private String emailId;
    private String mobileNo;
    private String password;
    private Byte status;
    private String orgName;
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private LocalDate expiryDate;
    private Integer clientCount;
    private String logo;

}
