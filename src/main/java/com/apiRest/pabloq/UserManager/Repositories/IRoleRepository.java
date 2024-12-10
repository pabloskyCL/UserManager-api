package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleAdmin);

    @Query(value = "SELECT r.id, r.name, string_to_array(string_agg(distinct p.name, ','), ',') as privileges " +
            "FROM role r " +
            "LEFT JOIN roles_privileges rp on r.id = rp.role_id " +
            "LEFT JOIN privilege p on p.id = rp.privilege_id " +
            "Group by r.id", nativeQuery = true)
    List<Object[]> getAllRolePrivilageList();

    @Modifying
    @Query(value = "DELETE FROM users_roles WHERE role_id = :roleId", nativeQuery = true)
    void deleteUserRolesByRole(@Param("roleId") Long roleId);

    List<Role> findByIdIn(Collection<Long> ids);
}
