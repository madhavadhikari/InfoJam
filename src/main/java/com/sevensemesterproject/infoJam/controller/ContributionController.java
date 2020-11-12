package com.sevensemesterproject.infoJam.controller;

import java.text.ParseException;
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
import com.sevensemesterproject.infoJam.request.ContributionRequest;
import com.sevensemesterproject.infoJam.response.RCResponse;
import com.sevensemesterproject.infoJam.service.ContributionService;

@Controller
@RequestMapping("/api/contribution")
public class ContributionController {
	
	@Autowired
	ContributionService contributionService;
	
	@PostMapping("/sendContribution")
	public ResponseEntity<Object> sendContribution(@RequestBody ContributionRequest request,
		@RequestHeader Long loginId,  @RequestHeader String token){
		contributionService.sendContribution(request, loginId, token);
		return new ResponseEntity<Object>("Contributed", HttpStatus.OK);
	}
	
    @RequestMapping(value ="getMyReportAndContribution", method = RequestMethod.GET)
	public ResponseEntity<Object> getMyReportAndContribution( @RequestHeader Long loginId,
		@RequestHeader String token) throws ParseException{
		List<RCResponse> RCresponse = contributionService.getMyReportAndContribution(loginId, token);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("data", RCresponse);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	}
}
