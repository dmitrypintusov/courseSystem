package by.pvt.pintusov.courses.utils;

import by.pvt.pintusov.courses.enums.AccessLevelType;
import by.pvt.pintusov.courses.enums.CourseStatusType;
import by.pvt.pintusov.courses.pojos.*;

import java.util.Calendar;
import java.util.Set;

/**
 * Utility class for building entities
 * @author dpintusov
 * @version 1.2
 */
public class EntityBuilder {
	private EntityBuilder() {}

	/**
	 * Creates user
	 * @param firstName - user's first name
	 * @param lastName user's last name
	 * @param login - user's login
	 * @param password - user's password
	 * @param courses - set of user's courses
	 * @param marks - set of user's marks
	 * @param marks - set of user's access levels
	 * @return entity of <strong>User</strong>
	 */
	public static User buildUser (String firstName, String lastName, String login, String password, Set<Course> courses, Set<Mark> marks, Set<AccessLevel> accessLevels) {
		User user = new User ();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLogin(login);
		user.setPassword(password);
		user.setCourses(courses);
		user.setMarks(marks);
		user.setAccessLevels(accessLevels);
		return user;
	}

	/**
	 * Creates course
	 * @param courseName - course's name
	 * @param hours - hours of study
	 * @param courseStatus - course's status
	 * @param startDate - course's start date
	 * @param updateDate - course's update date
	 * @param users - set of course's users
	 * @return entity of <strong>Course</strong>
	 */
	public static Course buildCourse (String courseName, Integer hours, CourseStatusType courseStatus, Calendar startDate, Calendar updateDate, Set<User> users) {
		Course course = new Course ();
		course.setCourseName(courseName);
		course.setHours(hours);
		course.setCourseStatus(courseStatus);
		course.setStartDate(startDate);
		course.setUpdateDate(updateDate);
		course.setUser(users);
		return course;
	}

	/**
	 * Creates mark
	 * @param marks - user's mark
	 * @param user - user that get or set mark
	 * @param course - mark got on course
	 * @param date - date of mark
	 * @return entity of <strong>Mark</strong>
	 */
	public static Mark buildMark (Integer marks, User user, Course course, Calendar date) {
		Mark mark = new Mark ();
		mark.setMark(marks);
		mark.setUser(user);
		mark.setCourse(course);
		mark.setDate(date);
		return mark;
	}

	/**
	 * Creates archive
	 * @param courses - set of courses put to archive
	 * @return entity of <strong>Archive</strong>
	 */
	public static Archive buildArchive (Set <Course> courses) {
		Archive archive = new Archive();
		archive.setCourses(courses);
		return archive;
	}

	/**
	 * Creates access level
	 * @param accessLevelType - type of access level
	 * @param users - users who have access level
	 * @return entity of <strong>Archive</strong>
	 */
	public static AccessLevel buildAccessLevel (AccessLevelType accessLevelType, Set<User> users) {
		AccessLevel accessLevel = new AccessLevel();
		accessLevel.setAccessLevelType(accessLevelType);
		accessLevel.setUsers(users);
		return accessLevel;
	}
}
