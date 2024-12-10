package com.apiRest.pabloq.UserManager.Controllers.Request;

import java.util.List;

public class RoleRequest {

    String name;
    Long[] permissionList;

    public RoleRequest(String name, Long[] permissionList){
        this.name = name;
        this. permissionList = permissionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Long[] permissionList) {
        this.permissionList = permissionList;
    }
}
