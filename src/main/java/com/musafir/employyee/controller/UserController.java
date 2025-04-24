package com.musafir.employyee.controller;

import com.musafir.employyee.entity.User;
import com.musafir.employyee.repo.UserRepo;
import com.musafir.employyee.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }
}
