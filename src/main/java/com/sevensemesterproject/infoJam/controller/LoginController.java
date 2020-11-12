package com.sevensemesterproject.infoJam.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sevensemesterproject.infoJam.request.LoginRequest;
import com.sevensemesterproject.infoJam.request.PasswordEditRequest;
import com.sevensemesterproject.infoJam.response.LoginResponse;
import com.sevensemesterproject.infoJam.service.LoginService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/acc")
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	@ApiOperation(value = "User Login API", notes = "Various informations will be provided when login is success.",
	response = LoginResponse.class)
	public ResponseEntity<Object> login(@RequestBody LoginRequest request){
		LOG.debug("---->Request accepted for Loging In.");
		LoginResponse response = loginService.login(request);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("login", response);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);	
	}
	
	@PutMapping("/{loginId}/logout")
	@ApiOperation(value = "User LogOut API", notes = "Status of login will be loggedOut")
	public ResponseEntity<Object> logout(@PathVariable Long loginId, @RequestHeader String token){
		LOG.debug("----> Request to logout accepted.");
		loginService.logout(loginId, token);
		return new ResponseEntity<Object>("User logged out!",HttpStatus.OK);	
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
	public ResponseEntity<Object> changePassword(@RequestHeader Long loginId, @RequestHeader String token,
		@RequestBody PasswordEditRequest passwordEditRequest) {
		LOG.debug("Request accepted to change password.");
		loginService.changePassword(loginId, token, passwordEditRequest);
		return new ResponseEntity<Object>("Password sucessfully changed!",HttpStatus.OK);
	}
}
