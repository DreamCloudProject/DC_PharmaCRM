package ru.dreamcloud.alexion.model.authentication;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*delimiter $$

CREATE TABLE `common_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `common_roles_rules_rel` (
  `role_id` int(11) NOT NULL,
  `rule_id` int(11) NOT NULL,
  `allow` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`rule_id`),
  KEY `fk_role_rr_rel_idx` (`role_id`),
  KEY `fk_rule_rr_rel_idx` (`rule_id`),
  CONSTRAINT `fk_role_rr_rel` FOREIGN KEY (`role_id`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rule_rr_rel` FOREIGN KEY (`rule_id`) REFERENCES `common_rules` (`rule_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
	
	@ManyToMany(mappedBy = "roles")
    private List<CommonRule> rules;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}, mappedBy="role")
	private List<CommonUserInfo> users;

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

	public List<CommonRule> getRules() {
		return rules;
	}

	public void setRules(List<CommonRule> rules) {
		this.rules = rules;
	}	
	
	public List<CommonUserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<CommonUserInfo> users) {
		this.users = users;
	}

}