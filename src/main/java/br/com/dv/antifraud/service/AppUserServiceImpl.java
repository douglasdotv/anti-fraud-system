package br.com.dv.antifraud.service;

import br.com.dv.antifraud.dto.UserCreationInfo;
import br.com.dv.antifraud.dto.UserResponse;
import br.com.dv.antifraud.dto.UserDeletionResponse;
import br.com.dv.antifraud.exception.UserAlreadyExistsException;
import br.com.dv.antifraud.mapper.AppUserMapper;
import br.com.dv.antifraud.entity.AppUser;
import br.com.dv.antifraud.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder encoder;

    public AppUserServiceImpl(AppUserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserResponse register(UserCreationInfo userCreationInfo) {
        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(userCreationInfo.username());

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        AppUser user = AppUserMapper.toEntity(userCreationInfo);
        user.setPassword(encoder.encode(user.getPassword()));
        AppUser savedUser = userRepository.save(user);

        return AppUserMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> findAll() {
        List<AppUser> users = userRepository.findAllByOrderByIdAsc();
        return AppUserMapper.toResponseList(users);
    }

    @Override
    public UserDeletionResponse delete(String username) {
        Optional<AppUser> userOptional = userRepository.findByUsernameIgnoreCase(username);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found!");
        }

        AppUser user = userOptional.get();
        userRepository.delete(user);

        return AppUserMapper.toDeletionResponse(user);
    }

}
