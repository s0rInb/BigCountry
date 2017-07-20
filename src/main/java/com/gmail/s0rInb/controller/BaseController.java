package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
	@Autowired
	private HttpServletRequest request;

	@PostConstruct
	protected abstract void init();
	public User getUser() {
		return (User) request.getAttribute("user");
	}
}
