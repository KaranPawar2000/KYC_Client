package com.infinito.KYC.service;

import com.infinito.KYC.dto.ClientDataTransferDTO;
import com.infinito.KYC.entity.Client;
import com.infinito.KYC.repo.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ClientImportService {

    private final ClientRepository clientRepository;

    public ClientImportService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Client importClient(ClientDataTransferDTO dto) {
        // check conflicts by email or mobile
        clientRepository.findByEmailId(dto.getEmailId()).ifPresent(existing -> {
            // choose to update or throw; here we update some fields
        });

        // Simple duplicate handling sample: if email exists, update that entity
        Client client = clientRepository.findByEmailId(dto.getEmailId())
                .orElseGet(Client::new);

        client.setAddress(dto.getAddress());
        client.setPincode(dto.getPincode());
        client.setEmailId(dto.getEmailId());
        client.setMobileNo(dto.getMobileNo());
        client.setPassword(dto.getPassword()); // consider encoding or encryption
        client.setStatus(dto.getStatus());
        client.setOrgName(dto.getOrgName());
        client.setCreatedModifiedDate(dto.getCreatedModifiedDate());
        client.setReadOnly(dto.getReadOnly());
        client.setArchiveFlag(dto.getArchiveFlag());
        client.setExpiryDate(dto.getExpiryDate());
        client.setClientCount(dto.getClientCount());
        client.setLogo(dto.getLogo());

        return clientRepository.save(client);
    }
}
