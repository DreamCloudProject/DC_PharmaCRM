package ru.dreamcloud.pharmatracker.model.authentication;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*delimiter $$

CREATE TABLE `common_rules` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name = "common_rules")
public class CommonRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_id")
	private Integer ruleId;

	@Column(name = "component_name")
	private String componentName;

	@ManyToMany
    @JoinTable(name = "common_roles_rules_rel",
            joinColumns = @JoinColumn(name = "rule"),
            inverseJoinColumns = @JoinColumn(name = "role"))
	private List<CommonRole> roles;

	public CommonRule() {
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public List<CommonRole> getRoles() {
		return roles;
	}

	public void setRoles(List<CommonRole> roles) {
		this.roles = roles;
	}
}