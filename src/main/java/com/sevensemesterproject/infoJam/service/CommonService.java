package com.sevensemesterproject.infoJam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sevensemesterproject.infoJam.exception.ValidationException;
import com.sevensemesterproject.infoJam.model.Login;
import com.sevensemesterproject.infoJam.repository.LoginRepository;
import com.sevensemesterproject.infoJam.repository.ReportRepository;

@Service
public class CommonService {
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	ReportRepository reportRepository;

	public String createUsername(String fullName) {
		
		String firstName;
		String lastName;
		int space;
		space = fullName.indexOf(' ');
		firstName = fullName.substring(0, space);
        lastName = fullName.substring(space+1);
        return firstName+"."+lastName; //need to make more relevant later by checking into DB
		
	 }
	
	 public void isValidToken(Long loginId, String token) {
		Login login = loginRepository.findByIdAndToken(loginId, token);
		if (login == null) {
			throw new ValidationException("LoginId and token mis-matched.");
		  }
	 }
}