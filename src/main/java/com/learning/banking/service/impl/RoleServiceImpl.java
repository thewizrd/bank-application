package com.learning.banking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.banking.entity.Role;
import com.learning.banking.enums.UserRoles;
import com.learning.banking.repo.RoleRepository;
import com.learning.banking.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<Role> findByRoleName(UserRoles roleName) {
		return roleRepository.findByRoleName(roleName);
	}

	@Override
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}
}
