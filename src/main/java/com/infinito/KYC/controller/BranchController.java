package com.infinito.KYC.controller;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.service.interfac.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private IBranchService branchService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> register(@RequestBody Branch branch){
        Response response = branchService.add(branch);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        Response response = branchService.updateBranch(id, branch);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> getBranch(){
        System.out.println();
        Response response = branchService.getAllBranch();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> getBranchById(@PathVariable Long id) {
        Response response = branchService.getBranchById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
