package ru.dreamcloud.pharmatracker.zk;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericInitiator;

import ru.dreamcloud.pharmatracker.zk.services.AuthenticationService;
import ru.dreamcloud.pharmatracker.zk.services.SchedulerService;

public class ApplicationInit extends GenericInitiator {
	private AuthenticationService authenticationService;
	private SchedulerService scheduler;
	
	public ApplicationInit() {	
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		initAuthenticationService();
		initSchedulerService();
	}
	
	private void initAuthenticationService() throws Exception {
		Session session = Sessions.getCurrent();
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		String loginPage = Labels.getLabel("pages.login.URL");
		if(requestPath.indexOf(loginPage) == -1){
	        authenticationService = new AuthenticationService();
			if(!authenticationService.initAccess()){
				Execution exec = Executions.getCurrent();
			    HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			    response.sendRedirect(loginPage);
			    exec.setVoided(true); //no need to create UI since redirect will take place			    
			}		
		}
	}
	
	private void initSchedulerService() {
		Session session = Sessions.getCurrent();
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		String loginPage = Labels.getLabel("pages.login.URL");
		if(requestPath.indexOf(loginPage) == -1){
			if(authenticationService.initAccess()){
				scheduler = (((SchedulerService)session.getAttribute("schedulerService")) != null) ? ((SchedulerService)session.getAttribute("schedulerService")) : new SchedulerService();
				scheduler.initSchedulerJobs();
				session.setAttribute("schedulerService", scheduler);
			}
		}
	}

}
