package ru.dreamcloud.pharmatracker.model.test;

import java.lang.reflect.Field;
import java.util.List;

import ru.dreamcloud.util.jpa.DataSourceLoader;

public class TestEntities {

	public static void main(String[] args) {
		List<Object> all = DataSourceLoader.getInstance().getAllEntities(null);
		String term = "пац";
		try {
			for (Object object : all) {
				Class c = object.getClass();
				String s = c.getName();
				System.out.println("***********************************");
				System.out.println(s);
				Field[] publicFields = c.getDeclaredFields();
				System.out.println("***********************************");
				for (Field field : publicFields) {
					Class fieldType = field.getType();
					if (fieldType.getName().equals(String.class.getName())) {
						field.setAccessible(true);
						System.out.println("-----------------------------------");
						System.out.println("Имя: " + field.getName());
						System.out.println("Тип: " + fieldType.getName());
						if((field.get(object) != null) 
							&& (field.get(object).toString().toLowerCase().indexOf(term.toLowerCase()) != -1)){
							System.out.println("Значение: " + field.get(object));							
						}
					}
				}
				System.out.println("-----------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
