package com.infinito.KYC.service.impl;

import com.infinito.KYC.dto.BranchDTO;
import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.User;
import com.infinito.KYC.exception.OurException;
import com.infinito.KYC.repo.BranchRepository;
import com.infinito.KYC.service.interfac.IBranchService;
import com.infinito.KYC.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;


@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
private Utils utils;

    @Override
    public Response add(Branch branch) {
        Response response = new Response();
        try {
            branchRepository.save(branch);
            response.setStatusCode(200);
            response.setMessage("Branch added successfully");
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Branch with this name or code already exists: " + e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Error occurred during branch registration: " + e.getMessage());
            response.setStatusCode(400);
        }
        return response;
    }

    @Override
    public Response getAllBranch() {
        Response response = new Response();
        try{
           List<Branch> branchList = branchRepository.findAll();
           List<BranchDTO> branchDTOList = utils.convertToDTOList(branchList); // Use the Utils method

            response.setStatusCode(200);
           response.setMessage("successful");
           response.setBranchDTOList(branchDTOList);
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting branches"+ e.getMessage());
        }
        return response;
    }
    @Override
    public Response getBranchById(Long id) {
        Response response = new Response();
        try {
            Branch branch = branchRepository.findById(id).orElseThrow(() -> new OurException("Branch not found"));
            if (branch != null) {
                BranchDTO branchDTO = utils.convertToBranchDTO(branch);
                response.setStatusCode(200);
                response.setMessage("Branch found");
                response.setBranchDTO(branchDTO);
                response.setStatus(true);
            } else {
                response.setStatusCode(404);
                response.setMessage("Branch not found");
                response.setStatus(false);

            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching branch: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateBranch(Long id, Branch updatedBranch) {
        Response response = new Response();
        try {
            Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new OurException("Branch not found"));

            // Update fields
            existingBranch.setName(updatedBranch.getName());
            existingBranch.setCode(updatedBranch.getCode());
            existingBranch.setStatus(updatedBranch.getStatus());

            // Save updated branch
            branchRepository.save(existingBranch);

            response.setStatusCode(200);
            response.setMessage("Branch updated successfully");
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Branch with this name or code already exists: " + e.getMessage());
            response.setStatusCode(400);
        } catch (Exception e) {
            response.setMessage("Error occurred during branch update: " + e.getMessage());
            response.setStatusCode(400);
        }
        return response;
    }

}
