package by.pvt.pintusov.courses.pojos;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Describes the entity <strong>Mark</strong>
 * @author dpintusov
 * @version 1.2
 */

@Data
@Entity
public class Mark extends AbstractEntity {
	private static final long serialVersionUID = 4L;

	public Mark() { super(); }

	@Column (nullable = false, updatable = false)
	private Integer mark;

	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private User user;

	@ManyToOne
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	private Course course;

	@Column
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar date;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof Mark)) return false;
		Mark mark = (Mark) o;
		if (this.mark != null ? !this.mark.equals(mark.mark) : mark.mark != null) return false;
		if (user != null ? !user.equals(mark.user) : mark.user != null) return false;
		if (course != null ? !course.equals(mark.course) : mark.course != null) return false;
		return date != null ? date.equals(mark.date) : mark.date == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (course != null ? course.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Mark{" +
				"mark=" + mark +
				", user=" + user +
				", course=" + course +
				", date=" + date +
				'}';
	}
}
