package com.sevensemesterproject.infoJam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sevensemesterproject.infoJam.request.FeedbackCreationRequest;
import com.sevensemesterproject.infoJam.response.FeedbackResponse;
import com.sevensemesterproject.infoJam.service.FeedbackService;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	@Autowired
	FeedbackService feedbackService;
	
	@PostMapping("/sendFeedback")
    public ResponseEntity<Object> sendFeedback(@RequestBody FeedbackCreationRequest request,
		@RequestHeader Long loginId,  @RequestHeader String token){
		feedbackService.sendFeedback(request, loginId, token);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@RequestMapping(value ="getAllFeedbacks", method = RequestMethod.GET)
	  public ResponseEntity<Object> getAllFeedbacks(){
		  List<FeedbackResponse> listedFeedbackResponse = feedbackService.getAllFeedbacks();
		  return new ResponseEntity<Object>(listedFeedbackResponse, HttpStatus.OK);
	  }
}
