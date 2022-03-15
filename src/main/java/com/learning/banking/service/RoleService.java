package com.learning.banking.service;

import java.util.Optional;

import com.learning.banking.entity.Role;
import com.learning.banking.enums.UserRoles;

public interface RoleService {
	public Optional<Role> findByRoleName(UserRoles roleName);
	public Role addRole(Role role);
}
