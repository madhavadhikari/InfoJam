package com.sevensemesterproject.infoJam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sevensemesterproject.infoJam.service.CommonService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    
    @Autowired
    private CommonService commonService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> isValidToken(@RequestHeader Long loginId, @RequestHeader String token){
        LOG.debug("Request Accepted to validate the loginId with patricular token.");
        commonService.isValidToken(loginId, token);
        return new ResponseEntity<>("LoginId and token matched.", HttpStatus.OK);
	}
}
