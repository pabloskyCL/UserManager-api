package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.PermissionRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreatePermissionResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.PermissionDto;
import com.apiRest.pabloq.UserManager.Entities.Privilege;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Repositories.IPrivilegeRepository;
import com.apiRest.pabloq.UserManager.Repositories.IRoleRepository;
import com.apiRest.pabloq.UserManager.Services.Interfaces.IPermissionService;
import com.apiRest.pabloq.UserManager.Services.Interfaces.IRoleService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class PrivilegeService implements IPermissionService {

    private final IPrivilegeRepository privilegeRepository;
    private final IRoleService roleService;
    private final IRoleRepository roleRepository;

    public PrivilegeService(IPrivilegeRepository privilegeRepository, IRoleService roleService, IRoleRepository roleRepository){
        this.privilegeRepository = privilegeRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    public List<Privilege> getPrivilegeListByName(Long[] privilegeNames){
            return privilegeRepository.findByIdIn(Arrays.asList(privilegeNames));
    }

    public List<PermissionDto> getAll(){
        return privilegeRepository.allPermission();
    }

    public CreatePermissionResponse createPermission(PermissionRequest permissionRequest) {
        Privilege newPermission = new Privilege(permissionRequest.getName());
        Collection<Role> roles = roleService.getRoleListByIds(permissionRequest.getRoleList());
        Privilege createdPermission = privilegeRepository.save(newPermission);
        for(Role role : roles){
            HashSet<Privilege> currentPrivileges = new HashSet<>( role.getPrivileges());
            currentPrivileges.add(createdPermission);
            role.setPrivileges(currentPrivileges);
            roleRepository.save(role);
        }


        return new CreatePermissionResponse(createdPermission.getId(),createdPermission.getName(),Arrays.asList(permissionRequest.getRoleList()));
    }
}
