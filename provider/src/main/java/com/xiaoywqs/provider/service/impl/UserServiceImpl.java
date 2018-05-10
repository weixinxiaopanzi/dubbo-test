package com.xiaoywqs.provider.service.impl;

import org.dubbo.demo.api.UserService;
import org.dubbo.demo.domain.User;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "1.0.1")
public class UserServiceImpl implements UserService {

	public User getUserById(Long id) {
		User user = new User();
		user.setId(id);
		user.setUserName("Test2");
		user.setPassword("456789");
		return user;
	}

}
