package com.test.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.entities.security.Role;
import com.test.entities.security.RoleType;
import com.test.entities.security.User;
import com.test.mail.SendMail;
import com.test.repo.RoleRepository;
import com.test.repo.UserRepository;
import com.test.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;
	private SendMail sendMail;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
			RoleRepository roleRepository, SendMail sendMail) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
		this.sendMail = sendMail;
	}

	@Override
	public List<User> findAll() {
		log.info("Fetch data user");
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {
		log.info("Fetch data user by id {}", id);
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void save(User user) {
		if (user.getId() != 0) {
			userRepository.save(user);
		}
		if (userRepository.existsByUsername(user.getUsername())) {
			log.warn("Duplicate username");
			throw new IllegalStateException("Tên tài khoản đã được sử dụng !");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			log.warn("Duplicate email");
			throw new IllegalStateException("Email đã được sử dụng !");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findByName(RoleType.ROLE_USER);
		roles.add(role);
		user.setRoles(roles);
		user.setEnabled(false);
		user.setVerifyToken(UUID.randomUUID().toString());

		userRepository.save(user);
	}

	@Override
	public void deleteByID(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public void sigin(String email, String link) {
		User user = findByEmail(email);
		log.info("Fetch user{}", user);
		try {
			sendMail.verify(user, link);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

	}

	@Override
	public boolean verify(String verifyToken) {
		User user = userRepository.findByVerifyToken(verifyToken).orElse(null);
		log.info("User{}", user);
		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setEnabled(true);
			user.setVerifyToken(null);
			userRepository.save(user);
			log.info("Enabler user success {} ", user);
			return true;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public void forgot(String email, String link) {
		try {
			User user = userRepository.findByEmail(email).orElse(null);
			if (user != null) {
				user.setResetToken(UUID.randomUUID().toString());
				userRepository.save(user);
				sendMail.resetPassword(user, link);
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	@Override
	public boolean checkResetToken(String resetToken) {
		User user = userRepository.findByResetToken(resetToken).orElse(null);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void reset(String resetToken, String password) {
		User user = userRepository.findByResetToken(resetToken).orElse(null);
		log.info("User has update {}", user);
		if (user != null) {
			updatePassword(user, password);
		}
	}

	@Override
	public void updatePassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		user.setResetToken(null);
		userRepository.save(user);
		log.info("User has update {}", user);
	}

}
