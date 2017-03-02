package by.pvt.pintusov.courses.pojos;

import by.pvt.pintusov.courses.enums.CourseStatusType;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes the entity <strong>Course</strong>
 * @author dpintusov
 * @version 1.2
 */

@Entity
public class Course extends AbstractEntity {
	private static final long serialVersionUID = 3L;

	public Course() {
		super();
	}

	@Column (nullable = false)
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	private String courseName;

	@Column (nullable = false)
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	private Integer hours;

	@Enumerated(EnumType.STRING)
	@Column (columnDefinition = "enum('OPEN', 'ENDED', 'ARCHIVE')")
	public CourseStatusType getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(CourseStatusType courseStatus) {
		this.courseStatus = courseStatus;
	}
	private CourseStatusType courseStatus;

	@Column (insertable = true, updatable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	private Calendar startDate;

	@Column (insertable = false, updatable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	public Calendar getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}
	private Calendar updateDate;

	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<User> getUser() {
		return users;
	}
	public void setUser(Set<User> users) {
		this.users = users;
	}
	private Set<User> users;
	public void addUser(User user){
		if(users == null){
			users = new HashSet<>();
		}
		users.add(user);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof User)) return false;
		if (!super.equals(o)) return false;

		Course course = (Course) o;

		if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
		if (hours != null ? !hours.equals(course.hours) : course.hours != null) return false;
		if (courseStatus != course.courseStatus) return false;
		if (startDate != null ? !startDate.equals(course.startDate) : course.startDate != null) return false;
		return updateDate != null ? updateDate.equals(course.updateDate) : course.updateDate == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
		result = 31 * result + (hours != null ? hours.hashCode() : 0);
		result = 31 * result + (courseStatus != null ? courseStatus.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseName='" + courseName + '\'' +
				", hours=" + hours +
				", courseStatus=" + courseStatus +
				", startDate=" + startDate +
				", updateDate=" + updateDate +
				'}';
	}
}
