package com.apiRest.pabloq.UserManager.Controllers.Request;


public class UpdatePermissionInRoleRequest {

    private Long[] permissions;

    public UpdatePermissionInRoleRequest() {}

    public UpdatePermissionInRoleRequest(Long[] permissions) {
        this.permissions = permissions;
    }

    public Long[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Long[] permissions) {
        this.permissions = permissions;
    }
}
