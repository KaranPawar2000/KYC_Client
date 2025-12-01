package com.infinito.KYC.service.impl;
import com.infinito.KYC.dto.BranchDTO;
import com.infinito.KYC.utils.Utils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;

import com.infinito.KYC.dto.LoginRequest;
import com.infinito.KYC.dto.Response;
import com.infinito.KYC.dto.UserDTO;
import com.infinito.KYC.entity.Branch;
import com.infinito.KYC.entity.Role;
import com.infinito.KYC.entity.User;
import com.infinito.KYC.exception.OurException;
import com.infinito.KYC.repo.BranchRepository;
import com.infinito.KYC.repo.RoleRepository;
import com.infinito.KYC.repo.UserRepository;
import com.infinito.KYC.service.interfac.IUserService;
import com.infinito.KYC.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Utils utils;

    @Override
    public Response register(User user) {
        System.out.println("Registering User: " + user);

        Response response = new Response();
        try {

            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException(user.getEmail() + " already exists");
            }

            // Fetch and assign roles based on the incoming user data
            if (user.getRole() != null) {
                Role existingRole = roleRepository.findById(user.getRole().getId())
                                    .orElseThrow(() -> new OurException("Role not found: " + user.getRole().getId()));
                user.setRole(existingRole);

            } else {
                throw new OurException("No roles assigned to the user");
            }

            Branch branch = branchRepository.findById(user.getBranch().getId())
                    .orElseThrow(() -> new OurException("Branch not found"));
            System.out.println(user.getPassword());
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            response.setStatusCode(200);
            response.setMessage("User registered successfully");
            response.setEmail(user.getEmail());
            response.setStatus(true);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try {
            System.out.println("authentication started");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            System.out.println("authentication successful");
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User Not Found"));
            String role=user.getRole().getName();
            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRoleName(role);
            response.setUserId(user.getId());
            response.setUserName(user.getName());
            response.setBranchId(user.getBranch().getId());
            response.setExpirationTime("7 Days");
            response.setEmail(user.getEmail());
            response.setMessage("successful");
            response.setUserId(user.getId());

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Login " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUsers() {
        Response response =new Response();
        try{
            List<User> usersList = userRepository.findAll();
            List<UserDTO> userDTOList = utils.convertUsersListToUserDTOList(usersList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserDTOList(userDTOList);
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Getting users"+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new OurException("Branch not found"));
            if (user != null) {
                UserDTO userDTO = utils.convertUserToUserDTO(user);
                response.setStatusCode(200);
                response.setMessage("User found");
                response.setUserDTO(userDTO);
                response.setStatus(true);
            } else {
                response.setStatusCode(404);
                response.setMessage("user not found");
                response.setStatus(false);

            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(Long id, User user) {
        Response response = new Response();
        try {
            User existingUser = userRepository.findById(id).orElseThrow(() -> new OurException("User not found"));

            // Update fields
            existingUser.setBranch(user.getBranch());
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setStatus(user.getStatus());

            // Save updated user
            userRepository.save(existingUser);

            response.setStatusCode(200);
            response.setMessage("User updated successfully");
        } catch (Exception e) {
            response.setMessage("Error occurred during user update: " + e.getMessage());
            response.setStatusCode(400);
        }
        return response;
    }

    @Override
    public Response updateUserPassword(Long id, String newPassword) {
        Response response = new Response();
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));

//            String encodedPassword = passwordEncoder.encode(newPassword);
            String testPassword = newPassword;
            System.out.println(":--"+testPassword);
            existingUser.setPassword(passwordEncoder.encode(testPassword));
            // Set the last password update time
            existingUser.setLastPasswordUpdate(new Date());


            userRepository.save(existingUser);


            response.setStatusCode(200);
            response.setMessage("Password updated successfully");
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during password update: " + e.getMessage());
            response.setStatus(false);
        }
        return response;
    }


}
