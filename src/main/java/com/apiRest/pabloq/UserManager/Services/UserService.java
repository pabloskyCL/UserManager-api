package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Controllers.Request.UpdateRequest;
import com.apiRest.pabloq.UserManager.Entities.Dto.UserDto;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto updateUser(UpdateRequest newUserInfo){
        User user = this.userRepository.findById(newUserInfo.getId()).orElseThrow(
                () -> new RuntimeException("no existe un usuario con este id")
        );

            user.setFirstName(newUserInfo.getFirstName());
            user.setLastName(newUserInfo.getLastName());
            user.setEmail(newUserInfo.getEmail());
            user.setAddress(newUserInfo.getAddress());
            user.setPhone(newUserInfo.getPhone());
            User modifiedUser = this.userRepository.save(user);

        return new UserDto.UserDtoBuilder()
                .id(modifiedUser.getId())
                .firstName(modifiedUser.getFirstName())
                .lastName(modifiedUser.getLastName())
                .email(modifiedUser.getEmail())
                .address(modifiedUser.getAddress())
                .phone(modifiedUser.getPhone())
                .build();


    }

    public UserDto getUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("user with this id not found"));

        return new UserDto.UserDtoBuilder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .build();
    }

}
