package br.com.dv.antifraud.repository;

import br.com.dv.antifraud.entity.AppUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRoleRepository extends JpaRepository<AppUserRole, Long> {

    Optional<AppUserRole> findByName(String name);

}
