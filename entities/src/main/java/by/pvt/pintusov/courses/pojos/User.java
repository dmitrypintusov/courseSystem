package by.pvt.pintusov.courses.pojos;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes the entity <strong>User</strong>
 * @author dpintusov
 * @version 1.2
 */

@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class User extends AbstractEntity {
	private static final long serialVersionUID = 2L;

	public User() { super (); }

	@Column (nullable = false, length = 15)
	private String firstName;

	@Column (nullable = false, length = 30)
	private String lastName;

	@Column (unique = true, nullable = false, length = 25)
	private String login;

	@Column (nullable = false, length = 30)
	private String password;

	@ManyToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	private Set <Course> courses;
	public void addCourse(Course course){
		if(courses == null){
			courses = new HashSet<>();
		}
		courses.add(course);
	}

	@OneToMany (mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Mark> getMarks() {
		return marks;
	}
	public void setMarks(Set<Mark> marks) {
		this.marks = marks;
	}
	private Set<Mark> marks;
	public void addMark(Mark mark){
		if(marks == null){
			marks = new HashSet<>();
		}
		marks.add(mark);
	}

	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<AccessLevel> getAccessLevels() {
		return accessLevels;
	}
	public void setAccessLevels(Set<AccessLevel> accessLevels) {
		this.accessLevels = accessLevels;
	}
	private Set<AccessLevel> accessLevels;
	public void addAccessLevel(AccessLevel accessLevel){
		if(accessLevels == null){
			accessLevels = new HashSet<>();
		}
		accessLevels.add(accessLevel);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof User)) return false;
		if (!super.equals(o)) return false;
		User user = (User) o;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		if (login != null ? !login.equals(user.login) : user.login != null) return false;
		return password != null ? password.equals(user.password) : user.password == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
