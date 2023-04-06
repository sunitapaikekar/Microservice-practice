package com.appsdeveloperblog.app.ws.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // localhost:8080/users
public class UserController {

	Map<String, UserRest> userMap;

	/*
	 * to make request param optional 1. use defaultValue attribute or/and 2. make
	 * required=false --cannot be used with premitive data types
	 */
	@GetMapping
	public String getUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "20") Integer limit,
			@RequestParam(value = "sort", required = false) String sort) {

		return "Get User was called with page: " + page + "  limit :" + limit + "  sort:" + sort;
	}

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> getUserById(@PathVariable String userId) {
		UserRest returnValue = new UserRest();
		if (userMap.containsKey(userId)) {
			return new ResponseEntity<UserRest>(userMap.get(userId), HttpStatus.OK);

		} else {
			return new ResponseEntity<UserRest>(HttpStatus.NO_CONTENT);

		}
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();
		
		
		returnValue.setEmail(userDetails.getEmail());
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		String userId = UUID.randomUUID().toString();
		returnValue.setUserId(userId);
		if (userMap == null) {
			userMap = new HashMap<>();
			userMap.put(userId, returnValue);
		}
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
	}

	@PutMapping(path = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserRest> updateUser(@PathVariable String userId,
			@Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {

		UserRest storedUser = userMap.get(userId);
		
//		if(true) throw new UserServiceException("User not found with given userId: "+userId);
		storedUser.setFirstName(userDetails.getFirstName());
		storedUser.setLastName(userDetails.getLastName());

		userMap.put(userId, storedUser);
		return new ResponseEntity<UserRest>(storedUser, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		userMap.remove(userId);
		return new ResponseEntity<String>("User " + userId + "  is deleted ", HttpStatus.OK);
	}
}
