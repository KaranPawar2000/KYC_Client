    package com.infinito.KYC.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import org.hibernate.annotations.CreationTimestamp;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.GrantedAuthority;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.*;
    import java.util.stream.Collectors;

    @Data
    @EqualsAndHashCode(exclude = {"roles"})

    @Entity
    @Table(name="users")
    public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @NotBlank(message = "Email is required")
        @Column(unique = true)
        private String email;

        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Password is required")
        private String password;

        @NotBlank(message = "Phone Number is required")
        private String phoneNumber;

        @ManyToOne(fetch=FetchType.EAGER)
        @JoinColumn(name="branch_id")
        private Branch branch;


        @ManyToOne(fetch = FetchType.EAGER) // Many users can have one role
        @JoinColumn(name = "role_id", nullable = false) // Foreign key to Role
        private Role role; // Single role for the user

        private Boolean status;

        @CreationTimestamp
        @Column(name = "created_date", updatable = false)
        private LocalDate createdDate;

        @CreationTimestamp
        @Column(name = "created_time", updatable = false)
        private LocalTime createdTime;


        @Temporal(TemporalType.TIMESTAMP)
        private Date lastPasswordUpdate;


        @OneToMany(mappedBy = "user" ,fetch= FetchType.LAZY, cascade = CascadeType.ALL)
        private List<UserLogs> userLogs;

    //    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    //    private List<BranchWiseDetailedReport> reports = new ArrayList<>();


        // existing methods remain the same

        // Add a method to update the password and the last password update time
        public void updatePassword(String newPassword) {
            this.password = newPassword;
            this.lastPasswordUpdate = new Date(); // Set to current date/time
        }

        // Optionally, add a getter for lastPasswordUpdate
        public Date getLastPasswordUpdate() {
            return lastPasswordUpdate;
        }



        @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.getName()));

        }
        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
