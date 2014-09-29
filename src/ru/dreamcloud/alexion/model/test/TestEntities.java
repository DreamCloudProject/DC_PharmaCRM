package ru.dreamcloud.alexion.model.test;

import java.util.ArrayList;
import java.util.List;

import ru.dreamcloud.alexion.model.authentication.CommonRole;
import ru.dreamcloud.alexion.model.authentication.CommonRule;
import ru.dreamcloud.alexion.model.authentication.RuleAssociation;
import ru.dreamcloud.util.jpa.DataSourceLoader;


public class TestEntities {

	public static void main(String[] args) {
		List<RuleAssociation> rules = new ArrayList<RuleAssociation>();
		
		CommonRole role = new CommonRole();		
		role.setTitle("Новая тестовая роль");
		role.setDescription("Описание тест");
		role.setRules(rules);
		
		List<RuleAssociation> roles = new ArrayList<RuleAssociation>();
		
		CommonRule rule = new CommonRule();
		rule.setComponentName("Просто компонент");
		rule.setRoles(roles);
		
		role.addRule(rule, "true");
		
		List<RuleAssociation> currentRulesList = new ArrayList(DataSourceLoader.getInstance().fetchRecords("RuleAssociation", "where e.roleId=12"));
		
		/*for (RuleAssociation ruleAssociation : currentRulesList) {
			System.out.println(ruleAssociation.getRole().getTitle()+" - "+ruleAssociation.getRule().getComponentName() + ": "+ ruleAssociation.getAllow() );
		}*/
		
		/*CommonRole roleToDelete = (CommonRole)DataSourceLoader.getInstance().getRecord(CommonRole.class, 13);
		CommonRule ruleToDelete = (CommonRule)DataSourceLoader.getInstance().getRecord(CommonRule.class, 15);
		for (RuleAssociation ruleAssociation : roleToDelete.getRules()) {
			DataSourceLoader.getInstance().removeRecord(ruleAssociation);
		}
		DataSourceLoader.getInstance().removeRecord(roleToDelete);
		
		for (RuleAssociation ruleAssociation : ruleToDelete.getRoles()) {
			DataSourceLoader.getInstance().removeRecord(ruleAssociation);
		}
		DataSourceLoader.getInstance().removeRecord(ruleToDelete);*/

	}

}
