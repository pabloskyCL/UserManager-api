package com.apiRest.pabloq.UserManager.Services;

import com.apiRest.pabloq.UserManager.Entities.Privilege;
import com.apiRest.pabloq.UserManager.Entities.Role;
import com.apiRest.pabloq.UserManager.Entities.User;
import com.apiRest.pabloq.UserManager.Repositories.IUserRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    public CustomUserDetailService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return userRepository.findByEmail(username).get();
//            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(),true,true,true,getGrantedAuthorities(getPrivileges(user.getRoles())));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
