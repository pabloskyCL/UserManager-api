package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import com.apiRest.pabloq.UserManager.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping
    public User updateUserInfo(@RequestBody User newUserInfo){
        User user = this.userRepository.findById(newUserInfo.getId()).orElseThrow(
                () -> new RuntimeException("no existe un usuario con este id")
        );

        if(null != user){
            user.setFirstName(newUserInfo.getFirstName());
            user.setLastName(newUserInfo.getLastName());
            user.setEmail(newUserInfo.getEmail());
            user.setAddress(newUserInfo.getAddress());
            user.setPhone(newUserInfo.getPhone());
            user.setAge(newUserInfo.getAge());
            user.setPassword(user.getPassword());
            this.userRepository.save(user);
        }

        return user;

    }

    @GetMapping
    public List<User> allUser(){
        return this.userRepository.findAll();
    }

    @GetMapping(value = "{id}")
    public User getUser(@PathVariable Long id){
        return this.userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user with this id not found")
        );
    }


}
