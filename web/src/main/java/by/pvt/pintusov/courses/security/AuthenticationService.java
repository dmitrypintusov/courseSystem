package by.pvt.pintusov.courses.security;

import by.pvt.pintusov.courses.constants.WebConstants;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Authentication service
 * Project: courses
 * Created by: dpintusov
 * Date: 16.02.17.
 */
@Service("authenticationService")
public class AuthenticationService implements UserDetailsService {
	private static Logger logger = Logger.getLogger(AuthenticationService.class);

	@Autowired
	private IUserService userService;

	/**
	 * Secured loading user by it login
	 * @param login
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	@Transactional(readOnly = true)
	public CustomUser loadUserByUsername (String login) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userService.getUserByLogin(login);
			if (user == null) {
				logger.info("User with login - " + login + " - not found");
				throw new UsernameNotFoundException(WebConstants.USER_NOT_FOUND);
			}
		} catch (ServiceException e) {
			logger.error(e);
		}
		return new CustomUser(user, true, true, true, true, getGrantedAuthorities (user));
	}

	private List<GrantedAuthority> getGrantedAuthorities (User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (AccessLevel access: user.getAccessLevels()) {
			authorities.add(new SimpleGrantedAuthority(WebConstants.ROLE_PREFIX + access.getAccessLevelType().toString().toUpperCase()));
		}
		return authorities;
	}
}
