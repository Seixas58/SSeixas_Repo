package com.example.psoftg5.usermanagement.api;


import com.example.psoftg5.exceptions.UserNotFoundException;
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


    @Operation(summary = "Get user from its reader number")
    @GetMapping("/readerNumber/{readerNumber}")
    public ResponseEntity<UserView> getUserByReaderNumber(@PathVariable Long readerNumber) {
        User user = userService.getUserByReaderNumber(readerNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with reader number: " + readerNumber));

        UserView userView = userViewMapper.toUserView(user);
        return ResponseEntity.ok().body(userView);
    }

    @Operation(summary = "Get user by name")
    @GetMapping("/user/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable String name){
        List<User> users = userService.findByName(name);
        List<UserView> userViews = userViewMapper.toUserView(users);
        if (!userViews.isEmpty()) {
            return ResponseEntity.ok().body(userViews);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ArrayList<>());
        }
    }
}







