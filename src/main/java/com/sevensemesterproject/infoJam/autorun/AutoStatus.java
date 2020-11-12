package com.sevensemesterproject.infoJam.autorun;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sevensemesterproject.infoJam.model.Notification;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.repository.NotificationRepository;
import com.sevensemesterproject.infoJam.repository.ReportRepository;
import com.sevensemesterproject.infoJam.util.JamStatus;
import com.sevensemesterproject.infoJam.util.NotificationStatus;
import com.sevensemesterproject.infoJam.util.ReportStatus;

@Component
public class AutoStatus {
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Scheduled(fixedRate=30000)
	public void updateReportStatus() {
		List<Report> reports = reportRepository.findAll();
		
		for(Report r : reports) {
				
			Date date1 =  new Date();
			Date date2 = r.getCreatedDate();
			Long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
				
			if(r.getJamStatus().equals(JamStatus.LOW)) {
			    if(diffInMillies > 600000) {
			    	r.setReportStatus(ReportStatus.EXPIRED);
			    }
			    	
			  }
			else if(r.getJamStatus().equals(JamStatus.MEDIUM)) {
			    	
			    if(diffInMillies > 1200000) {
			    	r.setReportStatus(ReportStatus.EXPIRED);
			    }
			    	
			   }
			 else if(r.getJamStatus().equals(JamStatus.HIGH)) {
			    	
				 if(diffInMillies > 1800000) {
			    	r.setReportStatus(ReportStatus.EXPIRED);
			    	}
			     }
			reportRepository.save(r);
	   } 		
	}
	
	@Scheduled(fixedRate=30000)
	public void updateNotificationStatus() {
		List<Notification> notifications = notificationRepository.findByNotificationStatusNot(NotificationStatus.EXPIRED);
		
		for(Notification n : notifications) {
			
			Report report = reportRepository.findByid(n.getReport().getId());
		
			if(report.getReportStatus() == ReportStatus.ACTIVE){
			
				n.setNotificationStatus(NotificationStatus.ACTIVE);
			}
			else
				n.setNotificationStatus(NotificationStatus.EXPIRED);
		
			notificationRepository.save(n);
		}	
	}
}



