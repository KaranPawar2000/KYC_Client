package com.infinito.KYC.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="detailed_report")
public class BranchWiseDetailedReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name ="branch_id")
    private Branch branch;



    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String api_type;

    private String message;

    private boolean status_code;

    private String api_request_body;




}
