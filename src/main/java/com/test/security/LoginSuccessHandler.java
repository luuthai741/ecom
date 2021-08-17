package com.test.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.test.entities.security.Role;
import com.test.entities.security.User;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		User user = userPrinciple.getUser();
		for(Role role : user.getRoles()) {
			if(role.getName().name().equals("ROLE_ADMIN")) {
				response.sendRedirect("/admin/");
			}
			else {
				response.sendRedirect("/");
			}
		}
	}


}
