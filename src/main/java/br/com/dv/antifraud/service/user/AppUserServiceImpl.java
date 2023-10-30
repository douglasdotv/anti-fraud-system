package br.com.dv.antifraud.service.user;

import br.com.dv.antifraud.dto.user.*;
import br.com.dv.antifraud.entity.AppUser;
import br.com.dv.antifraud.entity.AppUserRole;
import br.com.dv.antifraud.enums.RoleType;
import br.com.dv.antifraud.enums.UserOperation;
import br.com.dv.antifraud.exception.custom.CannotLockAdminException;
import br.com.dv.antifraud.exception.custom.EntityAlreadyExistsException;
import br.com.dv.antifraud.exception.custom.InvalidRoleException;
import br.com.dv.antifraud.exception.custom.RoleAlreadyAssignedException;
import br.com.dv.antifraud.mapper.AppUserMapper;
import br.com.dv.antifraud.repository.AppUserRepository;
import br.com.dv.antifraud.repository.AppUserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.USER_ALREADY_EXISTS_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.USER_NOT_FOUND_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.INVALID_ROLE_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.ROLE_ALREADY_ASSIGNED_MESSAGE;
import static br.com.dv.antifraud.constants.AntifraudSystemConstants.CANNOT_LOCK_ADMIN_MESSAGE;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final AppUserRoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public AppUserServiceImpl(AppUserRepository userRepository,
                              AppUserRoleRepository roleRepository,
                              PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public UserResponse register(UserRegistrationRequest request) {
        userRepository.findByUsernameIgnoreCase(request.username()).ifPresent(user -> {
            throw new EntityAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE);
        });

        AppUser user = AppUserMapper.dtoToEntity(request);

        user.setPassword(encoder.encode(user.getPassword()));

        boolean isFirstUser = userRepository.count() == 0;
        AppUserRole userRole = getOrCreateRole(isFirstUser ? RoleType.ADMINISTRATOR : RoleType.MERCHANT);
        user.setRole(userRole);
        user.setLocked(!isFirstUser);

        AppUser savedUser = userRepository.save(user);

        return AppUserMapper.entityToDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<AppUser> users = userRepository.findAllByOrderByIdAsc();
        return AppUserMapper.entityToDtoList(users);
    }

    @Override
    @Transactional
    public UserDeletionResponse delete(String username) {
        AppUser user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        userRepository.delete(user);

        return AppUserMapper.entityToDeletionDto(user);
    }

    @Override
    @Transactional
    public UserResponse changeRole(UserRoleUpdateRequest request) {
        if (request.role().equals(RoleType.ADMINISTRATOR)) {
            throw new InvalidRoleException(INVALID_ROLE_MESSAGE);
        }

        AppUser user = userRepository.findByUsernameIgnoreCase(request.username())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (user.getRole().getName().equals(request.role().name())) {
            throw new RoleAlreadyAssignedException(ROLE_ALREADY_ASSIGNED_MESSAGE);
        }

        AppUserRole userRole = getOrCreateRole(request.role());
        user.setRole(userRole);

        AppUser savedUser = userRepository.save(user);
        return AppUserMapper.entityToDto(savedUser);
    }

    @Override
    @Transactional
    public UserStatusUpdateResponse changeStatus(UserStatusUpdateRequest request) {
        AppUser user = userRepository.findByUsernameIgnoreCase(request.username())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (user.getRole().getName().equals(RoleType.ADMINISTRATOR.name())) {
            throw new CannotLockAdminException(CANNOT_LOCK_ADMIN_MESSAGE);
        }

        user.setLocked(request.operation().equals(UserOperation.LOCK));

        AppUser savedUser = userRepository.save(user);
        return AppUserMapper.entityToStatusUpdateDto(savedUser);
    }

    private AppUserRole getOrCreateRole(RoleType role) {
        return roleRepository.findByName(role.name()).orElseGet(() -> {
            AppUserRole newRole = new AppUserRole();
            newRole.setName(role.name());
            return roleRepository.save(newRole);
        });
    }

}
