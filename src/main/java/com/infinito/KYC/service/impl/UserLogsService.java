package com.infinito.KYC.service.impl;

import com.infinito.KYC.dto.Response;
import com.infinito.KYC.dto.UserLogsDTO;
import com.infinito.KYC.entity.UserLogs;
import com.infinito.KYC.repo.UserLogsRepository;
import com.infinito.KYC.service.interfac.IUserLogsService;
import com.infinito.KYC.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLogsService implements IUserLogsService {

    @Autowired
    UserLogsRepository userLogsRepository;

    @Autowired
    Utils utils;

    @Override
    public Response add(UserLogs userLogs) {
        Response response =new Response();
        userLogsRepository.save(userLogs);

        response.setStatusCode(200);
        response.setMessage("User log added successfully");
        response.setStatus(true);
        return response;
    }



    @Override
    public Response findLoginLogsByUserId(long userId) {
        // Use the repository method to fetch logs where 'logged in' text exists
        List<UserLogs> logList= userLogsRepository.findLoginLogsByUserId("logged in", userId);
        List<UserLogsDTO> userLogsDTOS =utils.convertUserLogListToUserLogDTOList(logList);
            Response response= new Response();
        if (!logList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Login logs retrieved successfully");
            response.setStatus(true);
            response.setUserLogs(userLogsDTOS);
        } else {
            response.setStatusCode(404);
            response.setMessage("No login logs found for the user");
            response.setStatus(false);
        }


        return response;
    }

    @Override
    public Response findActivityLogsByUserId(long userId) {
        List<UserLogs> logList= userLogsRepository.findActivityLogsByUserId("logged in", userId);
        List<UserLogsDTO> userLogsDTOS =utils.convertUserLogListToUserLogDTOList(logList);
        Response response= new Response();
        if (!logList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Login logs retrieved successfully");
            response.setStatus(true);
            response.setUserLogs(userLogsDTOS);
        } else {
            response.setStatusCode(404);
            response.setMessage("No login logs found for the user");
            response.setStatus(false);
        }


        return response;
    }

    @Override
    public Response findLoginLogs() {
        List<UserLogs> logList = userLogsRepository.findLoginLogs("logged in");
        List<UserLogs> filteredLogs = logList.stream()
                .filter(log -> log.getUser() != null)
                .collect(Collectors.toList());
        List<UserLogsDTO> userLogsDTOS = utils.convertUserLogListToUserLogDTOList(filteredLogs);

        Response response = new Response();
        if (!filteredLogs.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Login logs retrieved successfully");
            response.setStatus(true);
            response.setUserLogs(userLogsDTOS);
        } else {
            response.setStatusCode(404);
            response.setMessage("No login logs found for the user");
            response.setStatus(false);
        }

        return response;
    }

    @Override
    public Response findActivityLogs() {
        List<UserLogs> logList= userLogsRepository.findActivityLogs("logged in");
        List<UserLogsDTO> userLogsDTOS =utils.convertUserLogListToUserLogDTOList(logList);
        Response response= new Response();
        if (!logList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("Login logs retrieved successfully");
            response.setStatus(true);
            response.setUserLogs(userLogsDTOS);
        } else {
            response.setStatusCode(404);
            response.setMessage("No login logs found for the user");
            response.setStatus(false);
        }


        return response;
    }

}
