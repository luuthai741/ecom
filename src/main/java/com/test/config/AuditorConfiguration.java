package com.test.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.test.security.UserPrinciple;

@Component
//Config @CreateBy & @ModifiedBy
@EnableJpaAuditing
public class AuditorConfiguration implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

		return Optional.of(userPrinciple.getUsername());
	}

}
