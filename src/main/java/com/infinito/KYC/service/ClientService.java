package com.infinito.KYC.service;

import com.infinito.KYC.dto.ClientDataTransferDTO;
import com.infinito.KYC.entity.Client;
import com.infinito.KYC.repo.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Target app — service (map DTO -> entity and save)
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder; // if you store encoded passwords

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client createFromDto(ClientDataTransferDTO dto) {
        Client c = new Client();
        c.setAddress(dto.getAddress());
        c.setPincode(dto.getPincode());
        c.setEmailId(dto.getEmailId());
        c.setMobileNo(dto.getMobileNo());
        // password: encode or handle appropriately
        if (dto.getPassword() != null) {
            c.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        c.setStatus(dto.getStatus());
        c.setOrgName(dto.getOrgName());
        c.setCreatedModifiedDate(dto.getCreatedModifiedDate());
        c.setReadOnly(dto.getReadOnly());
        c.setArchiveFlag(dto.getArchiveFlag());
        c.setExpiryDate(dto.getExpiryDate());
        c.setClientCount(dto.getClientCount());
        c.setLogo(dto.getLogo());
        // If you need to save branches or admins, map them here (but be careful with cascade and duplicates).
        return clientRepository.save(c);
    }
}

