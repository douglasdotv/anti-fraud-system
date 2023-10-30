package br.com.dv.antifraud.mapper;

import br.com.dv.antifraud.constants.AntifraudSystemConstants;
import br.com.dv.antifraud.dto.user.UserRegistrationRequest;
import br.com.dv.antifraud.dto.user.UserResponse;
import br.com.dv.antifraud.dto.user.UserDeletionResponse;
import br.com.dv.antifraud.dto.user.UserStatusUpdateResponse;
import br.com.dv.antifraud.entity.AppUser;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserMapper {

    public static AppUser dtoToEntity(UserRegistrationRequest creationInfo) {
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
        return new UserDeletionResponse(user.getUsername(), AntifraudSystemConstants.STATUS_DELETED_SUCCESSFULLY);
    }

    public static UserStatusUpdateResponse entityToStatusUpdateDto(AppUser user) {
        String lockStatus = user.isLocked() ? AntifraudSystemConstants.STATUS_LOCKED : AntifraudSystemConstants.STATUS_UNLOCKED;
        String responseStatus = String.format(
                AntifraudSystemConstants.USER_STATUS_UPDATE_TEMPLATE, user.getUsername(), lockStatus);
        return new UserStatusUpdateResponse(responseStatus);
    }

}
