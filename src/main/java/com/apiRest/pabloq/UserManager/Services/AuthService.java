package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.LoginRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.RegisterRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.AuthResponse;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Jwt.JwtService;
import com.apiRest.pabloq.UserManager.Repositories.IRoleRepository;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IRoleRepository roleRepository;

    public AuthService(IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        Set<Role> roleList = user.getRoles();
        List<String> roleNames = new ArrayList<>();
        for(Role role: roleList){
            roleNames.add(role.getName());
        }
        System.out.println(roleNames);
        return new AuthResponse(token, user.getId(),roleNames);
    }

    public AuthResponse register(RegisterRequest request) {
        User newUser = new User.UserBuilder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .phone(request.getPhone())
                .role(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))))
                .enabled(true)
                .build();

        User createdUser = userRepository.save(newUser);
        Set<Role> roleList = createdUser.getRoles();
        List<String> roleNames = new ArrayList<>();
        for(Role role: roleList){
            roleNames.add(role.getName());
        }
        return new AuthResponse(this.jwtService.getToken(createdUser), createdUser.getId(), roleNames);
    }
}
