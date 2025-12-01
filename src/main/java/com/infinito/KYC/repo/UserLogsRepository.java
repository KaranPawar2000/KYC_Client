package com.infinito.KYC.repo;

import com.infinito.KYC.entity.UserLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLogsRepository extends JpaRepository<UserLogs,Long> {

    // Query to find logs with 'logged in' for a specific user
    @Query("SELECT ul FROM UserLogs ul WHERE ul.log_text LIKE %:logText% AND ul.user.id = :userId")
    List<UserLogs> findLoginLogsByUserId(String logText, long userId);

    @Query("SELECT ul FROM UserLogs ul WHERE ul.log_text LIKE %:logText% ")
    List<UserLogs> findLoginLogs(String logText);

    @Query("SELECT ul FROM UserLogs ul WHERE ul.log_text NOT LIKE %:logText% AND ul.user.id = :userId")
    List<UserLogs> findActivityLogsByUserId(String logText, long userId);

    @Query("SELECT ul FROM UserLogs ul WHERE ul.log_text NOT LIKE %:logText% ")
    List<UserLogs> findActivityLogs(String logText);




}
