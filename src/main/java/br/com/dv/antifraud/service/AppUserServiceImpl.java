package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.*;
import br.com.dv.antifraud.entity.AppUserRole;
import br.com.dv.antifraud.enums.RoleType;
import br.com.dv.antifraud.enums.UserOperation;
import br.com.dv.antifraud.exception.CannotLockAdminException;
import br.com.dv.antifraud.exception.InvalidRoleException;
import br.com.dv.antifraud.exception.RoleAlreadyAssignedException;
import br.com.dv.antifraud.exception.UserAlreadyExistsException;
import br.com.dv.antifraud.mapper.AppUserMapper;
import br.com.dv.antifraud.entity.AppUser;
import br.com.dv.antifraud.repository.AppUserRepository;
import br.com.dv.antifraud.repository.AppUserRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public UserResponse register(UserCreationInfo userCreationInfo) {
        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(userCreationInfo.username());

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        AppUser user = AppUserMapper.dtoToEntity(userCreationInfo);

        user.setPassword(encoder.encode(user.getPassword()));

        boolean isFirstUser = userRepository.count() == 0;
        AppUserRole userRole = getOrCreateRole(isFirstUser ? RoleType.ADMINISTRATOR : RoleType.MERCHANT);
        user.setRole(userRole);
        user.setLocked(!isFirstUser);

        AppUser savedUser = userRepository.save(user);

        return AppUserMapper.entityToDto(savedUser);
    }

    @Override
    public List<UserResponse> findAll() {
        List<AppUser> users = userRepository.findAllByOrderByIdAsc();
        return AppUserMapper.entityToDtoList(users);
    }

    @Override
    public UserDeletionResponse delete(String username) {
        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(username);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }

        AppUser user = userOptional.get();
        userRepository.delete(user);

        return AppUserMapper.entityToDeletionDto(user);
    }

    @Override
    @Transactional
    public UserResponse changeRole(UserRoleUpdateInfo userRoleUpdateInfo) {
        if (userRoleUpdateInfo.role().equals(RoleType.ADMINISTRATOR)) {
            throw new InvalidRoleException("Invalid role. The available roles are MERCHANT and SUPPORT.");
        }

        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(userRoleUpdateInfo.username());

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }

        AppUser user = userOptional.get();

        if (user.getRole().getName().equals(userRoleUpdateInfo.role().name())) {
            throw new RoleAlreadyAssignedException("User already has this role!");
        }

        AppUserRole userRole = getOrCreateRole(userRoleUpdateInfo.role());
        user.setRole(userRole);

        AppUser savedUser = userRepository.save(user);
        return AppUserMapper.entityToDto(savedUser);
    }

    @Override
    @Transactional
    public UserStatusUpdateResponse changeStatus(UserStatusUpdateInfo userStatusUpdateInfo) {
        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(userStatusUpdateInfo.username());

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }

        AppUser user = userOptional.get();

        if (user.getRole().getName().equals(RoleType.ADMINISTRATOR.name())) {
            throw new CannotLockAdminException("Cannot assign LOCK status to ADMIN.");
        }

        user.setLocked(userStatusUpdateInfo.operation().equals(UserOperation.LOCK));

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