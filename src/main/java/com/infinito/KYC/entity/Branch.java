    package com.infinito.KYC.entity;

    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.ArrayList;
    import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "branch")
    public class Branch {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


        @NotBlank(message = "Email is required")
        @Column(unique = true)
        private String name;


        @NotBlank(message = "Email is required")
        @Column(unique = true)
        private String code;

        private Boolean status;

        @OneToMany(mappedBy = "branch",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private List<BranchWiseDetailedReport> reports = new ArrayList<>();

        @OneToMany(mappedBy = "branch",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private List<User> users = new ArrayList<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "client_id")   // foreign key in branch table
        private Client client;



    }
