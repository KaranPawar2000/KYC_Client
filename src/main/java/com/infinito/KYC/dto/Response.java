package com.infinito.KYC.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.Role;
import com.infinito.KYC.entity.UserLogs;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int statusCode;
    private String message;

    private String token;
    private String expirationTime;
    private String email;
    private UserDTO user;
    private boolean status;
    private Branch branch;
    private BranchDTO branchDTO;
    private UserDTO userDTO;
    private List<BranchDTO> branchDTOList;
    private List<UserDTO> userDTOList;
    private Role role;
    private List<RoleDTO> roleDTOList;
    private String roleName;
    private Long userId;
    private String userName;
    private List<UserLogsDTO> userLogs;
    private Long branchId;

    List<BranchWiseDetailedReportDTO> branchWiseDetailedReportDTOList;

    // Generic field to store any additional data
    private Object data;

}
