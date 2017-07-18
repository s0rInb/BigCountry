package com.gmail.s0rInb.authentication;

import com.gmail.s0rInb.entities.UserSession;
import com.gmail.s0rInb.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final String HTTP_REFERER = "referer";
	private static final List<String> AVAILVABLE_REFERERS_WHITHOUT_CHECK = Arrays.asList(
//			"www.performance-lab.ru",
//			"localhost:4200",
//			"192.168.14.24",
//			"34.249.149.28",
//			"192.168.19.21"
			"localhost:9292"
	);

	@Autowired
	private UserSessionRepository userSessionRepository;

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
//		if (request.getRequestURI().equals("/api/getTreeTableOperatingIndexesByCompanyWithOutSession")) {
//			return true;
//		}

		String referer = Optional.ofNullable(request.getHeader(HTTP_REFERER)).orElse("");
		if (AVAILVABLE_REFERERS_WHITHOUT_CHECK.stream().anyMatch(referer::contains)) {
			return true;
		}

		UserSession userSession = null;
		if (!uri.endsWith("loginUpdate") && !uri.endsWith("logout") && !uri.endsWith("/")) {
			if (request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
					userSession = userSessionRepository.findByToken(cookie.getValue());
					if (userSession != null) break;
				}
				if (userSession != null) {
					request.setAttribute("user", userSession.getUser());
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return false;
				}
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
		}
		return true;
	}
}