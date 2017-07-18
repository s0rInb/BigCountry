package com.gmail.s0rInb.controller;

import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//public class UserController {

//	@Autowired
//	private UserService userService;
//	@RequestMapping(method = RequestMethod.POST, value = "loginUpdate", produces = "application/json")
//	@ResponseBody
//	public Response login(@RequestBody final User user){
//		Response response = new Response();
////		User byUsername = userService.findByUsername(user.getUsername());
////		if (byUsername==null){
////			throw new AccessDeniedException("user not found");
////		}
////		response.setEntity(byUsername);
////		response.setEntityClass("login");
//		return response;
//	}
//}
