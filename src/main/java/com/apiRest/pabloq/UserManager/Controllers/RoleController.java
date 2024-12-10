package com.apiRest.pabloq.UserManager.Controllers;


import com.apiRest.pabloq.UserManager.Controllers.Request.RoleRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreateRoleResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.RolePermissionListDto;
import com.apiRest.pabloq.UserManager.Services.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(originPatterns = {"https://wssccl.space", "http://localhost:4200"},allowedHeaders = {"Authorization", "Cache-Control", "Content-Type","X-Amz-Date","X-Api-Key","X-Amz-Security-Token","X-Forwarded-For","Set-Cookie"}, exposedHeaders = {"Set-Cookie", "Authorization"} ,allowCredentials = "true")
public class RoleController {

    private final RoleService roleService;
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    public List<RolePermissionListDto> roleWithPermissionList(){
        return this.roleService.allRoleWithPrivileges();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateRoleResponse createRole(@RequestBody RoleRequest request){
        System.out.println(request.getName() + Arrays.toString(request.getPermissionList()));
        return this.roleService.createRole(request);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> deleteRole(@PathVariable Long id){
        return this.roleService.deleteRole(id);
    }

//    @PutMapping("/{id}")
//    public RolePermissionListResponse updateRole(@PathVariable Long id, @RequestBody String[] permissions){
//        return new RolePermissionListResponse(1L, "nombre", List.of("asdf"));
//    }


}
