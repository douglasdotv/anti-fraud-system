package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.*;
import br.com.dv.antifraud.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService userService;

    public AuthController(AppUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserCreationInfo creationInfo,
                                                     UriComponentsBuilder uriBuilder) {
        UserResponse response = userService.register(creationInfo);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserDeletionResponse> deleteUser(@PathVariable String username) {
        UserDeletionResponse userDeletionResponse = userService.delete(username);
        return ResponseEntity.ok(userDeletionResponse);
    }

    @PutMapping("/role")
    public ResponseEntity<UserResponse> changeRole(@RequestBody UserRoleUpdateInfo roleUpdateInfo) {
        UserResponse response = userService.changeRole(roleUpdateInfo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/access")
    public ResponseEntity<UserStatusUpdateResponse> changeStatus(@RequestBody UserStatusUpdateInfo statusUpdateInfo) {
        UserStatusUpdateResponse response = userService.changeStatus(statusUpdateInfo);
        return ResponseEntity.ok(response);
    }

}