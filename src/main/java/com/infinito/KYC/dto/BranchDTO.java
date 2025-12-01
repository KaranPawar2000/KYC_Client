package com.infinito.KYC.dto;

import lombok.Data;

@Data
public class BranchDTO {
    private long id;
    private String name;
    private String code;
    private Boolean status;
}
