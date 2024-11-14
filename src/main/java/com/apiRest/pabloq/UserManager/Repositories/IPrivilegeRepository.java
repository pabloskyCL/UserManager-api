package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
