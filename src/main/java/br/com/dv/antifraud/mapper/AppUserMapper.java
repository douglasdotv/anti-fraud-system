package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.dto.user.UserCreationInfo;
import br.com.dv.antifraud.dto.user.UserResponse;
import br.com.dv.antifraud.dto.user.UserDeletionResponse;
import br.com.dv.antifraud.dto.user.UserStatusUpdateResponse;
import br.com.dv.antifraud.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {

    private static final String DELETED_SUCCESSFULLY = "Deleted successfully!";
    private static final String LOCKED = "locked";
    private static final String UNLOCKED = "unlocked";

    public static AppUser dtoToEntity(UserCreationInfo creationInfo) {
        AppUser user = new AppUser();
        user.setName(creationInfo.name());
        user.setUsername(creationInfo.username());
        user.setPassword(creationInfo.password());
        return user;
    }

    public static UserResponse entityToDto(AppUser user) {
        return new UserResponse(user.getId(), user.getName(), user.getUsername(), user.getRole().getName());
    }

    public static List<UserResponse> entityToDtoList(List<AppUser> appUsers) {
        return appUsers.stream()
                .map(AppUserMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public static UserDeletionResponse entityToDeletionDto(AppUser user) {
        return new UserDeletionResponse(user.getUsername(), DELETED_SUCCESSFULLY);
    }

    public static UserStatusUpdateResponse entityToStatusUpdateDto(AppUser user) {
        String lockStatus = user.isLocked() ? LOCKED : UNLOCKED;
        String responseStatus = String.format("User %s %s!", user.getUsername(), lockStatus);
        return new UserStatusUpdateResponse(responseStatus);
    }

}
