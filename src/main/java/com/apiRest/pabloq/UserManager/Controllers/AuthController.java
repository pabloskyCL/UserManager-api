package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Controllers.Request.LoginRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.RegisterRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.AuthResponse;
import com.apiRest.pabloq.UserManager.Services.AuthService;
import com.apiRest.pabloq.UserManager.Services.UserService;
import com.apiRest.pabloq.UserManager.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = {"http://localhost:4200"},allowedHeaders = "*" ,allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);

        Cookie cookie = new Cookie("JWT-TOKEN", authResponse.getToken());
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(13000);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));

    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logOut(HttpServletResponse response){
        CookieUtil.clear(response,"JWT-TOKEN");
        Map<String, String> res = new HashMap<>();
        res.put("message", "sesión terminada");
        return ResponseEntity.ok(res);
    }

}

