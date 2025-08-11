package com.example.psoftg5.usermanagement.api;

import com.example.psoftg5.bookmanagement.model.Book;
import com.example.psoftg5.lendingmanagement.service.LendingService;
import com.example.psoftg5.usermanagement.model.AuthorityRole;
import com.example.psoftg5.usermanagement.model.User;
import com.example.psoftg5.usermanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name = "Users")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserViewMapper userViewMapper;

    @Autowired
    private LendingService lendingService;

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

    @Operation(summary = "Get user from its reader number")
    @GetMapping("/readerNumber/{readerNumber}")
    public ResponseEntity<UserView> getUserByReaderNumber(@PathVariable Long readerNumber) {
        final Optional<User> user = userService.getUserByReaderNumber(readerNumber);
        return user.map(value -> ResponseEntity.ok(userViewMapper.toUserView(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by name")
    @GetMapping("/user/{name}")
    public ResponseEntity<List<UserView>> getUsersByName(@PathVariable String name) {
        final List<User> users = userService.findByName(name);
        return ResponseEntity.ok(userViewMapper.toUserView(users));
    }

    @GetMapping("/top5readers")
    public List<User> getTop5Readers() {
        return lendingService.getTop5Readers();
    }
}







