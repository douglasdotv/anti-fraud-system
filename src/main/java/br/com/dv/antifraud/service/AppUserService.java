package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.user.*;

import java.util.List;

public interface AppUserService {

    UserResponse register(UserRegistrationRequest request);

    List<UserResponse> findAll();

    UserDeletionResponse delete(String username);

    UserResponse changeRole(UserRoleUpdateRequest request);

    UserStatusUpdateResponse changeStatus(UserStatusUpdateRequest request);

}
