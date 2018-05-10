package org.dubbo.demo.api;

import org.dubbo.demo.domain.User;

public interface UserService {

	User getUserById(Long id);
}
