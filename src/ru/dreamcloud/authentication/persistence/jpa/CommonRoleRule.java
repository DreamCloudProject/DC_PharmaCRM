package ru.dreamcloud.authentication.persistence.jpa;

import java.io.Serializable;
import javax.persistence.*;


/*delimiter $$

CREATE TABLE `common_role_rules` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` int(11) DEFAULT NULL,
  `component_name` varchar(255) DEFAULT NULL,
  `allow` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`rule_id`),
  KEY `fk_role_common_role_rule_idx` (`role`),
  CONSTRAINT `fk_role_common_role_rule` FOREIGN KEY (`role`) REFERENCES `common_roles` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name="common_role_rules")
public class CommonRoleRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rule_id")
	private int ruleId;

	private String allow;

	@Column(name="component_name")
	private String componentName;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
    @JoinColumn(name = "role")
	private CommonRole role;

	public CommonRoleRule() {
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getAllow() {
		return this.allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public CommonRole getRole() {
		return role;
	}

	public void setRole(CommonRole role) {
		this.role = role;
	}	

}