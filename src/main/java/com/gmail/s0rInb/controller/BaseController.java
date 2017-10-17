package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private RevisionListener revisionListener;
	private static final Logger logger = LogManager.getLogger(BaseController.class);
	@PostConstruct
	protected abstract void init();
	public User getUser() {
		User user = (User) request.getAttribute("user");
		ScopeComponent.setCurrentUser(user);
		return user;
	}
}
