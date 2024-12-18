package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.Dto.UserRolePrivilegeDto;
import com.apiRest.pabloq.UserManager.Entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles", "roles.privileges"})
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.firstName=:firstname," +
            " u.lastName=:lastname, u.email=:email," +
            " u.address=:address," +
            " u.phone=:phone where u.id = :id")
    void updateUser(@Param(value = "id") Integer id,
                    @Param(value = "firstname") String firstname,
                    @Param(value = "lastname") String lastname,
                    @Param(value = "email") String email,
                    @Param(value = "address") String address,
                    @Param(value = "phone") String phone
                    );



    @Query(value = "SELECT u.id as userId, " +
            "u.first_name as firstName, " +
            "u.last_name as lastName, " +
            "u.email as email, " +
            "u.address as address, " +
            "u.phone as phone, " +
            "u.enabled as enabled, " +
            "string_to_array(string_agg(distinct r.name, ','),',') as roleName, " +
            "string_to_array(string_agg(distinct p.name, ','), ',') as privilegeName " +
            "FROM users u " +
            "LEFT JOIN users_roles ur on u.id = ur.user_id " +
            "LEFT JOIN role r on ur.role_id = r.id " +
            "LEFT JOIN roles_privileges rp on r.id = rp.role_id " +
            "LEFT JOIN privilege p on rp.privilege_id = p.id " +
            "WHERE u.id <> :currentUserId " +
            "GROUP BY u.id", nativeQuery = true)
    List<Object[]> getAllWithAuthorities(@Param("currentUserId") Long id);

    @Query(value = "SELECT u.id as userId, " +
            "u.first_name as firstName, " +
            "u.last_name as lastName, " +
            "u.email as email, " +
            "u.address as address, " +
            "u.phone as phone, " +
            "u.enabled as enabled, " +
            "string_to_array(string_agg(distinct r.name, ','),',') as roleName, " +
            "string_to_array(string_agg(distinct p.name, ','), ',') as privilegeName " +
            "FROM users u " +
            "LEFT JOIN users_roles ur on u.id = ur.user_id " +
            "LEFT JOIN role r on ur.role_id = r.id " +
            "LEFT JOIN roles_privileges rp on r.id = rp.role_id " +
            "LEFT JOIN privilege p on rp.privilege_id = p.id " +
            "WHERE u.email = :email " +
            "GROUP BY u.id", nativeQuery = true)
    Object[] getUserWithRoleAndPrivileges(@Param("email") String email);

}
