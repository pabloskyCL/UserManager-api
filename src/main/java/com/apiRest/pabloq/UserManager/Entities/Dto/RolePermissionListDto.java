package com.apiRest.pabloq.UserManager.Entities.Dto;

import java.util.List;

public class RolePermissionListDto {

    private Long id;
    private String name;
    private List<String> privileges;

    public RolePermissionListDto(Long id, String name, List<String> privileges){
        this.id = id;
        this.name = name;
        this.privileges= privileges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
