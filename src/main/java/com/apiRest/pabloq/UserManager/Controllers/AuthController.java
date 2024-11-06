package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Controllers.Request.LoginRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.RegisterRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.AuthResponse;
import com.apiRest.pabloq.UserManager.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));

    }

}

