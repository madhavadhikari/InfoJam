package com.sevensemesterproject.infoJam.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.model.Feedback;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.FeedbackRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.FeedbackCreationRequest;
import com.sevensemesterproject.infoJam.response.FeedbackResponse;
import com.sevensemesterproject.infoJam.util.Status;

@Service
public class FeedbackService {
	
	@Autowired
	CommonService commonService;
	@Autowired 
	FeedbackRepository feedbackRepository;
	@Autowired 
	UserRepository userRepository;
	
	@Transactional
	public void sendFeedback(FeedbackCreationRequest request, Long loginId, String token) {
		
			commonService.isValidToken(loginId, token);
			
			Feedback fb = new Feedback();
			fb.setCreatedBy(loginId);
			fb.setLoginId(loginId);
			fb.setCreatedDate(new Date());
			fb.setStatus(Status.ACTIVE);
			fb.setTitle(request.getTitle());
			fb.setDescription(request.getDescription());
			feedbackRepository.save(fb);
		}	
	
	@Transactional
	public List<FeedbackResponse> getAllFeedbacks() {

		List<FeedbackResponse> feedbackResponse = new ArrayList<>();
		List<Feedback> feedbacks = feedbackRepository.findByOrderByCreatedDateDesc();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		feedbacks.forEach(f ->{
			FeedbackResponse fbr = new FeedbackResponse();
			User user = userRepository.getOne(f.getCreatedBy());
			fbr.setSender(user.getFullName());
			fbr.setTitle(f.getTitle());
			fbr.setDescription(f.getDescription());
			fbr.setCreatedDate(df.format(f.getCreatedDate()));
			feedbackResponse.add(fbr);
		});
		return feedbackResponse;	
	}
}
