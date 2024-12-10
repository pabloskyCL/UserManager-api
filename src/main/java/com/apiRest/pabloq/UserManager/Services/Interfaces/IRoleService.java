package com.apiRest.pabloq.UserManager.Services.Interfaces;

import com.apiRest.pabloq.UserManager.Controllers.Request.RoleRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreateRoleResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.RolePermissionListDto;
import com.apiRest.pabloq.UserManager.Entities.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    public List<RolePermissionListDto> allRoleWithPrivileges();
    public CreateRoleResponse createRole(RoleRequest roleRequest);
    public List<Role> getRoleListByIds(Long[] ids);
    public Map<String, String> deleteRole(Long id);
}
