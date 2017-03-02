package by.pvt.pintusov.courses.controllers;

import by.pvt.pintusov.courses.constants.PagePath;
import by.pvt.pintusov.courses.constants.Parameters;
import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.filters.PaginationFilter;
import by.pvt.pintusov.courses.managers.PagePathManager;
import by.pvt.pintusov.courses.pojos.AccessLevel;
import by.pvt.pintusov.courses.pojos.Archive;
import by.pvt.pintusov.courses.pojos.Course;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.IArchiveService;
import by.pvt.pintusov.courses.services.ICourseService;
import by.pvt.pintusov.courses.services.IUserService;
import by.pvt.pintusov.courses.utils.OrderingUtil;
import by.pvt.pintusov.courses.utils.PaginationUtil;
import by.pvt.pintusov.courses.utils.PrincipalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Admin controller
 * using Spring
 * Project: courses
 * Created by: dpintusov
 * Date: 17.02.17.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes("filter")
public class AdminController {

	@Autowired
	private IUserService userService;
	@Autowired
	private ICourseService courseService;
	@Autowired
	private IArchiveService archiveService;
	@Autowired
	private PagePathManager pagePathManager;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PrincipalUtil principalUtil;

	@ModelAttribute("filter")
	public PaginationFilter createFilter() {
		return new PaginationFilter();
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String showAdminMainPage (ModelMap modelMap) {
		modelMap.addAttribute(Parameters.USER, principalUtil.getPrincipal());
		return pagePathManager.getProperty(PagePath.ADMIN_PAGE_PATH);
	}

	@RequestMapping (value = "/maketeacher", method = GET)
	public String endCoursePage () {
		return pagePathManager.getProperty(PagePath.ADMIN_MAKE_TEACHER_PATH);
	}

	/**
	 * Giving status of TEACHER to User
	 * @param modelMap - model map parameter
	 * @param login - user login
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/maketeacher", method = POST)
	public String makeTeacher (ModelMap modelMap,
	                         @RequestParam(value = Parameters.USER_LOGIN, required = false)String login,
	                         Locale locale) {
		String pagePath;
			try {
				User user = userService.getUserByLogin(login);
				AccessLevelType accessLevelType = AccessLevelType.TEACHER;
				AccessLevel accessLevel = new AccessLevel();
				accessLevel.setAccessLevelType(accessLevelType);
				user.addAccessLevel(accessLevel);
				userService.saveOrUpdate(user);
				modelMap.addAttribute(Parameters.SUCCESS_OPERATION, messageSource.getMessage("message.successoperation", null, locale));
				pagePath = pagePathManager.getProperty(PagePath.ADMIN_MAKE_TEACHER_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}

	@RequestMapping (value = "/archive", method = GET)
	public String archiveCoursePage () {
		return pagePathManager.getProperty(PagePath.ADMIN_ARCHIVE_COURSE_PATH);
	}

	/**
	 * Putting course into archive
	 * @param modelMap - model map parameter
	 * @param archiveCourse - course name to put in archive
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping (value = "/archive", method = POST)
	public String archiveCourse (ModelMap modelMap,
	                         @RequestParam(value = Parameters.COURSE_ARCHIVE, required = false)String archiveCourse,
	                         Locale locale) {
		String pagePath;
		try {
			/*Checking if course status is ended, if not showing message*/
			if (courseService.isCourseStatusEnded(archiveCourse)) {
				Course course = courseService.getByCourseName(archiveCourse);
				Archive archive = new Archive();
				archive.addCourseToArchive(course);
				archiveService.saveOrUpdate(archive);
				courseService.updateCourseStatus(course.getCourseName(), CourseStatusType.ARCHIVE);
				modelMap.addAttribute(Parameters.SUCCESS_OPERATION, messageSource.getMessage("message.successoperation", null, locale));
				pagePath = pagePathManager.getProperty(PagePath.ADMIN_ARCHIVE_COURSE_PATH);
			} else {
				modelMap.addAttribute(Parameters.COURSE_STATUS_OPEN, messageSource.getMessage("message.courseopen", null, locale));
				pagePath = pagePathManager.getProperty(PagePath.ADMIN_ARCHIVE_COURSE_PATH);
			}
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}

	/**
	 * Show courses list
	 * with pagination and sorting option
	 * @param modelMap - model map parameter
	 * @param filter - pagination filter
	 * @param locale - locale parameter
	 * @return path to page
	 */
	@RequestMapping(value = "/courses", method = {GET, POST})
	public String showCoursesPage (ModelMap modelMap,
	                               @ModelAttribute ("filter") PaginationFilter filter,
	                               Locale locale) {
		String pagePath;
		Integer currentPage = filter.getCurrentPage();
		Integer recordsPerPage = filter.getRecordsPerPage();
		String ordering = filter.getOrdering();
		String direction = filter.getDirection();
		filter = PaginationUtil.defineParameters(ordering, direction, currentPage, recordsPerPage);
		try {
			Integer numberOfPages = courseService.getNumberOfPages(filter.getRecordsPerPage());
			String order = OrderingUtil.defineOrderingType(filter.getOrdering() + OrderingUtil.defineOrderingDirection(filter.getDirection()));
			List<Course> courseList = courseService.getAllToPage(filter.getRecordsPerPage(), filter.getCurrentPage(), order);
			modelMap.addAttribute(Parameters.COURSE_LIST, courseList);
			modelMap.addAttribute(Parameters.NUMBER_OF_PAGES, numberOfPages);
			modelMap.addAttribute(Parameters.FILTER, filter);
			pagePath = pagePathManager.getProperty(PagePath.ADMIN_SHOW_COURSES_PATH);
		} catch (ServiceException e) {
			modelMap.addAttribute(Parameters.ERROR_DATABASE, messageSource.getMessage("message.databaseerror", null, locale));
			pagePath = pagePathManager.getProperty(PagePath.ERROR_PAGE_PATH);
		}
		return pagePath;
	}
}
