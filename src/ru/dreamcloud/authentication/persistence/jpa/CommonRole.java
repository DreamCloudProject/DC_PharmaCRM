package ru.dreamcloud.authentication.persistence.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import ru.dreamcloud.alexion.model.Region;


/*delimiter $$

CREATE TABLE `common_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
 */
@Entity
@Table(name="common_roles")
public class CommonRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private int roleId;

	private String description;

	private String title;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="role")
    private List<CommonRoleRule> rules;

	public CommonRole() {
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public List<CommonRoleRule> getRules() {
		return rules;
	}

	public void setRules(List<CommonRoleRule> rules) {
		this.rules = rules;
	}	

}