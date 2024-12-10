package com.apiRest.pabloq.UserManager.Controllers.Response;

import java.util.List;

public class CreatePermissionResponse {

    private Long id;
    private String name;
    private List<Long> roleList;

    public CreatePermissionResponse(Long id, String name, List<Long> roleList) {
        this.id = id;
        this.name = name;
        this.roleList = roleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleList = roleList;
    }
}
