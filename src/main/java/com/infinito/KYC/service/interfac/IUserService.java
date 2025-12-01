package com.infinito.KYC.service.interfac;

import com.infinito.KYC.dto.LoginRequest;
import com.infinito.KYC.dto.Response;
import com.infinito.KYC.dto.UserDTO;
import com.infinito.KYC.entity.User;

public interface IUserService {

        Response register(User user);

        Response login(LoginRequest loginRequest);

        Response getUsers();

        Response getUserById(Long id);

        Response updateUser(Long id,User user);

        Response updateUserPassword(Long id, String password);



}
