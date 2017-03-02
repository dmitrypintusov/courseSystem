package by.pvt.pintusov.courses.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * USER DATA ACCESS OBJECT class
 * used for validation inputs
 * Project name: courses
 * Created by dpintusov
 * Date: 22.01.2017.
 */

@Data
@NoArgsConstructor
public class UserDTO {
	@Size(min = 2, max = 20, message = "First name should be between 2 and 20 characters long")
	@Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "First name should be alphanumeric with no spaces")
	@NotNull(message = "First name cannot be empty")
	private String firstName;

	@Size(min = 3, max = 50, message = "Last name should be between 3 and 50 characters long")
	@Pattern(regexp = "^[a-zA-Zа-яА-Я0-9-/s]+$", message = "First name should be alphanumeric with no spaces")
	@NotNull(message = "Last name cannot be empty")
	private String lastName;

	@Size(min = 3, max = 20, message = "Login should be between 3 and 20 characters long")
	@NotNull(message = "Login cannot be empty")
	private String login;

	@Size(min = 6, max = 20, message = "Password should be between 3 and 20 characters long")
	@NotNull(message = "Password cannot be empty")
	private String password_1;

	@Size(min = 6, max = 20, message = "Password should be between 3 and 20 characters long")
	@NotNull(message = "Password cannot be empty")
	private String password_2;

	@Size(min = 3, max = 50, message = "Course name should be between 3 and 50 characters long")
	@Pattern(regexp = "^[a-zA-Zа-яА-Я0-9-/s]+$", message = "Course name should be alphanumeric with no spaces")
	@NotNull(message = "Course name cannot be empty")
	private String courseName;

	@Pattern(regexp = "TEACHER|STUDENT|ADMIN")
	@Size(min = 7, max = 7, message = "Incorrect value of access role")
	@NotNull(message = "Access role cannot be empty")
	private String accessRole;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof UserDTO)) return false;
		UserDTO userDTO = (UserDTO) o;
		if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
		if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
		if (login != null ? !login.equals(userDTO.login) : userDTO.login != null) return false;
		if (password_1 != null ? !password_1.equals(userDTO.password_1) : userDTO.password_1 != null) return false;
		if (password_2 != null ? !password_2.equals(userDTO.password_2) : userDTO.password_2 != null) return false;
		if (courseName != null ? !courseName.equals(userDTO.courseName) : userDTO.courseName != null) return false;
		return accessRole != null ? accessRole.equals(userDTO.accessRole) : userDTO.accessRole == null;
	}

	@Override
	public int hashCode() {
		int result = firstName != null ? firstName.hashCode() : 0;
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password_1 != null ? password_1.hashCode() : 0);
		result = 31 * result + (password_2 != null ? password_2.hashCode() : 0);
		result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
		result = 31 * result + (accessRole != null ? accessRole.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", login='" + login + '\'' +
				", password_1='" + password_1 + '\'' +
				", password_2='" + password_2 + '\'' +
				", courseName='" + courseName + '\'' +
				", accessRole='" + accessRole + '\'' +
				'}';
	}
}
