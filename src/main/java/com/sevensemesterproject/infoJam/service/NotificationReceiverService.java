package com.sevensemesterproject.infoJam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.model.Notification;
import com.sevensemesterproject.infoJam.model.NotificationReceivers;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.repository.NotificationReceiverRepository;
import com.sevensemesterproject.infoJam.repository.NotificationRepository;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.util.DistanceCalculation;
import com.sevensemesterproject.infoJam.util.SeenStatus;
import com.sevensemesterproject.infoJam.util.TrackStatus;

@Service
public class NotificationReceiverService {

	@Autowired
	TrackUserRepository trackUserRepository;
	@Autowired
	NotificationReceiverRepository notificationReceiverRepository;
	@Autowired
	NotificationRepository notificationRepository;
	
	@Transactional
	public void setReceiver(Notification notification, Report report) {
		
		List<TrackUser> trackedUsers = trackUserRepository.findByLocationAndLoginIdNot(report.getLocation(),notification.getCreatedBy());
		
		if (trackedUsers != null) {
			trackedUsers.forEach(tU -> {
				
				NotificationReceivers receivers = new NotificationReceivers();
				receivers.setNotification(notification);
				receivers.setSeenStatus(SeenStatus.UNSEEN);
				receivers.setReceiverId(tU.getLoginId());
				receivers.setReport(report);
				notificationReceiverRepository.save(receivers);
				
				/*DistanceCalculation distanceCalculation= new DistanceCalculation(); 
				
				double distance = distanceCalculation.distance(report.getLalitude(), report.getLongitude(), tU.getLalitude(), tU.getLongitude());
				if(distance <= 300) {
					
				NotificationReceivers receivers = new NotificationReceivers();
				receivers.setNotification(notification);
				receivers.setSeenStatus(SeenStatus.UNSEEN);
				receivers.setReceiverId(tU.getLoginId());
				receivers.setReport(report);
				notificationReceiverRepository.save(receivers);
				}*/
			});
		}
	}
}
