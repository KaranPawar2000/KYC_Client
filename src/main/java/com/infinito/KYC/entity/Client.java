package com.infinito.KYC.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_client_master")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    private String pincode;

    @Column(unique = true)
    private String emailId;

    @Column(unique = true)
    private String mobileNo;

    @Column(unique = true)
    private String password;


    private Byte status;
    private String orgName;
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private LocalDate expiryDate;
    private Integer clientCount;
    private String logo;


    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();


}