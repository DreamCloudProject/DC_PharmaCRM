package ru.dreamcloud.authentication.persistence.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "common_roles_rules_rel")
@IdClass(RuleAssociationId.class)
public class RuleAssociation {
	@Id
	@Column(name = "role", insertable=false, updatable=false)
	private int roleId;
	
	@Id
	@Column(name = "rule", insertable=false, updatable=false)
	private int ruleId;
	
	private String allow;
	
	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinColumn(name = "role", referencedColumnName = "role_id")
	private CommonRole role;	

	@ManyToOne(cascade={CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinColumn(name = "rule", referencedColumnName = "rule_id")
	private CommonRule rule;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public CommonRole getRole() {
		return role;
	}

	public void setRole(CommonRole role) {
		this.role = role;
	}

	public CommonRule getRule() {
		return rule;
	}

	public void setRule(CommonRule rule) {
		this.rule = rule;
	}

}
