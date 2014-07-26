package ru.dreamcloud.alexion.model.test;
import ru.dreamcloud.alexion.model.District;
import ru.dreamcloud.alexion.model.Region;
import ru.dreamcloud.alexion.utils.DataSourceLoader;

public class TestEntities {
	
	public static void main(String[] args) {
		/*Resolution res = (Resolution)DataSourceLoader.getInstance().getRecord(Resolution.class, 5);
		
		
		Calendar calendar = Calendar.getInstance();		 
		java.util.Date currentDate = calendar.getTime();		 
		
		java.sql.Date date1 = new java.sql.Date(currentDate.getTime());
		  
		calendar.setTime(currentDate);		
		calendar.add(Calendar.DAY_OF_YEAR, 1);  
		
		java.util.Date tomorrow = calendar.getTime();		
		
		java.sql.Date date2 = new java.sql.Date(tomorrow.getTime());
		
		Patient patient = new Patient();
		patient.setAge(32);
		patient.setFirstname("Петр");
		patient.setLastname("Петров");
		patient.setMiddlename("Петрович");
		patient.setResolutionType(res);		
		
		DataSourceLoader.getInstance().addRecord(ev);
		DataSourceLoader.getInstance().addRecord(patient);
		Patient p = (Patient)DataSourceLoader.getInstance().getRecord(Patient.class, 5);
		
		Event ev = new Event();
		ev.setDateTimeStart(date1);
		ev.setDateTimeEnd(date2);
		ev.setTitle("Пациент добавлен!");
		ev.setDescription("Позвонить в прокуратуру!");
		ev.setPatient(p);
		
		DataSourceLoader.getInstance().addRecord(ev);
		
		
		District ds = (District)DataSourceLoader.getInstance().getRecord(District.class, 1);
		
		DataSourceLoader.getInstance().removeRecord(ds, 1);*/
		
		Region reg = new Region();
		
		reg.setTitle("Тест регион");
		reg.setDescription("Москва");		
		
		District ds = new District();
		ds.setTitle("Центр");
		ds.setDescription("Простой округ");
		
		reg.setDistrict(ds);		
		
		//DataSourceLoader.getInstance().addRecord(reg);
		//DataSourceLoader.getInstance().removeRecord(reg, 2);
		DataSourceLoader.getInstance().removeRecord(ds, 6);
	}

}
