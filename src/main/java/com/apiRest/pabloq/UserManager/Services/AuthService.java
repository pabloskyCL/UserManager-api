package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.LoginRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.RegisterRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.AuthResponse;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Jwt.JwtService;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAddress(request.getAddress());
        newUser.setAge(request.getAge());
        newUser.setPhone(request.getPhone());
        newUser.setRole(Role.USER);

        userRepository.save(newUser);

        return new AuthResponse(this.jwtService.getToken(newUser));
    }
}
