package com.test.service;

import com.test.entities.security.User;

public interface UserService extends GenericService<User>{
	User findByUsername(String username);
	User findByEmail(String email);
	
	void sigin(String email, String link );
	boolean verify(String verifyToken);

	boolean checkResetToken(String resetToken);
	void forgot(String email, String link);
	
	void reset(String resetToken, String password);
	void updatePassword(User user, String password);
	
}
