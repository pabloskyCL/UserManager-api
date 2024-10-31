package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "demo")
    public String demo(){
        return "demo de endpoint privado";
    }


}
