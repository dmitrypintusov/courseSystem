package by.pvt.pintusov.courses.controllers;

import by.pvt.pintusov.courses.constants.PagePath;
import by.pvt.pintusov.courses.constants.Parameters;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.managers.PagePathManager;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.ICourseService;
import by.pvt.pintusov.courses.services.IMarkService;
import by.pvt.pintusov.courses.services.IUserService;
import by.pvt.pintusov.courses.utils.PrincipalUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Teacher controller
 * with Spring
 * Project: courses
 * Created by: dpintusov
 * Date: 17.02.17.
 */
@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
	private static Logger logger = Logger.getLogger(TeacherController.class);

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

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showTeacherMainPage (ModelMap modelMap) {
		modelMap.addAttribute(Parameters.USER, principalUtil.getPrincipal());
		return pagePathManager.getProperty(PagePath.TEACHER_PAGE_PATH);
	}

	@RequestMapping(value = "/addcourse", method = GET)
	public String showAddCoursePage(){
		return pagePathManager.getProperty(PagePath.TEACHER_ADD_COURSE_PATH);
	}

	/**
	 * Adding course
	 * @param modelMap - model map parameter
	 * @param course - course entity
	 * @param bindingResult - binding result parameter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping(value = "/addcourse", method = POST)
	public String addCourse (ModelMap modelMap,
	                       Course course,
	                       BindingResult bindingResult,
	                       Locale locale) {
		String pagePath;
			if(!bindingResult.hasErrors()) {
				try {
					courseService.startCourse(course);
					modelMap.addAttribute(Parameters.SUCCESS_OPERATION, messageSource.getMessage("message.successoperation", null, locale));
					pagePath = pagePathManager.getProperty(PagePath.TEACHER_ADD_COURSE_PATH);
				} catch (ServiceException e) {
					modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror",null, locale));
					pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
				} catch (NullPointerException e) {
					logger.error("Error in Teacher Controller add course method: ", e);
					pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
				}
			} else {
				pagePath = pagePathManager.getProperty(PagePath.REGISTRATION_PAGE_PATH);
			}
		return pagePath;
	}

	/**
	 * Showing list of students
	 * @param modelMap - model map parameter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/students", method = GET)
	public String showStudentsPage (ModelMap modelMap,
	                                Locale locale) {
		String pagePath;
		try {
			List<User> userList = userService.getAll();
			modelMap.addAttribute(Parameters.USERS_LIST, userList);
			pagePath = pagePathManager.getProperty(PagePath.TEACHER_SHOW_STUDENTS_PAGE);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}


	@RequestMapping (value = "/endcourse", method = GET)
	public String endCoursePage () {
		return pagePathManager.getProperty(PagePath.TEACHER_END_COURSE_PATH);
	}

	/**
	 * End course function
	 * changes course status to ENDED
	 * @param modelMap - model map parameter
	 * @param endCourse - course name to end
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/endcourse", method = POST)
	public String endCourse (ModelMap modelMap,
	                           @RequestParam(value = Parameters.COURSE_END, required = false)String endCourse,
	                           Locale locale) {
		String pagePath;
			try {
				if (!courseService.isCourseStatusEnded(endCourse)) {
					Course course = courseService.getByCourseName(endCourse);
					CourseStatusType courseStatus = CourseStatusType.ENDED;
					courseService.updateCourseStatus(course.getCourseName(), courseStatus);
					modelMap.addAttribute(Parameters.SUCCESS_OPERATION, messageSource.getMessage("message.successoperation", null, locale));
					pagePath = pagePathManager.getProperty(PagePath.TEACHER_END_COURSE_PATH);
				} else {
					modelMap.addAttribute(Parameters.COURSE_STATUS_ENDED, messageSource.getMessage("message.courseended", null, locale));
					pagePath = pagePathManager.getProperty(PagePath.TEACHER_END_COURSE_PATH);
				}
			} catch (ServiceException e) {
				modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
				pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
			}
			return pagePath;
	}

	/**
	 * Show list of courses
	 * @param modelMap - model map parameter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/courses", method = GET)
	public String showCoursesPage (ModelMap modelMap,
	                                Locale locale) {
		String pagePath;
		try {
			List<Course> courseList = courseService.getAll();
			modelMap.addAttribute(Parameters.COURSE_LIST, courseList);
			pagePath = pagePathManager.getProperty(PagePath.TEACHER_COURSES_PAGE);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}

	@RequestMapping (value = "/putmark", method = GET)
	public String putMarkPage () {
		return pagePathManager.getProperty(PagePath.TEACHER_PUT_MARK_PATH);
	}

	/**
	 * Putting mark to student
	 * @param modelMap - model map parameter
	 * @param studentId - student id
	 * @param studentMark - student mark
	 * @param courseName - course name
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/putmark", method = POST)
	public String archiveCourse (ModelMap modelMap,
	                             @RequestParam(value = Parameters.STUDENT_ID, required = false)Integer studentId,
	                             @RequestParam(value = Parameters.STUDENT_MARK, required = false)Integer studentMark,
	                             @RequestParam(value = Parameters.COURSE_NAME, required = false)String courseName,
	                             Locale locale) {
		String pagePath;
		try {
				markService.addMark(studentId, studentMark, courseName);
				modelMap.addAttribute(Parameters.SUCCESS_OPERATION, messageSource.getMessage("message.successoperation", null, locale));
				pagePath = pagePathManager.getProperty(PagePath.TEACHER_PUT_MARK_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}
}
