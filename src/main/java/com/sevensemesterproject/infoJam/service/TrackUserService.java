package com.sevensemesterproject.infoJam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.request.TrackUserRequest;
import com.sevensemesterproject.infoJam.response.TrackUserResponse;
import com.sevensemesterproject.infoJam.util.TrackStatus;

@Service
public class TrackUserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	@Autowired
	TrackUserRepository trackUserRepository;
	@Autowired
	private CommonService commonService;
	@Transactional
	public TrackUser editTrack(TrackUserRequest request, Long loginId, String token) {
		commonService.isValidToken(loginId, token);
		TrackUser track = trackUserRepository.findByLoginId(loginId);
		if(track != null) {
			LOG.debug("-----------LOginID found-------");
			track.setLalitude(request.getLalitude());
			track.setLongitude(request.getLongitude());
			track.setModifiedDate(new Date());
			track.setModifiedBy(loginId);	
			track.setLocation(request.getLocation());
			track.setTrackStatus(TrackStatus.UPDATED);
		}
		trackUserRepository.save(track);
		return track;
		
	}
	@Transactional
	public TrackUserResponse getTrackUser(Long id, Long loginId,String token) {
		commonService.isValidToken(loginId, token);
		TrackUser trackUser = trackUserRepository.getOne(id);
		TrackUserResponse trackUserResponse = new TrackUserResponse();
		trackUserResponse.setId(trackUser.getId());
		trackUserResponse.setLoginId(trackUser.getLoginId());
		trackUserResponse.setEmail(trackUser.getEmail());
		trackUserResponse.setUsername(trackUser.getUsername());
		trackUserResponse.setStatus(trackUser.getStatus());
		trackUserResponse.setTrackStatus(trackUser.getTrackStatus());
		trackUserResponse.setLalitude(trackUser.getLalitude());
		trackUserResponse.setLongitude(trackUser.getLongitude());
		trackUserResponse.setLocation(trackUser.getLocation());
		return trackUserResponse;
	}
	@Transactional
	public  List<TrackUserResponse> getAllTrackUsers(Long loginId,String token) {
		commonService.isValidToken(loginId, token);
		List<TrackUserResponse> trackUserResponses = new ArrayList<>();
		List<TrackUser> trackUsers = trackUserRepository.findAll();
		
		for(TrackUser t: trackUsers) {
			TrackUserResponse trackUserResponse = new TrackUserResponse();
			trackUserResponse.setId(t.getId());
			trackUserResponse.setLoginId(t.getLoginId());
			trackUserResponse.setEmail(t.getEmail());
			trackUserResponse.setUsername(t.getUsername());
			trackUserResponse.setStatus(t.getStatus());
			trackUserResponse.setTrackStatus(t.getTrackStatus());
			trackUserResponse.setLalitude(t.getLalitude());
			trackUserResponse.setLongitude(t.getLongitude());
			trackUserResponse.setLocation(t.getLocation());
			trackUserResponses.add(trackUserResponse);
			
		}
		return trackUserResponses;
	}

}
