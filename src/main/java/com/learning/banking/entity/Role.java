package com.learning.banking.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Roles
 *
 * @author bryan
 * @date Mar 4, 2022-3:55:39 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "customers")
@ToString(exclude = "customers")
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "roleName"))
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleID;
	@Column(unique = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRoles roleName;
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<Customer> customers;
	
	public Role(UserRoles roleName) {
		this.roleName = roleName;
	}
}
