package com.apiRest.pabloq.UserManager.Controllers.Response;

public class AuthResponse {

    String token;
    Long userId;

    public AuthResponse(String token, Long id) {
        this.userId = id;
        this.token = token;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }
}
