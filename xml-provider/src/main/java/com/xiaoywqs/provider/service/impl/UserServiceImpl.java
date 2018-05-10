package com.xiaoywqs.provider.service.impl;

import org.dubbo.demo.api.UserService;
import org.dubbo.demo.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	public User getUserById(Long id) {
		User user = new User();
		user.setId(id);
		user.setUserName("Test");
		user.setPassword("123456");
		return user;
	}

}
