package by.pvt.pintusov.courses.pojos;

import by.pvt.pintusov.courses.enums.AccessLevelType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes the entity <strong>AccessLevel</strong>
 * @author dpintusov
 * @version 1.2
 */

@Entity
public class AccessLevel extends AbstractEntity {
	private static final long serialVersionUID = 5L;

	public AccessLevel() {super();}

	@Enumerated (EnumType.STRING)
	@Column(columnDefinition = "enum('TEACHER', 'STUDENT', 'ADMIN')")
	public AccessLevelType getAccessLevelType() {
		return accessLevelType;
	}
	public void setAccessLevelType(AccessLevelType accessLevelType) {
		this.accessLevelType = accessLevelType;
	}
	private AccessLevelType accessLevelType;

	@ManyToMany
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	private Set <User> users;
	public void addUser(User user){
		if(users == null){
			users = new HashSet<>();
		}
		users.add(user);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof AccessLevel)) return false;
		if (!super.equals(o)) return false;
		AccessLevel that = (AccessLevel) o;
		return accessLevelType == that.accessLevelType;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (accessLevelType != null ? accessLevelType.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AccessLevel{" +
				"accessLevelType=" + accessLevelType +
				'}';
	}
}
