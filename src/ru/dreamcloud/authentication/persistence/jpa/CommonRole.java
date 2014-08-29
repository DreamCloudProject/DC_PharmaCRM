package ru.dreamcloud.authentication.persistence.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*delimiter $$

CREATE TABLE `common_roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `common_roles_rules_rel` (
  `role_id` int(11) NOT NULL,
  `rule_id` int(11) NOT NULL,
  `allow` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`rule_id`),
  KEY `fk_role_roles_rules_id_idx` (`role_id`),
  KEY `fk_rule_roles_rules_rel_idx` (`rule_id`),
  CONSTRAINT `fk_role_roles_rules_id` FOREIGN KEY (`role_id`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rule_roles_rules_rel` FOREIGN KEY (`rule_id`) REFERENCES `common_rules` (`rule_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
	
	@OneToMany(cascade={CascadeType.PERSIST},mappedBy="role")
    private List<RuleAssociation> rules;

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

	public List<RuleAssociation> getRules() {
		return rules;
	}

	public void setRules(List<RuleAssociation> rules) {
		this.rules = rules;
	}
	
	public void addRule(CommonRule rule, String allow) {
		RuleAssociation association = new RuleAssociation();		
		association.setRole(this);
		association.setRule(rule);
		association.setRoleId(this.getRoleId());
		association.setRuleId(rule.getRuleId());
		association.setAllow(allow);

		this.rules.add(association);
		rule.getRoles().add(association);
	}
}