package ru.dreamcloud.alexion.model.test;

import java.util.ArrayList;
import java.util.List;

import ru.dreamcloud.alexion.model.Event;
import ru.dreamcloud.alexion.model.EventReason;
import ru.dreamcloud.alexion.model.authentication.CommonRole;
import ru.dreamcloud.alexion.model.authentication.CommonRule;
import ru.dreamcloud.alexion.model.authentication.RuleAssociation;
import ru.dreamcloud.util.jpa.DataSourceLoader;


public class TestEntities {

	public static void main(String[] args) {
		EventReason evr = new EventReason();
		
		evr.setTitle("Text reason1");
		evr.setDescription("Text Description");
		
		//DataSourceLoader.getInstance().addRecord(evr);
		
		EventReason evr2 = (EventReason)DataSourceLoader.getInstance().getRecord(EventReason.class, 2);
		
		evr2.setTitle("New Title");
		
		DataSourceLoader.getInstance().removeRecord(evr2);

	}

}
