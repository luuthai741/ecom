package com.test.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.entities.security.User;
import com.test.repo.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Không thể tìm thấy người dùng với tên " + username));
		log.debug("Kiểm tra user{} để đăng nhập",user);
		return new UserPrinciple(user);
	}

}
