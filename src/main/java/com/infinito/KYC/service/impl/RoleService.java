package com.infinito.KYC.service.impl;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.dto.RoleDTO;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.Role;
import com.infinito.KYC.repo.RoleRepository;
import com.infinito.KYC.service.interfac.IRoleService;
import com.infinito.KYC.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Utils utils;

    @Override
    public Response add(Role role) {
        Response response = new Response();
        try {
            // Save the role using RoleRepository
            roleRepository.save(role);
            response.setStatusCode(200);
            response.setMessage("Role added successfully");
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Role with this name already exists: " + e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Error occurred during role registration: " + e.getMessage());
            response.setStatusCode(500);  // Set 500 for server error
        }
        return response;
    }

    public Response getAllRoles(){
        Response response = new Response();
        try{
            List<Role> roleList = roleRepository.findAll();  // Get the List from repository
            List<RoleDTO> roleDTOList = utils.convertRoleListToRoleDTOList(roleList);
            response.setMessage("successful");
            response.setRoleDTOList(roleDTOList);
            response.setStatusCode(200);

        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting branches"+ e.getMessage());
        }
        return response;
    }
}
