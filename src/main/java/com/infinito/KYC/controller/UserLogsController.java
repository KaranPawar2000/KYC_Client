package com.infinito.KYC.controller;


import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.UserLogs;
import com.infinito.KYC.service.impl.UserLogsService;
import com.infinito.KYC.service.interfac.IUserLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class UserLogsController {

    @Autowired
    IUserLogsService  userLogsService;

    @PostMapping("/add")
    public ResponseEntity<Response> addLogs(@RequestBody UserLogs userLogs){
        Response response =userLogsService.add(userLogs);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

        @GetMapping("get_log_in_logs")
        @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
        public ResponseEntity<Response> getLoginLogs(){
            Response response =userLogsService.findLoginLogs();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

    @GetMapping("get_log_in_logs/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> getLoginLogsBYId(@PathVariable long id){
        Response response =userLogsService.findLoginLogsByUserId(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("get_activity_logs")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> getActivityLogsById(){
        Response response =userLogsService.findActivityLogs();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @GetMapping("get_activity_logs/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Response> getActivityLogsById(@PathVariable long id){
        Response response =userLogsService.findActivityLogsByUserId(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
