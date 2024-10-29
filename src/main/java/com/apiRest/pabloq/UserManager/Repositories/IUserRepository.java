package com.apiRest.pabloq.UserManager.Repositories;

import com.apiRest.pabloq.UserManager.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
