package by.pvt.pintusov.courses.security;

import by.pvt.pintusov.courses.pojos.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom user with security
 * Project: courses
 * Created by: dpintusov
 * Date: 16.02.17.
 */
public class CustomUser extends org.springframework.security.core.userdetails.User {
	private User user;

	public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getLogin(), user.getPassword(), authorities);
		this.user = user;
	}

	public CustomUser(User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(user.getLogin(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	public String getUserFirstName () {
		return user.getFirstName();
	}

	public String getUserLastName () {
		return user.getLastName();
	}

	public String getUserLogin () {
		return user.getLogin();
	}
}
