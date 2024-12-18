package com.apiRest.pabloq.UserManager.Controllers.Response;

import java.util.List;

public class AuthResponse {

    String token;
    Long userId;
    List<String> roleNames;

    public AuthResponse(String token, Long id, List<String> roleNames) {
        this.userId = id;
        this.token = token;
        this.roleNames = roleNames;
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

    public void setUserId(Long id) {
        this.userId = id;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
