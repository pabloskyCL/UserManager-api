package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.UpdateRequest;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserDto;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserRolePrivilegeDto;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Jwt.JwtService;
import com.apiRest.pabloq.UserManager.Repositories.IRoleRepository;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final IRoleRepository roleRepository;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, JwtService jwtService){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public UserDto updateUser(UpdateRequest newUserInfo){
        User user = this.userRepository.findById(newUserInfo.getId()).orElseThrow(
                () -> new RuntimeException("no existe un usuario con este id")
        );
        Role role = this.roleRepository.findById((long)newUserInfo.getRole()).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        System.out.println(role.getName());
            user.setFirstName(newUserInfo.getFirstName());
            user.setLastName(newUserInfo.getLastName());
            user.setEmail(newUserInfo.getEmail());
            user.setAddress(newUserInfo.getAddress());
            user.setPhone(newUserInfo.getPhone());
            user.setRoles(roles);
            User modifiedUser = this.userRepository.save(user);

        return new UserDto.UserDtoBuilder()
                .id(modifiedUser.getId())
                .firstName(modifiedUser.getFirstName())
                .lastName(modifiedUser.getLastName())
                .email(modifiedUser.getEmail())
                .address(modifiedUser.getAddress())
                .phone(modifiedUser.getPhone())
                .roles(Arrays.asList(role.getName()))
                .build();
    }

    public UserDto getUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user with this id not found"));

        Set<Role> roles = user.getRoles();
        List<String> roleNameList = new ArrayList<>();
        for (Role role: roles){
            roleNameList.add(role.getName());
        }
        return new UserDto.UserDtoBuilder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .roles(roleNameList)
                .build();
    }

    public Long getUserIdFromToken(String token){
        return this.jwtService.getUserIdFromToken(token);
    }

    public List<UserRolePrivilegeDto> getUsersWithRolesAndPrivileges(Long currentUserId){
        List<Object[]> results = userRepository.getAllWithAuthorities(currentUserId);
        List<UserRolePrivilegeDto> dtoList = new ArrayList<>();
        for (Object[] row : results){
            UserRolePrivilegeDto dto = new UserRolePrivilegeDto(
                    (Long) row[0],
                    (String) row[1],
                    (String) row[2],
                    (String) row[3],
                    (String) row[4],
                    (String) row[5],
                    (boolean) row[6],
                     row[7] == null ? new ArrayList<>(): Arrays.asList((String[]) row[7]),
                    row[8] == null ? new ArrayList<>(): Arrays.asList((String[]) row[8])
                    );

            dtoList.add(dto);
        }

        return dtoList;
    }

}
