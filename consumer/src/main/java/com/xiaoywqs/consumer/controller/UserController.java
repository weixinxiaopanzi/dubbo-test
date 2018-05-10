package com.xiaoywqs.consumer.controller;

import org.dubbo.demo.api.UserService;
import org.dubbo.demo.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("user")
public class UserController {

	@Reference(version = "1.0.1")
	private UserService userService;
	
	@RequestMapping("getById")
	public User getById(Long id){
		return userService.getUserById(id);
	}
}
