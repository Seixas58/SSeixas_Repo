package com.example.psoftg5.usermanagement.api;


import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Users")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserViewMapper userViewMapper;

    @Operation(summary = "Create a user")
    @PostMapping("/create")
    public ResponseEntity<UserView> createUser(@RequestBody @Valid final CreateUserRequest request) {
        final User user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userViewMapper.toUserView(user));
    }

    @Operation(summary = "Update user information")
    @PatchMapping("/{username}")
    public ResponseEntity<UserView> updateUser(@PathVariable("username") String username, @Valid @RequestBody CreateUserRequest request) {
        final User user = userService.partialUpdate(username, request);
        return ResponseEntity.ok(userViewMapper.toUserView(user));
    }

}







