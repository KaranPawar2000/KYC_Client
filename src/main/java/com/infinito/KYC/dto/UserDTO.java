package com.infinito.KYC.dto;

import com.infinito.KYC.entity.Branch;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String branch;
    private String role;
    private Boolean status;
}
