package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
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

}
