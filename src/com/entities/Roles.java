package com.entities;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Integer roleId;
	@Column(name = "name", nullable = false)
	private String roleName;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<Users> users;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
