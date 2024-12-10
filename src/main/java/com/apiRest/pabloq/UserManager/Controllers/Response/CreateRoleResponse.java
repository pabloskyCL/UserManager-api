package com.apiRest.pabloq.UserManager.Controllers.Response;

import java.util.List;

public class CreateRoleResponse {
    private Long id;
    private String name;
    private List<Long> privileges;


    public CreateRoleResponse(Long id, String name, List<Long> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
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

    public List<Long> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Long> privileges) {
        this.privileges = privileges;
    }
}
