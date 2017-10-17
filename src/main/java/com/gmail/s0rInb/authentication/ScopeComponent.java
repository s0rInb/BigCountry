package com.gmail.s0rInb.authentication;

import com.gmail.s0rInb.controller.BaseController;
import com.gmail.s0rInb.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScopeComponent {
	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		ScopeComponent.currentUser = currentUser;
	}
}


