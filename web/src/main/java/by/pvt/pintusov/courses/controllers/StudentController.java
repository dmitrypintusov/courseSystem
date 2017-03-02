package by.pvt.pintusov.courses.controllers;

import by.pvt.pintusov.courses.constants.PagePath;
import by.pvt.pintusov.courses.constants.Parameters;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.managers.PagePathManager;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.pojos.Mark;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.ICourseService;
import by.pvt.pintusov.courses.services.IMarkService;
import by.pvt.pintusov.courses.services.IUserService;
import by.pvt.pintusov.courses.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Student controller
 * using Spring
 * Project: courses
 * Created by: dpintusov
 * Date: 17.02.17.
 */

@Controller
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICourseService courseService;
	@Autowired
	private IMarkService markService;
	@Autowired
	private PagePathManager pagePathManager;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PrincipalUtil principalUtil;

	@RequestMapping (value = "/main", method = GET)
	public String showStudentMainPage (ModelMap modelMap) {
		modelMap.addAttribute(Parameters.USER, principalUtil.getPrincipal());
		return pagePathManager.getProperty(PagePath.STUDENT_PAGE_PATH);
	}

	@RequestMapping(value = "/attendcourse", method = GET)
	public String showAttendCoursePage () {
		return pagePathManager.getProperty(PagePath.STUDENT_ATTEND_COURSE_PATH);
	}

	/**
	 * Showing list of courses
	 * user can attend
	 * @param modelMap - model map parameter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping(value = "/attendcourse", method = POST)
	public String attendCourse (ModelMap modelMap,
	                            Locale locale) {
		String pagePath;
		try {
			List<Course> courseList = courseService.getAll();
			modelMap.addAttribute(Parameters.COURSE_LIST, courseList);
			pagePath = pagePathManager.getProperty(PagePath.STUDENT_ATTEND_COURSE_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}

	/**
	 * Showing list of teachers
	 * @param modelMap - model map parameter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/teachers", method = GET)
	public String showTeachersPage (ModelMap modelMap,
	                                Locale locale) {
		String pagePath;
		try {
			List<User> userList = userService.getAll();
			modelMap.addAttribute(Parameters.USERS_LIST, userList);
			pagePath = pagePathManager.getProperty(PagePath.STUDENT_SHOW_TEACHERS_PAGE_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}

	@RequestMapping (value = "/marks", method = GET)
	public String showMarksPage (ModelMap modelMap,
	                             Locale locale) {
		String pagePath;
		try {
			List<Mark> markList = markService.getAll();
			modelMap.addAttribute(Parameters.MARK_LIST, markList);
			pagePath = pagePathManager.getProperty(PagePath.STUDENT_SHOW_MARKS_PAGE_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}
}
