package br.com.dv.antifraud.controller;

import br.com.dv.antifraud.dto.user.*;
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
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        UserResponse response = userService.register(request);
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
        UserDeletionResponse response = userService.delete(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/role")
    public ResponseEntity<UserResponse> changeRole(@RequestBody UserRoleUpdateRequest request) {
        UserResponse response = userService.changeRole(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/access")
    public ResponseEntity<UserStatusUpdateResponse> changeStatus(@RequestBody UserStatusUpdateRequest request) {
        UserStatusUpdateResponse response = userService.changeStatus(request);
        return ResponseEntity.ok(response);
    }

}
