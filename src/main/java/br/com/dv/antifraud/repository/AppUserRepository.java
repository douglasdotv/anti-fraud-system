package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    List<AppUser> findAllByOrderByIdAsc();

}
