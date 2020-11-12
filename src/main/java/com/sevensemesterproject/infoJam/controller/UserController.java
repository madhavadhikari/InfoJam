package com.sevensemesterproject.infoJam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.request.EmailEditRequest;
import com.sevensemesterproject.infoJam.request.ProfilePicEditRequest;
import com.sevensemesterproject.infoJam.request.UserEditRequest;
import com.sevensemesterproject.infoJam.request.UserRegisterRequest;
import com.sevensemesterproject.infoJam.response.UserResponse;
import com.sevensemesterproject.infoJam.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller 
@RequestMapping("/api/user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	@ApiOperation(value = "User Creation API", notes = "Various informations will be provided for user as:.",
	response = UserResponse.class)
	public ResponseEntity<Object> registerUser(@RequestBody UserRegisterRequest request){
		LOG.debug("---->Requesting for register user.");
		User user = userService.registerUser(request);
		return new ResponseEntity<Object>("User Created "+ user.getId(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a user API", notes = "on successful request the response is as:",
	response = UserResponse.class)
	public ResponseEntity<Object> getUser(@PathVariable Long id){
	   	UserResponse userResponse = userService.getUser(id);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("user", userResponse);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	
	 @RequestMapping(value = "editUser", method = RequestMethod.PUT) 
	 @ApiOperation(value = "User Edit API", notes = "Various informations will be provided.",
	 response = UserResponse.class)
	 public ResponseEntity<Object> editUser(@RequestHeader Long userId, @RequestHeader String token, 
	     @RequestBody UserEditRequest userEditRequest) {
		 LOG.debug("Request accepted to edit user.");
		 User userResponse = userService.editUser(userId, token, userEditRequest);
		 Map<Object, Object> responseMap = new HashMap<Object, Object>();
			responseMap.put("user", userResponse);
		 return new ResponseEntity<Object>(responseMap, HttpStatus.OK);  
	 }
	
	 @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
	 public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
		 return new ResponseEntity<Object>(HttpStatus.OK);
	 }
	  
	  @RequestMapping(value ="getAllUsers", method = RequestMethod.GET)
	  @ApiOperation(value = "Get all users API", notes = "on successful request the response is as:",
	  response = UserResponse.class)
	  public ResponseEntity<Object> getAllUsers(){
		  List<UserResponse> listedusers = userService.getAllUsers();
		  Map<Object, Object> responseMap = new HashMap<Object, Object>();
		  responseMap.put("data", listedusers);
		  return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	  }
	  
	  @RequestMapping(value = "profile-pic-change", method = RequestMethod.PUT)
	  public ResponseEntity<Object> changeProfilePicture(@RequestHeader Long id, 
		  @RequestHeader Long loginId, @RequestHeader String token,
		  @RequestBody ProfilePicEditRequest profilePicEditRequest) {
		  LOG.debug("Request accepted to change profilePicture.");
		  String url = userService.changeProfilePic(loginId, id,  token, profilePicEditRequest);
		  Map<Object, Object> responseMap = new HashMap<Object, Object>();
		  responseMap.put("url", url);
		  return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	  }
	  
	  @RequestMapping(value = "/changeEmail", method = RequestMethod.PUT)
	  public ResponseEntity<Object> changeEmail(@RequestHeader Long id, @RequestHeader String token,
		   @RequestBody EmailEditRequest emailEditRequest) {
		   LOG.debug("Request accepted to change Email.");
		  String emailUpdated = userService.changeEmail(id, token, emailEditRequest);
		   Map<Object, Object> responseMap = new HashMap<Object, Object>();
		   responseMap.put("email", emailUpdated);
		   return new ResponseEntity<Object>(responseMap ,HttpStatus.OK);
	  }
}
