package by.pvt.pintusov.courses.controllers;

import by.pvt.pintusov.courses.exceptions.ServiceException;
import by.pvt.pintusov.courses.pojos.User;
import by.pvt.pintusov.courses.services.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User REST controller
 * Project: courses
 * Created by: dpintusov
 * Date: 21.02.17.
 */
@RestController
public class UserRestController {
private static Logger logger = Logger.getLogger(UserRestController.class);

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getUsers () {
		try {
			List<User> users = userService.getAll();
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (ServiceException e) {
			logger.error(e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUser (@PathVariable("id") Integer id) {
		User user = null;
		try {
			user = userService.getById(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (ServiceException e) {
			logger.error(e);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Integer> saveUser (User user) {
		Integer userId = null;
		try {
			userId = (Integer) userService.saveOrUpdate(user);
		}catch (ServiceException e) {
			logger.error(e);
		}
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Integer> updateUser (@PathVariable ("id") Integer id, @RequestBody User newUser) {
		Integer userId = null;
		User user = null;
		try {
			user = userService.getById(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setLogin(newUser.getLogin());
			user.setPassword(newUser.getPassword());
			user.setCourses(newUser.getCourses());
			user.setMarks(newUser.getMarks());
			user.setAccessLevels(newUser.getAccessLevels());
			userId = (Integer) userService.saveOrUpdate(user);
		}catch (ServiceException e) {
			logger.error(e);
		}
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser (@PathVariable("id") Integer id) {
		try {
			userService.delete(id);
		}catch (ServiceException e) {
			logger.error(e);
		}
	}
}
