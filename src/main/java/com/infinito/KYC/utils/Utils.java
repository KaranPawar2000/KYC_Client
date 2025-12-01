package com.infinito.KYC.utils;

import com.infinito.KYC.dto.*;
import com.infinito.KYC.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Utils {

    public  BranchDTO convertToBranchDTO(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setId(branch.getId());
        branchDTO.setName(branch.getName());
        branchDTO.setCode(branch.getCode());
        branchDTO.setStatus(branch.getStatus());
        return branchDTO;
    }
    public  UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO =new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setBranch(user.getBranch().getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus());
        // Add null check for role
        if (user.getRole() != null) {
            userDTO.setRole(user.getRole().getName());
        } else {
            userDTO.setRole("No role assigned");
        }
        return userDTO;
    }

    public UserLogsDTO convertUserLogsToUserLogDTO(UserLogs userLogs){
        UserLogsDTO userLogsDTO =new UserLogsDTO();
        userLogsDTO.setDate(userLogs.getDate());
        userLogsDTO.setUserName(userLogs.getUser().getName());
        userLogsDTO.setLogText(userLogs.getLog_text());
        return userLogsDTO;
    }

    public BranchWiseDetailedReportDTO convertBranchWiseDetailedReportToBranchWiseDetailedReportDTO(BranchWiseDetailedReport branchWiseDetailedReport) {

        BranchWiseDetailedReportDTO branchWiseDetailedReportDTO = new BranchWiseDetailedReportDTO();

        branchWiseDetailedReportDTO.setBranch_name(branchWiseDetailedReport.getBranch().getName());
        branchWiseDetailedReportDTO.setLocalDateTime(branchWiseDetailedReport.getCreatedAt());
        branchWiseDetailedReportDTO.setApi_type(branchWiseDetailedReport.getApi_type());
        branchWiseDetailedReportDTO.setMessage(branchWiseDetailedReport.getMessage());
        branchWiseDetailedReportDTO.setStatus_code(branchWiseDetailedReport.isStatus_code());
        branchWiseDetailedReportDTO.setApi_request_body(branchWiseDetailedReport.getApi_request_body());

        return branchWiseDetailedReportDTO;
    }

    public List<BranchWiseDetailedReportDTO> convertBranchWiseDetailedReportListToBranchWiseDetailedReportDTOList(List<BranchWiseDetailedReport> branchWiseDetailedReportList) {

        return branchWiseDetailedReportList.stream()
                .map(this::convertBranchWiseDetailedReportToBranchWiseDetailedReportDTO)
                .collect(Collectors.toList());

    }

        public List<UserLogsDTO> convertUserLogListToUserLogDTOList(List<UserLogs> userLogs){
        return userLogs.stream()
                .map(this::convertUserLogsToUserLogDTO) // Use the convertToDTO method for each Branch
                .collect(Collectors.toList());
    }

    public List<BranchDTO> convertToDTOList(List<Branch> branches) {
        return branches.stream()
                .map(this::convertToBranchDTO) // Use the convertToDTO method for each Branch
                .collect(Collectors.toList());
    }

    public List<UserDTO> convertUsersListToUserDTOList(List<User> usersList) {
        return usersList.stream()
                .map(this::convertUserToUserDTO) // Use the convertToDTO method for each Branch
                .collect(Collectors.toList());
    }

    public RoleDTO convertRoleToRoleDTO (Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public List<RoleDTO> convertRoleListToRoleDTOList (List<Role> roleList) {
        return roleList.stream()
                .map(this::convertRoleToRoleDTO) // Use the convertToDTO method for each Role
                .collect(Collectors.toList());
    }

}
