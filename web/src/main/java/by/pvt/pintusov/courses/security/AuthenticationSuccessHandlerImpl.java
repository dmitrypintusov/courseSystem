package by.pvt.pintusov.courses.security;

import by.pvt.pintusov.courses.constants.PagePath;
import by.pvt.pintusov.courses.constants.Parameters;
import by.pvt.pintusov.courses.constants.WebConstants;
import by.pvt.pintusov.courses.managers.PagePathManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Project: courses
 * Created by: USER
 * Date: 16.02.17.
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	private Logger logger =Logger.getLogger(AuthenticationSuccessHandlerImpl.class);
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private PagePathManager pagePathManager;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	/**
	 * Check if target url is allowed
	 * @param request - http request
	 * @param response - http response
	 * @param authentication - user authentication
	 * @throws IOException
	 */
	protected void handle (HttpServletRequest request,
	                       HttpServletResponse response,
	                       Authentication authentication) throws IOException {
		String targetUrl = determineTargetUrl (authentication);
		if (response.isCommitted()) {
			logger.info(WebConstants.UNABLE_TO_REDIRECT + targetUrl);
			return;
		}
		request.getSession().setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, messageSource.getMessage("message.loginerror", null, request.getLocale()));
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Determine user authority
	 * and redirect to allowed pages
	 * @param authentication - user authentication
	 * @return path to page
	 */
	protected String determineTargetUrl (Authentication authentication) {
		boolean isTeacher = false;
		boolean isStudent = false;
		boolean isAdmin = false;
		String pagePath;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority: authorities) {
			if (grantedAuthority.getAuthority().equals(WebConstants.ROLE_TEACHER)) {
				isTeacher = true;
				break;
			}
			else if (grantedAuthority.getAuthority().equals(WebConstants.ROLE_STUDENT)) {
				isStudent = true;
				break;
			}
			else if (grantedAuthority.getAuthority().equals(WebConstants.ROLE_ADMIN)) {
				isAdmin = true;
				break;
			}
		}
		if (isTeacher) {
			pagePath = "/" + pagePathManager.getProperty(PagePath.TEACHER_PAGE_PATH);
		} else if (isStudent) {
			pagePath = "/" + pagePathManager.getProperty(PagePath.STUDENT_PAGE_PATH);
		} else if (isAdmin) {
			pagePath = "/" + pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
		} else {
			pagePath = pagePathManager.getProperty(PagePath.HOME_PAGE_PATH);
		}
		return pagePath;
	}

	/**
	 * Delete user authentication
	 * @param request - http request
	 */
	protected void clearAuthenticationAttributes (HttpServletRequest request) {
		HttpSession session;
		session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy (RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
