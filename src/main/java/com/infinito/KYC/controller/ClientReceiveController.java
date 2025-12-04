package com.infinito.KYC.controller;

import com.infinito.KYC.dto.ClientDataTransferDTO;
import com.infinito.KYC.entity.Client;
import com.infinito.KYC.service.ClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClientReceiveController {

    private final ClientService clientService;

    public ClientReceiveController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveClient(@RequestBody ClientDataTransferDTO dto) {


        try {
            Client saved = clientService.createFromDto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", saved.getId()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Duplicate unique field"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
