package com.thecodinginterface.tcilol.repositories;

import com.thecodinginterface.tcilol.models.AvailableRoles;
import com.thecodinginterface.tcilol.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(AvailableRoles name);
}
