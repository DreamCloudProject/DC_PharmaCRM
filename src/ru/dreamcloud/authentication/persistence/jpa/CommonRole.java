package ru.dreamcloud.authentication.persistence.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the common_roles database table.
 * 
 */
@Entity
@Table(name="common_roles")
public class CommonRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;

	private String allow;

	@Column(name="component_id")
	private String componentId;

	private String description;

	private String title;

	public CommonRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getAllow() {
		return this.allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getComponentId() {
		return this.componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}