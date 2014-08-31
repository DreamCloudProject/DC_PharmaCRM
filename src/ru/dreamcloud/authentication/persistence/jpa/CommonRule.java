package ru.dreamcloud.authentication.persistence.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/*delimiter $$

CREATE TABLE `common_rules` (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8$$
*/
@Entity
@Table(name = "common_rules")
public class CommonRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rule_id")
	private int ruleId;

	@Column(name = "component_name")
	private String componentName;

	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},mappedBy = "rule")
	private List<RuleAssociation> roles;

	public CommonRule() {
	}

	public int getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public List<RuleAssociation> getRoles() {
		return roles;
	}

	public void setRoles(List<RuleAssociation> roles) {
		this.roles = roles;
	}
}