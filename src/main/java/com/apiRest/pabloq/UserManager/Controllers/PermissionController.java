package com.apiRest.pabloq.UserManager.Controllers;
import com.apiRest.pabloq.UserManager.Controllers.Request.PermissionRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.CreatePermissionResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.PermissionDto;
import com.apiRest.pabloq.UserManager.Services.Interfaces.IPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@CrossOrigin(originPatterns = {"https://wssccl.space", "http://localhost:4200"},allowedHeaders = {"Authorization", "Cache-Control", "Content-Type","X-Amz-Date","X-Api-Key","X-Amz-Security-Token","X-Forwarded-For","Set-Cookie"}, exposedHeaders = {"Set-Cookie", "Authorization"} ,allowCredentials = "true")
public class PermissionController {
    private final IPermissionService privilegeService;

    public PermissionController(IPermissionService privilegeService){
        this.privilegeService = privilegeService;
    }

    @GetMapping
    public List<PermissionDto> getAllPermission(){
        return privilegeService.getAll();
    }

    @PostMapping
    public CreatePermissionResponse createPermission(@RequestBody PermissionRequest permissionRequest){
        System.out.println(permissionRequest.getName());
        return privilegeService.createPermission(permissionRequest);
    }

}
