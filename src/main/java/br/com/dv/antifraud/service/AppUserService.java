package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.UserCreationInfo;
import br.com.dv.antifraud.dto.UserResponse;
import br.com.dv.antifraud.dto.UserDeletionResponse;

import java.util.List;

public interface AppUserService {

    UserResponse register(UserCreationInfo userCreationInfo);

    List<UserResponse> findAll();

    UserDeletionResponse delete(String username);

}
