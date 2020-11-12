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
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.request.ReportEditRequest;
import com.sevensemesterproject.infoJam.request.ReportRegisterRequest;
import com.sevensemesterproject.infoJam.response.ReportResponse;
import com.sevensemesterproject.infoJam.service.ReportService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/api/report")
public class ReportController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	ReportService reportService;
	
	@PostMapping("/reportRegister")
	public ResponseEntity<Object> registerReport(@RequestBody ReportRegisterRequest request, 
		@RequestHeader Long loginId, @RequestHeader String token){
		LOG.debug("<----- Requesting for report register----->");
		Report report = reportService.setReport(request, loginId, token);
		return new ResponseEntity<Object>("Reported successfully, your report id is " + report.getId(), HttpStatus.OK);	
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a report API", notes = "on successful request the response is as:",
	response = ReportResponse.class)
	public ResponseEntity<Object> getReport(@PathVariable Long id, @RequestHeader Long loginId,  @RequestHeader String token){
		ReportResponse reportResponse = reportService.getReport(id, loginId, token);
		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		responseMap.put("report", reportResponse);
		return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	
	 @RequestMapping(value ="getAllReports", method = RequestMethod.GET)
	 @ApiOperation(value = "Get a report API", notes = "on successful request the response is as:",
	 response = ReportResponse.class)
	 public ResponseEntity<Object> getAllreports(){
		  List<ReportResponse> listedReports = reportService.getAllReports();
		  Map<Object, Object> responseMap = new HashMap<Object, Object>();
		  responseMap.put("data", listedReports);
		  return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	 @RequestMapping(value ="getAllReportsForMobile", method = RequestMethod.GET)
	 @ApiOperation(value = "Get a report API", notes = "on successful request the response is as:",
	 response = ReportResponse.class)
	 public ResponseEntity<Object> getAllreportsForMobile(){
		  List<ReportResponse> listedReports = reportService.getAllReportsForMobile();
		  Map<Object, Object> responseMap = new HashMap<Object, Object>();
		  responseMap.put("data", listedReports);
		  return new ResponseEntity<Object>(responseMap, HttpStatus.OK);
	 }
	 
	  @RequestMapping(value ="editReport", method = RequestMethod.PUT) 
	  public ResponseEntity<Object> editReport(@RequestBody ReportEditRequest reportEditRequest, @RequestHeader Long loginId,
		  @RequestHeader String token) {
		  LOG.debug("Request accepted to edit report.");
		  reportService.editReport(reportEditRequest, loginId, token); 
		  return new ResponseEntity<Object>(HttpStatus.OK); 
	  
	 }
	  
	  @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE)
	  public ResponseEntity<Object> deleteReport(@PathVariable Long id) {
		  reportService.deleteReport(id);
		  return new ResponseEntity<Object>(HttpStatus.OK);
	 }
	  
	/*
	 * @RequestMapping(value ="getLocationReport", method = RequestMethod.GET)
	 * 
	 * @ApiOperation(value = "Get a report API based on location", notes =
	 * "on successful request the response is as:", response = ReportResponse.class)
	 * public ResponseEntity<Object> getLocationReport(@RequestHeader String
	 * location, @RequestHeader Long loginId,
	 * 
	 * @RequestHeader String token){ ReportResponse reportResponse =
	 * reportService.getLocationReport(location, loginId, token); Map<Object,
	 * Object> responseMap = new HashMap<Object, Object>();
	 * responseMap.put("report", reportResponse); return new
	 * ResponseEntity<Object>(responseMap, HttpStatus.OK); }
	 */
}
