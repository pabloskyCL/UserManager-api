package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Controllers.Request.LoginRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.RegisterRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.AuthResponse;
import com.apiRest.pabloq.UserManager.Services.AuthService;
import com.apiRest.pabloq.UserManager.Services.UserService;
import com.apiRest.pabloq.UserManager.util.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = {"https://wssccl.space", "http://localhost:4200"},allowedHeaders = {"Authorization", "Cache-Control", "Content-Type","X-Amz-Date","X-Api-Key","X-Amz-Security-Token","X-Forwarded-For","Set-Cookie"}, exposedHeaders = {"Set-Cookie", "Authorization"} ,allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("JWT-TOKEN", authResponse.getToken())
                .path("/")
                .maxAge(13000)
                .secure(true)
                .httpOnly(true)
                .sameSite("None")
                .build();

//        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResponse);
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

