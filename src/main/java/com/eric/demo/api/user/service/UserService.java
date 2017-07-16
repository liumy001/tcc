package com.eric.demo.api.user.service;

import com.eric.demo.api.user.domain.User;
import com.eric.demo.api.user.domain.UserCriteria;
import com.eric.demo.commons.service.BaseService;

public interface UserService extends BaseService<User, UserCriteria> {
	User findOneByUsername(String username);
	
	void changePassword(String newPassword);
}
