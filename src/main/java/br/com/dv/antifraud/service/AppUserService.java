package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.*;

import java.util.List;

public interface AppUserService {

    UserResponse register(UserCreationInfo userCreationInfo);

    List<UserResponse> findAll();

    UserDeletionResponse delete(String username);

    UserResponse changeRole(UserRoleUpdateInfo roleUpdateInfo);

    UserStatusUpdateResponse changeStatus(UserStatusUpdateInfo userStatusUpdateInfo);

}
