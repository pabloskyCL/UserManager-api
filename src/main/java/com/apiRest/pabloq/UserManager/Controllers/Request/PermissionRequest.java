package com.apiRest.pabloq.UserManager.Controllers.Request;

public class PermissionRequest {

    String name;
    Long[] roleList;

    public PermissionRequest(String name, Long[] roleList) {
        this.name = name;
        this.roleList = roleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getRoleList() {
        return roleList;
    }

    public void setRoleList(Long[] roleList) {
        this.roleList = roleList;
    }
}
