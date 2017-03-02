package by.pvt.pintusov.courses.pojos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes the entity <strong>Archive</strong>
 * @author dpintusov
 * @version 1.2
 */

@Entity
public class Archive extends AbstractEntity {
	private static final long serialVersionUID = 6L;

	public Archive() {super(); }

	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Set<Course> getCourses() {
		return coursesInArchive;
	}
	public void setCourses(Set<Course> coursesInArchive) {
		this.coursesInArchive = coursesInArchive;
	}
	private Set<Course> coursesInArchive;

	public void addCourseToArchive(Course course){
		if(coursesInArchive == null){
			coursesInArchive = new HashSet<>();
		}
		coursesInArchive.add(course);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null ||  !(o instanceof User)) return false;
		if (!super.equals(o)) return false;
		Archive archive = (Archive) o;
		return coursesInArchive != null ? coursesInArchive.equals(archive.coursesInArchive) : archive.coursesInArchive == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (coursesInArchive != null ? coursesInArchive.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Archive{" +
				"coursesInArchive=" + coursesInArchive +
				'}';
	}
}
