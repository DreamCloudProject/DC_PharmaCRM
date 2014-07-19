import java.sql.Date;
import java.util.Calendar;

import ru.dreamcloud.pharmacrm.model.District;
import ru.dreamcloud.pharmacrm.model.Event;
import ru.dreamcloud.pharmacrm.model.Patient;
import ru.dreamcloud.pharmacrm.model.Region;
import ru.dreamcloud.pharmacrm.model.Resolution;
import ru.dreamcloud.pharmacrm.utils.DataSourceLoader;


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
		
		
	}

}
