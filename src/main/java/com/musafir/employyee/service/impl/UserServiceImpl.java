package com.musafir.employyee.service.impl;

import com.musafir.employyee.entity.Role;
import com.musafir.employyee.entity.User;
import com.musafir.employyee.exception.ResourceNotFoundException;
import com.musafir.employyee.repo.RoleRepo;
import com.musafir.employyee.repo.UserRepo;
import com.musafir.employyee.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
   private PasswordEncoder passwordEncoder;
    private RoleRepo roleRepo;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String roleString = user.getAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        Role role = roleRepo.findByRoleName(roleString).orElseGet(()-> {
            Role role1 = new Role();
            role1.setRoleName(roleString);
            return role1;
        });
        user.setRoles(List.of(role));
        return userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) {
       return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("No User with user Id: "+id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public String deleteUserById(Long id) {
        if(!userRepo.existsById(id))
            return "No user exists";
        else
            userRepo.deleteById(id);
        return "successfully deleted";
    }
}
