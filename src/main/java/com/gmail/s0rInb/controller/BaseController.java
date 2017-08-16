package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
	@Autowired
	private HttpServletRequest request;

	private static final Logger logger = LogManager.getLogger(BaseController.class);
	@PostConstruct
	protected abstract void init();
	public User getUser() {
		return (User) request.getAttribute("user");
	}
}
