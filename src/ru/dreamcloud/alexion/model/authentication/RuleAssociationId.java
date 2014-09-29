package ru.dreamcloud.alexion.model.authentication;

import java.io.Serializable;

public class RuleAssociationId implements Serializable {
	private int roleId;

	private int ruleId;

	public int hashCode() {
		return roleId + ruleId;
	}

	public boolean equals(Object object) {
		if (object instanceof RuleAssociationId) {
			RuleAssociationId otherId = (RuleAssociationId) object;
			return (otherId.roleId == this.roleId)
					&& (otherId.ruleId == this.ruleId);
		}
		return false;
	}
}
