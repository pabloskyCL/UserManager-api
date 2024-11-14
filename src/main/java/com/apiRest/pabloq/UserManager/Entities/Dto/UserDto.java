package com.apiRest.pabloq.UserManager.Entities.Dto;

import com.apiRest.pabloq.UserManager.Entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDto {

    Long id;
    String email;
    String firstName;
    String lastName;
    String phone;
    String address;

    private List<String> roles;

    public UserDto() {
    }

    public UserDto(Long id, String email, String firstName, String lastName, String phone, String address, List<String> roles) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
    }

    private UserDto(UserDtoBuilder userDtoBuilder) {
        this.id = userDtoBuilder.id;
        this.email = userDtoBuilder.email;
        this.firstName = userDtoBuilder.firstName;
        this.lastName = userDtoBuilder.lastName;
        this.address = userDtoBuilder.address;
        this.phone = userDtoBuilder.phone;
        this.roles = userDtoBuilder.roles;
    }

    public static class UserDtoBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String address;
        private String phone;
        private List<String> roles;

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserDtoBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDtoBuilder roles(List<String> role) {
            this.roles = role;
            return this;
        }

        public UserDto build(){
            return new UserDto(this);
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> role) {
        this.roles = role;
    }
}
