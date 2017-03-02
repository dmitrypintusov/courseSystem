package by.pvt.pintusov.courses.utils;

import by.pvt.pintusov.courses.constants.WebConstants;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.security.CustomUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Principal util
 * Project: courses
 * Created by: dpintusov
 * Date: 17.02.17.
 */

@Component
public class PrincipalUtil {
	public User getPrincipal() {
		User user = new User();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CustomUser) {
			user.setFirstName(((CustomUser) principal).getUserFirstName());
			user.setLastName(((CustomUser) principal).getUserLastName());
			user.setLogin(((CustomUser) principal).getUserLogin());
		} else {
			user = new User ();
			user.setFirstName(WebConstants.ANONYMOUS_USER);
		}
		return user;
	}
}
