package com.learning.banking.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.banking.entity.Role;
import com.learning.banking.enums.UserRoles;

/**
 * RoleRepository
 *
 * @author bryan
 * @date Mar 8, 2022-3:26:19 PM
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findRoleByRoleName(UserRoles roleName);
	Role getRoleByRoleName(UserRoles roleName);
}
