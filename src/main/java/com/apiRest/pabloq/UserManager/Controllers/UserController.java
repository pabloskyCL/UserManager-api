package com.apiRest.pabloq.UserManager.Controllers;

import com.apiRest.pabloq.UserManager.Controllers.Request.UpdateRequest;
import com.apiRest.pabloq.UserManager.Controllers.Response.BlockUserResponse;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserDto;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserRolePrivilegeDto;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import com.apiRest.pabloq.UserManager.Services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = {"http://localhost:4200"},allowedHeaders = "*", allowCredentials = "true")
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
    public List<UserRolePrivilegeDto> allUser(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) {
        String authorizationToken  =  authorizationHeader.replace("Bearer ", "");
        Optional<Cookie> cookie =  Arrays.stream(request.getCookies()).filter(cookies -> cookies.getName().equals("JWT-TOKEN")).findFirst();

        String token  = (authorizationToken.isEmpty()) ? authorizationToken : cookie.get().getValue() ;
        Long userId = this.userService.getUserIdFromToken(token);

        return this.userService.getUsersWithRolesAndPrivileges(userId);
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

    @PutMapping(value = "/block/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<BlockUserResponse> blockOrEnableUser(@PathVariable Long id) {
        Optional<User> targetUser = this.userRepository.findById(id);
        if(targetUser.isPresent()){
            User user = targetUser.get();
            user.setEnabled(!user.isEnabled());
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(new BlockUserResponse(true,"El usuario: "+ savedUser.getEmail()+ " a sido bloqueado"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BlockUserResponse(false, "usuario no encontrado"));

    }

}
