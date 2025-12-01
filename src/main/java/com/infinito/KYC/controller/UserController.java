package com.infinito.KYC.controller;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.User;
import com.infinito.KYC.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getUsers(){
    Response response =userService.getUsers();
    return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")  // or hasAuthority('ROLE_ADMIN')
    public ResponseEntity<Response> getBranchById(@PathVariable Long id) {
        Response response = userService.getUserById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")  // or hasAuthority('ROLE_ADMIN')
    public ResponseEntity<Response> updateBranch(@PathVariable Long id, @RequestBody User user) {
        Response response = userService.updateUser(id, user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-password/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateUserPassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newPassword = body.get("newPassword");
        System.out.println("newPassword: " + newPassword);
        Response response = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
