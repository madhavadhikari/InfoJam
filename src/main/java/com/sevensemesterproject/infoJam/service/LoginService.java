package com.sevensemesterproject.infoJam.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sevensemesterproject.infoJam.cloud.CloudinaryResource;
import com.sevensemesterproject.infoJam.controller.UserController;
import com.sevensemesterproject.infoJam.exception.LoginFailedException;
import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.exception.ValidationException;
import com.sevensemesterproject.infoJam.model.Login;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.LoginRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.LoginRequest;
import com.sevensemesterproject.infoJam.request.PasswordEditRequest;
import com.sevensemesterproject.infoJam.response.LoginResponse;
import com.sevensemesterproject.infoJam.util.LoginStatus;
import com.sevensemesterproject.infoJam.util.PasswordEncodeDecode;
import com.sevensemesterproject.infoJam.util.RandomNumber;

@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private LoginRepository loginRepository;
	@Autowired
	private CommonService commonService;
	
	public LoginResponse login(LoginRequest request) {
		LOG.debug("---->checking email");
		User user = userRepository.findByEmail(request.getEmail());
		if(user == null ||  user.equals(null))
			throw new NotFoundException("Email not found.");
		LOG.debug("---->Email found. user's id is "+ user.getId());
		
		LoginResponse loginResponse = new LoginResponse();
		
		Login login = loginRepository.findByUserId(user.getId());
		LOG.debug("---->Login Null?:"+ login);
		PasswordEncodeDecode ped = new PasswordEncodeDecode();
		if(login != null) {
			if(request.getPassword().equals(ped.doDecode(login.getPassword()))) {
				LOG.debug("---->Login Successfull!");
				login.setLoginStatus(LoginStatus.LOGGEDIN);
				login.setToken(RandomNumber.randomString(30));
				login.setModifiedDate(new Date());
				login.setTokenExpDateTime(new Date());
				
				loginRepository.save(login);
				LOG.debug("---->Login updated");
				
				//setting response 
				loginResponse.setUserId(user.getId());
				loginResponse.setUsername(user.getUsername());
				loginResponse.setCreatedDate(user.getCreatedDate());
				loginResponse.setEmail(user.getEmail());
				loginResponse.setFullName(user.getFullName());
				loginResponse.setLoginId(login.getId());
				loginResponse.setToken(login.getToken());
				loginResponse.setLoginStatus(LoginStatus.LOGGEDIN);
				loginResponse.setProfilePicture(new CloudinaryResource().getFileUrl(user.getProfilePicture()));
			
			}else {
				LOG.debug("---->Login Failed!");
				throw new LoginFailedException("Login failed!");
			}
		}
		return loginResponse;
	}

	public void logout(Long loginId, String token) {
		
		LOG.debug("---->Loggin out.....");
		Login login = loginRepository.findByIdAndToken(loginId, token);
		if(login == null )
			throw new NotFoundException("Login Id and token didn't match.");
		
		login.setLoginStatus(LoginStatus.LOGGEDOUT);
		login.setToken("");
		login.setModifiedDate(new Date());
		login.setTokenExpDateTime(new Date());
		loginRepository.save(login);
		LOG.debug("Logout successfull.");
	}

	public void changePassword(Long loginId, String token, PasswordEditRequest request) {
		
		PasswordEncodeDecode ped = new PasswordEncodeDecode();
		commonService.isValidToken(loginId, token);
		
		Login login = loginRepository.getOne(loginId);
		if (!request.getOldPassword().equals(ped.doDecode(login.getPassword()))) {
			throw new ValidationException("Invalid old password!");
		}
		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
			throw new ValidationException(
					"New password and confirm new password did not match.");
		}
		login.setPassword(ped.doEncode(request.getNewPassword()));
		loginRepository.save(login);
	}
}



