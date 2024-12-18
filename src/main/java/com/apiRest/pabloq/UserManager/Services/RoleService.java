package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.RoleRequest;
import com.apiRest.pabloq.UserManager.Controllers.Request.UpdatePermissionInRoleRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreateRoleResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.RolePermissionListDto;
import com.apiRest.pabloq.UserManager.Entities.Privilege;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Repositories.IPrivilegeRepository;
import com.apiRest.pabloq.UserManager.Repositories.IRoleRepository;
import com.apiRest.pabloq.UserManager.Services.Interfaces.IPermissionService;
import com.apiRest.pabloq.UserManager.Services.Interfaces.IRoleService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

@Service
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IPrivilegeRepository privilegeRepository;

    public RoleService(IRoleRepository roleRepository, IPrivilegeRepository privilegeRepository){
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    public List<RolePermissionListDto> allRoleWithPrivileges(){
        List<Object[]> data = this.roleRepository.getAllRolePrivilageList();
        List<RolePermissionListDto> result = new ArrayList<>();
        for(Object[] row : data ){
            RolePermissionListDto roleAndPermissionList = new RolePermissionListDto(
                    (Long) row[0],
                    (String) row[1],
                    Arrays.asList((String[]) row[2])
            );
            result.add(roleAndPermissionList);
        }
        return result;
    }

    public CreateRoleResponse createRole(RoleRequest roleRequest){
        Role role = new Role();
        role.setName(roleRequest.getName());
        List<Privilege> privilegeList = privilegeRepository.findByIdIn(Arrays.asList(roleRequest.getPermissionList()));
        Set<Privilege> privileges = new HashSet<>(privilegeList);
        role.setPrivileges(privileges);
        Role newRole = roleRepository.save(role);

        return new CreateRoleResponse(newRole.getId(),newRole.getName(),Arrays.asList(roleRequest.getPermissionList()));
    }

    public List<Role> getRoleListByIds(Long[] ids){
        return  roleRepository.findByIdIn(Arrays.asList(ids));
    }

    @Transactional
    public Map<String, String> deleteRole(Long id) {
        this.roleRepository.deleteUserRolesByRole(id);
        this.roleRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        return response;
    }

    public CreateRoleResponse updatePermissions(Long id, UpdatePermissionInRoleRequest request) throws Exception {
        Optional<Role> role = this.roleRepository.findById(id);
        if(role.isPresent()){
            Role fetched = role.get();
            List<Privilege> privilegeList = this.privilegeRepository.findByIdIn(Arrays.asList(request.getPermissions()));
             fetched.setPrivileges(new HashSet<>( privilegeList));
             Role createdRole = this.roleRepository.save(fetched);
            return new CreateRoleResponse(createdRole.getId(),createdRole.getName(),Arrays.asList(request.getPermissions()));

        }

        throw new Exception("rol no encontrado o privilegios(permisos) en mal formato");
    }
}
