package ru.dreamcloud.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationFilter implements Filter {
	private FilterConfig config = null;
	private boolean active = false;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String act = config.getInitParameter("active");
		if (act != null) {
			active = (act.toUpperCase().equals("TRUE"));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (active) {
			String jsessionId = ((HttpServletRequest)request).getSession().getId();
			System.out.println(jsessionId);
		}
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		config = null;
	}

}
