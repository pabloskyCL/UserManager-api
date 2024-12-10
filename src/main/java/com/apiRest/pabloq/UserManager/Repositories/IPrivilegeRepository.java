package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.Dto.PermissionDto;
import com.apiRest.pabloq.UserManager.Entities.Privilege;
import com.apiRest.pabloq.UserManager.Projections.PermissionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface IPrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);

//    @Query(value = "SELECT p FROM privilege p " +
//            "WHERE p.name in :privilegeList")
    List<Privilege> findByIdIn( Collection<Long> names);

    @Query(value = "SELECT new com.apiRest.pabloq.UserManager.Entities.Dto.PermissionDto(p.id, p.name) FROM Privilege p ")
    List<PermissionDto> allPermission();
}
