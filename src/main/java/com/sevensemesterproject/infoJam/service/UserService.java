package com.sevensemesterproject.infoJam.service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.sevensemesterproject.infoJam.cloud.CloudinaryResource;
import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.exception.ValidationException;
import com.sevensemesterproject.infoJam.model.Login;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.LoginRepository;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.EmailEditRequest;
import com.sevensemesterproject.infoJam.request.ProfilePicEditRequest;
import com.sevensemesterproject.infoJam.request.UserEditRequest;
import com.sevensemesterproject.infoJam.request.UserRegisterRequest;
import com.sevensemesterproject.infoJam.response.UserResponse;
import com.sevensemesterproject.infoJam.util.FileUtil;
import com.sevensemesterproject.infoJam.util.LoginStatus;
import com.sevensemesterproject.infoJam.util.PasswordEncodeDecode;
import com.sevensemesterproject.infoJam.util.Status;
import com.sevensemesterproject.infoJam.util.TrackStatus;
import com.sevensemesterproject.infoJam.util.UserRole;
import com.sevensemesterproject.infoJam.util.Validator;

@Service
public class UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private TrackUserRepository trackUserRepository;
	@Value("infojam/uploaded-files")
	private String environment;
	@Autowired
	private CommonService commonService;
	
	@Transactional
	public User registerUser(UserRegisterRequest request) {
		LOG.debug("---->Registering User.");
		
		User checkEmail = userRepository.findByEmail(request.getEmail());
		if(checkEmail != null) {
			throw new ValidationException("User with this email is already exist.");
		}
		boolean matcher = false;
		if(Validator.getEmailValidator(request.getEmail())==matcher) {
			throw new ValidationException("Please enter valid email id");
		}
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new ValidationException(
					"New password and confirm new password did not match.");
		}
		User user = new User();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setUsername(commonService.createUsername(request.getFullName()));
		user.setStatus(Status.ACTIVE);
		user.setCreatedDate(new Date());
		user.setUserRole(UserRole.USER);
		user = userRepository.save(user);
		
		LOG.debug("---->creating login for user "+ user.getId());
		createLoginForRegisteredUser(user, request.getPassword(), request.getConfirmPassword());
		createTrackForRegisteredUser(user);
		LOG.debug("---->user "+ user.getId()+ " registered.");
		return user;	
	}
	

	  @Transactional 
	  public void createLoginForRegisteredUser(User user, String password1, String password2) { 
		  
	  Login login = new Login();
	  PasswordEncodeDecode encodedecode = new PasswordEncodeDecode();
	  
	  if(!password1.equals(password2)) {
		  throw new NotFoundException("Password didn't match");
	  } else 
		  
	  login.setPassword(encodedecode.doEncode(password1));
	  login.setUserId(user.getId());
	  login.setTokenExpDateTime(new Date());
	  login.setCreatedDate(new Date());
	  login.setLoginStatus(LoginStatus.LOGGEDOUT); 
	  loginRepository.save(login);
	  
	  LOG.debug("---->Login created for userID "+
	  user.getId()+ " with loginId "+login.getId());
	  
    }
	  public void createTrackForRegisteredUser(User user) {
		  TrackUser track = new TrackUser();
		  
		  track.setLocation("N/A");
		  track.setEmail(user.getEmail());
		  track.setUsername(user.getUsername());
		  track.setLoginId(user.getId());
		  track.setTrackStatus(TrackStatus.CREATED);
		  trackUserRepository.save(track);
	  }
	
	
	@Transactional 
	public UserResponse getUser(Long id) {
		
		User user = userRepository.getOne(id);
		DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		UserResponse userResponse = new UserResponse();
		
		LOG.debug("---->Getting user ");
		userResponse.setCreatedDate(df.format(user.getCreatedDate()));
		userResponse.setFullName(user.getFullName());
		userResponse.setEmail(user.getEmail());
		userResponse.setUsername(user.getUsername());
		userResponse.setUserRole(user.getUserRole());
		userResponse.setAddress(user.getAddress());
		if(user.getDob() != null)
			userResponse.setDob(fmt.format(user.getDob()));
		else 
			userResponse.setDob("");
		userResponse.setProfilePicture(new CloudinaryResource().getFileUrl(user.getProfilePicture()));
		userResponse.setPhone(user.getPhone());
		userResponse.setGender(user.getGender());
		userResponse.setStatus(user.getStatus());
		
		return userResponse;
	 }

	 @Transactional
	 public User editUser(Long id, String token, UserEditRequest userEditRequest) {
	 
		 commonService.isValidToken(id, token); 
 		
		 User user = userRepository.getOne(id);
		 if(user == null) {
			 throw new NotFoundException("User not found related to this id."); 
		  	}
		 
		 user.setFullName(userEditRequest.getFullName());
		 boolean matcher = false;
		 if(Validator.getPhoneValidator(userEditRequest.getPhone())==matcher) {
			 throw new ValidationException("Phone Number not Valid.");
		 }
		 user.setPhone(userEditRequest.getPhone());
		 user.setAddress(userEditRequest.getAddress());
		 user.setGender(userEditRequest.getGender());
		 user.setDob(userEditRequest.getDob());
		 user.setModifiedBy(id);
		 user.setModifiedDate(new Date());
		 user.setProfilePicture(new CloudinaryResource().getFileUrl(user.getProfilePicture()));
	     return user = userRepository.save(user);
	  }
    
	 @Transactional
	 public void deleteUser(Long id) {
    	
		User userToDelete = userRepository.getOne(id);
		if(userToDelete == null) {
			throw new NotFoundException("User not found...");
		}
		LOG.debug("---->Deleting user...");
		userToDelete.setStatus(Status.DELETED);
	}
    
    @Transactional
	public List<UserResponse> getAllUsers() {
		
		List<UserResponse> listedusers = new ArrayList<>();
		DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<User> users = userRepository.findByUserRoleNotAndStatusNotOrderByFullNameAsc(UserRole.ADMIN, Status.DELETED);
		
		LOG.debug("---->Listing users...");
		for (User u : users) {
			UserResponse userResponse = new UserResponse();
			userResponse.setId(u.getId());
			userResponse.setFullName(u.getFullName());
			if(u.getDob() != null)
				userResponse.setDob(fmt.format(u.getDob()));
			else 
				userResponse.setDob("Null");
			userResponse.setEmail(u.getEmail());
			if( u.getPhone() != null) {
				userResponse.setPhone(u.getPhone());
			}else
			userResponse.setPhone("N/A");
			if(u.getAddress() != null) {
				userResponse.setAddress(u.getAddress());
			}else 
				userResponse.setAddress("N/A");
			userResponse.setStatus(u.getStatus());
			userResponse.setUserRole(u.getUserRole());
			userResponse.setCreatedDate(df.format(u.getCreatedDate()));
			userResponse.setProfilePicture(new CloudinaryResource().getFileUrl(u.getProfilePicture()));
			userResponse.setUsername(u.getUsername());
			listedusers.add(userResponse);
		}
		return listedusers;
	}

	@Transactional
	public String changeProfilePic(Long loginId, Long id, String token,
			ProfilePicEditRequest profilePicEditRequest) {
    	
    		commonService.isValidToken(loginId, token);
    		
    		User user = userRepository.getOne(id);
    		if(user == null) {
    			  throw new NotFoundException("User not found related to this id."); 
    		}
    			  
    		 if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
    				try {
    				new CloudinaryResource().deleteFile(user.getProfilePicture());
    				} catch (IOException e1) {
    						e1.printStackTrace();
    					}
    				}
    		    String publicId = "";
    		    String  finalUrl = "";
    			File file = null;
    			if (profilePicEditRequest.getProfilePicture() != null && !profilePicEditRequest.getProfilePicture().isEmpty()) {
				String fileDirectory = FileUtil.getFileLocation(environment);
				file = FileUtil.write("test", profilePicEditRequest.getProfilePicture());
				
				try {
					publicId = new CloudinaryResource().uploadFile(file, fileDirectory);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (file != null)
					user.setProfilePicture(publicId);
				finalUrl = new CloudinaryResource().getFileUrl(publicId);
    			}
				if (file != null) {
					file.deleteOnExit();
				}
				userRepository.save(user);
				return finalUrl;
    			
		}

		@Transactional
		public String changeEmail(Long id, String token, EmailEditRequest request) {
		
		commonService.isValidToken(id, token);
		
		String emailUpdated = "";
		User user = userRepository.getOne(id);
		if (!request.getOldEmail().equals(user.getEmail())) {
			throw new ValidationException("Invalid old email!");
		}
		if (!request.getNewEmail().equals(request.getConfirmNewEmail())) {
			throw new ValidationException(
					"New Email and confirm new email did not match.");
		}
		user.setEmail(request.getNewEmail());
		emailUpdated = request.getNewEmail();
		userRepository.save(user);
		return emailUpdated;
	}	
}
