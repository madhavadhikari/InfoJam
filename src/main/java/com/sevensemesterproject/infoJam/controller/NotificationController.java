package com.sevensemesterproject.infoJam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sevensemesterproject.infoJam.response.NotificationResponse;
import com.sevensemesterproject.infoJam.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	
	@RequestMapping(value ="getAllNotifications", method = RequestMethod.GET)
	@ApiOperation(value = "getAllNotifications", notes = "on successful request the response is as:",
	response = NotificationResponse.class)
	public ResponseEntity<Object> getAllNotifications( @RequestHeader Long loginId, @RequestHeader String token){
		List<NotificationResponse> listedNotification = notificationService.getAllNotifications(loginId, token);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("data", listedNotification);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	}
	
	@GetMapping("/getNotification")
	public ResponseEntity<Object> getNotification(@RequestHeader Long loginId,  @RequestHeader String token){
		NotificationResponse notificationResponse = notificationService.getNotification(loginId, token);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("report", notificationResponse);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
}
