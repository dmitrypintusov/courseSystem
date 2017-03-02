package by.pvt.pintusov.courses.constants;

/**
 * Dao constants
 * Project: courses
 * Created by: USER
 * Date: 20.01.17.
 */
public class DaoConstants {
	public static final String ERROR_DAO = "Error was thrown in DAO: ";
	public static final String ERROR_USER_BY_LOGIN = "Unable to return user by login. Error was thrown in DAO: ";
	public static final String ERROR_COURSE_STATUS = "Unable to check course status. Error was thrown in DAO: ";
	public static final String ERROR_ACCESS_LEVEL_TYPE = "Unable to return access level type. Error was thrown in DAO: ";
	public static final String ERROR_COURSES_LIST = "Unable to return list of courses. Error was thrown in DAO: ";
	public static final String ERROR_COURSE_NAME = "Unable to return course by name. Error was thrown in DAO: ";

	public static final String PARAMETER_USER_LOGIN = "login";
	public static final String PARAMETER_COURSE_STATUS = "courseStatus";
	public static final String PARAMETER_ACCESS_LEVEL_TYPE = "accessLevelType";
	public static final String PARAMETER_COURSE_NAME = "courseName";

	public static final String HQL_GET_BY_LOGIN = "FROM User U WHERE U.login =:login";
	public static final String HQL_GET_BY_ACCESS_LEVEL = "FROM AccessLevel WHERE accessLevelType = :accessLevelType";
	public static final String HQL_GET_COURSES = "FROM Course ";

	private DaoConstants () {}
}
