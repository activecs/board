/**
 * 
 */
package com.kharkiv.board.dto.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.kharkiv.board.dto.BaseEntity;

@Entity
@DynamicInsert
@Table(name = "roles")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 2198529309414696602L;

	@Column(length = 55, nullable = false, unique = true)
	private String name;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "role_user", 
		joinColumns = { @JoinColumn(name = "role_id", nullable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false) })
	private Set<User> users;

	public String getName() {
		return name;
	}

	public void setName(String role) {
		this.name = role;
	}
}