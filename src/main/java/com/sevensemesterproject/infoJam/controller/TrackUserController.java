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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.request.TrackUserRequest;
import com.sevensemesterproject.infoJam.response.TrackUserResponse;
import com.sevensemesterproject.infoJam.service.TrackUserService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/trackUser")
public class TrackUserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private TrackUserService trackUserService;
	
	@RequestMapping(value = "editTrack", method = RequestMethod.PUT)
	public ResponseEntity<Object> editTrack(@RequestBody TrackUserRequest request, @RequestHeader Long loginId,  @RequestHeader String token){
		LOG.debug("<----- Requesting for report register----->");
		TrackUser track = trackUserService.editTrack(request, loginId, token);
		return new ResponseEntity<Object>(track, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a trackUser API", notes = "on successful request the response is as:",
	response = TrackUserResponse.class)
	public ResponseEntity<Object> getTrackUser(@PathVariable Long id, @RequestHeader Long loginId,@RequestHeader String token){
		 TrackUserResponse userTrackResponse = trackUserService.getTrackUser(id, loginId, token);
		 Map<Object, Object> responseMap = new HashMap<Object, Object>();
		 responseMap.put("trackUser", userTrackResponse);
		 return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	
	 @RequestMapping(value ="getAllTrackUsers", method = RequestMethod.GET)
	 @ApiOperation(value = "Get all trackUsers API", notes = "on successful request the response is as:",
	 response = TrackUserResponse.class)
	 public ResponseEntity<Object> getAllTrackUsers( @RequestHeader Long loginId, @RequestHeader String token){
		  List<TrackUserResponse> listedTrackUser = trackUserService.getAllTrackUsers(loginId, token);
		  Map<Object, Object> responseMap = new HashMap<Object, Object>();
		  responseMap.put("data", listedTrackUser);
		  return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	 
}
