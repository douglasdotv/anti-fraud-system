package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.UserCreationInfo;
import br.com.dv.antifraud.dto.UserResponse;
import br.com.dv.antifraud.dto.UserDeletionResponse;
import br.com.dv.antifraud.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {

    public static AppUser toEntity(UserCreationInfo creationInfo) {
        AppUser user = new AppUser();
        user.setName(creationInfo.name());
        user.setUsername(creationInfo.username());
        user.setPassword(creationInfo.password());
        return user;
    }

    public static UserResponse toResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getName(), user.getUsername());
    }

    public static List<UserResponse> toResponseList(List<AppUser> appUsers) {
        return appUsers.stream()
                .map(AppUserMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static UserDeletionResponse toDeletionResponse(AppUser user) {
        return new UserDeletionResponse(user.getUsername(), "Deleted successfully!");
    }

}
