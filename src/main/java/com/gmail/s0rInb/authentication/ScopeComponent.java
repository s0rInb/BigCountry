package com.gmail.s0rInb.authentication;

import com.gmail.s0rInb.controller.BaseController;
import com.gmail.s0rInb.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ScopeComponent extends BaseController {
	public static User currentUser = new User();

	public static User getCurrentUser() {
		return currentUser;
	}

	private static void setCurrentUser(User currentUser) {
		ScopeComponent.currentUser = currentUser;
	}


	@Override
	public void init() {
		setCurrentUser(getUser());
	}
}


