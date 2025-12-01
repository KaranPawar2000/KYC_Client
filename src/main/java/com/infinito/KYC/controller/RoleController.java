package com.infinito.KYC.controller;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Role;
import com.infinito.KYC.service.interfac.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")  // or hasAuthority('ROLE_ADMIN')
    public ResponseEntity<Response> register(@RequestBody Role role) {
        Response response = roleService.add(role);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> register(){
        Response response = roleService.getAllRoles();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
}
