package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Controllers.Request.UpdateRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.UserListResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserDto;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import com.apiRest.pabloq.UserManager.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final IUserRepository userRepository;

    @Autowired
    public UserController(UserService userService, IUserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUserInfo(@RequestBody UpdateRequest newUserInfo) {
        return ResponseEntity.ok(this.userService.updateUser(newUserInfo));
    }

    @GetMapping
    public List<UserListResponse> allUser(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = this.userService.getUserIdFromToken(authorizationHeader.replace("Bearer ", ""));
        return this.userRepository.getAllWithoutCurrent(userId);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUser(id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Map <String, String>> deleteUser(@PathVariable Long id) {
        this.userRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("success", "true");
        return ResponseEntity.ok(response);
    }


}
