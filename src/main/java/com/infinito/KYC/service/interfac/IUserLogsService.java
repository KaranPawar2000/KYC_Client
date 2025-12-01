package com.infinito.KYC.service.interfac;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.entity.UserLogs;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserLogsService {

    Response add(UserLogs userLogs);


    // Query to find logs with 'logged in' for a specific user

    Response findLoginLogsByUserId(long userId);

    Response findActivityLogsByUserId(long userId);


    Response findLoginLogs();

    Response findActivityLogs();
}
