package com.sevensemesterproject.infoJam.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.model.Notification;
import com.sevensemesterproject.infoJam.model.NotificationReceivers;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.repository.NotificationReceiverRepository;
import com.sevensemesterproject.infoJam.repository.NotificationRepository;
import com.sevensemesterproject.infoJam.repository.ReportRepository;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.response.NotificationResponse;
import com.sevensemesterproject.infoJam.util.ReportStatus;
import com.sevensemesterproject.infoJam.util.SeenStatus;

@Service
public class NotificationService {

	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	NotificationReceiverRepository notificationReceiverRepository;
	@Autowired
	private TrackUserRepository trackUserRepository;
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	CommonService commonService;
	
	@Transactional
	public List<NotificationResponse> getAllNotifications(Long loginId, String token) {
		commonService.isValidToken(loginId, token);
		List<NotificationResponse> notificationResponses = new ArrayList<>();
		List<Notification> notifications = notificationRepository.findAll();
		
		for(Notification n: notifications) {
			NotificationResponse notificationResponse = new NotificationResponse();
			notificationResponse.setId(n.getId());
			notificationResponse.setCreatedBy(n.getCreatedBy());
			notificationResponse.setReportId(n.getReport().getId());
			notificationResponse.setCreatedDate(n.getCreatedDate());
			notificationResponses.add(notificationResponse);
		}
		return notificationResponses;
	}
	
	@Transactional
	public NotificationResponse getNotification(Long loginId, String token) {
		commonService.isValidToken(loginId, token);
		NotificationResponse notificationResponse = new NotificationResponse();
		TrackUser trackUser = trackUserRepository.findByLoginId(loginId);
		Report r = reportRepository.findByLocationAndReportStatusNot(trackUser.getLocation(), ReportStatus.EXPIRED);
		if(r == null) {
			throw new NotFoundException("Report notification not found for this location");
		}
		NotificationReceivers  receiver=  notificationReceiverRepository.findByReceiverIdAndReportIdAndSeenStatusNot(loginId, r.getId()
																	, SeenStatus.SEEN);
		if(receiver == null) {
			throw new NotFoundException("Receiver id not found");
		}
		notificationResponse.setLocation(receiver.getNotification().getReport().getLocation());
		notificationResponse.setJamStatus(receiver.getNotification().getReport().getJamStatus());
		notificationResponse.setCreatedBy(receiver.getNotification().getCreatedBy());
		notificationResponse.setReportId(receiver.getNotification().getId());
		return notificationResponse;
		
		/*List<NotificationResponse> notificationResponse = new ArrayList<>();
		TrackUser trackUser = trackUserRepository.findByLoginId(loginId);
		List<Report> reports = reportRepository.findByReportStatusNot(ReportStatus.EXPIRED);
		for(Report r : reports) {
			DistanceCalculation distanceCalculation= new DistanceCalculation(); 
			double d = distanceCalculation.distance(r.getLalitude(), r.getLongitude(), trackUser.getLalitude(), trackUser.getLongitude());
			if(d <= 300) {
				NotificationResponse response = new NotificationResponse();
				NotificationReceivers  receiver=  notificationReceiverRepository.findByReceiverIdAndReportIdAndSeenStatusNot(loginId, r.getId()
						, SeenStatus.SEEN);
				response.setLocation(receiver.getReport().getLocation());
				response.setJamStatus(receiver.getReport().getJamStatus());
				response.setCreatedBy(receiver.getNotification().getCreatedBy());
				response.setReportId(receiver.getReport().getId());
				notificationResponse.add(response);
			}
			else {
				throw new NotFoundException("Report notification not found for this location");
			}
		}
		return notificationResponse;*/
	}

}
