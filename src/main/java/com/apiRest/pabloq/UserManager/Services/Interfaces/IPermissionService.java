package com.apiRest.pabloq.UserManager.Services.Interfaces;

import com.apiRest.pabloq.UserManager.Controllers.Request.PermissionRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreatePermissionResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.PermissionDto;
import com.apiRest.pabloq.UserManager.Entities.Privilege;

import java.util.List;

public interface IPermissionService {

    public List<Privilege> getPrivilegeListByName(Long[] privilegeNames);
    public List<PermissionDto> getAll();
    public CreatePermissionResponse createPermission(PermissionRequest permissionRequest);
}
