package com.test.repo;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.UserInfo;
import com.test.entities.security.User;

@Repository
@Transactional
public interface InfoRepository extends CrudRepository<UserInfo, Integer> {
	Optional<UserInfo> findByUser(User user);
}
