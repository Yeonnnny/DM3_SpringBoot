package net.kdigital.board.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("Login Success");
		List<String> roleNames = new ArrayList<>();
		authentication.getAuthorities().forEach(authority->{
			roleNames.add(authority.getAuthority());
		});
		
		System.out.println("ROLE NAMES : "+roleNames);
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/adminPage");
			return;
		}
		if(roleNames.contains("ROLE_USER")) {
			response.sendRedirect("/");
			return;
		}
		response.sendRedirect("/");
		
	}
}