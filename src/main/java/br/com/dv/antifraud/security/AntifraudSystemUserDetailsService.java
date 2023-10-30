package br.com.dv.antifraud.security;

import br.com.dv.antifraud.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static br.com.dv.antifraud.constants.AntifraudSystemConstants.USER_NOT_FOUND_MESSAGE;

@Service
public class AntifraudSystemUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public AntifraudSystemUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username)
                .map(AppUserAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

}
