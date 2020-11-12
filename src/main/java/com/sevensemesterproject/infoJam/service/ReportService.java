package com.sevensemesterproject.infoJam.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.controller.UserController;
import com.sevensemesterproject.infoJam.exception.AlreadyExistException;
import com.sevensemesterproject.infoJam.exception.NotFoundException;
import com.sevensemesterproject.infoJam.model.Contribution;
import com.sevensemesterproject.infoJam.model.Notification;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.ContributionRepository;
import com.sevensemesterproject.infoJam.repository.NotificationReceiverRepository;
import com.sevensemesterproject.infoJam.repository.NotificationRepository;
import com.sevensemesterproject.infoJam.repository.ReportRepository;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.ReportEditRequest;
import com.sevensemesterproject.infoJam.request.ReportRegisterRequest;
import com.sevensemesterproject.infoJam.response.ReportResponse;
import com.sevensemesterproject.infoJam.util.ContributionEnum;
import com.sevensemesterproject.infoJam.util.ContributionType;
import com.sevensemesterproject.infoJam.util.DistanceCalculation;
import com.sevensemesterproject.infoJam.util.NotificationStatus;
import com.sevensemesterproject.infoJam.util.ReportStatus;
import com.sevensemesterproject.infoJam.util.Status;

@Service
public class ReportService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private TrackUserRepository trackUserRepository;
	@Autowired
	private CommonService commonService;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private NotificationReceiverService receiverService;
	@Autowired
	private ContributionRepository contributionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	NotificationReceiverRepository notificationReceiverRepository;
	
	@Transactional
	public Report setReport(ReportRegisterRequest request, Long loginId,  String token) {
		
		LOG.debug("--->Registering report<---");
		
		commonService.isValidToken(loginId, token);
		
		Report report = new Report();
		TrackUser trackUser = trackUserRepository.findByLoginId(loginId);
		List<Report> reports = reportRepository.findByReportStatusNot(ReportStatus.EXPIRED);
		for(Report r : reports) {
			DistanceCalculation distanceCalculation= new DistanceCalculation(); 
			double d = distanceCalculation.distance(r.getLalitude(), r.getLongitude(), trackUser.getLalitude(), trackUser.getLongitude());
			if(d <= 300) {
				throw new AlreadyExistException("Report already exist");
			}
		}
		report.setJamStatus(request.getJamstatus());
		report.setCreatedBy(loginId);
		report.setStatus(Status.ACTIVE);
		report.setReportStatus(ReportStatus.ACTIVE);
		report.setCreatedDate(new Date());
		report.setLalitude(trackUser.getLalitude());
		report.setLongitude(trackUser.getLongitude());
		report.setLocation(trackUser.getLocation());
		report = reportRepository.save(report);
		setContribution(report);
		setNotification(report);
		return report;	
	}
	
	@Transactional
	public ReportResponse getReport(Long id, Long loginId, String token) {
		commonService.isValidToken(loginId, token);
		Report report = reportRepository.findByIdAndReportStatusNot(id, ReportStatus.EXPIRED);
		if(report == null) {
			throw new NotFoundException("Report Not found or has been expired");
		}
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ReportResponse reportResponse = new ReportResponse();
		LOG.debug("-->Getting report<--");
		
		reportResponse.setLocation(report.getLocation());
		reportResponse.setId(report.getId());
		reportResponse.setJamStatus(report.getJamStatus());
		reportResponse.setReportStatus(report.getReportStatus());
		reportResponse.setCreatedDate(df.format(report.getCreatedDate()));
		reportResponse.setReporterId(report.getCreatedBy());
		reportResponse.setLongitude(report.getLongitude());
		reportResponse.setLalitude(report.getLalitude());
		
		User user = userRepository.getOne(loginId); 
		reportResponse.setReporter(user.getFullName());
		
		Long contributionCounts = contributionRepository.countByReportIdAndContributionEnum(id , ContributionEnum.YES);
		reportResponse.setContributiuonCount(contributionCounts);
		return reportResponse;
	}
	
	@Transactional
	public List<ReportResponse> getAllReports() {
		List<ReportResponse> listedReports = new ArrayList<>();
		List<Report> reports = reportRepository.findByOrderByCreatedDateDesc();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		for(Report r : reports) {
			ReportResponse reportResponse = new ReportResponse();
			
			reportResponse.setLocation(r.getLocation());
			reportResponse.setId(r.getId());
			reportResponse.setJamStatus(r.getJamStatus());
			reportResponse.setReportStatus(r.getReportStatus());
			reportResponse.setCreatedDate(df.format(r.getCreatedDate()));
			reportResponse.setReporterId(r.getCreatedBy());
			reportResponse.setLongitude(r.getLongitude());
			reportResponse.setLalitude(r.getLalitude());
			User user = userRepository.getOne(r.getCreatedBy()); 
			reportResponse.setReporter(user.getFullName());
			
			Long contributionCounts = contributionRepository.countByReportIdAndContributionEnum(r.getId() , ContributionEnum.YES);
			reportResponse.setContributiuonCount(contributionCounts);
			
			listedReports.add(reportResponse);
		}
		return listedReports;
	}

	@Transactional
	public Report editReport(ReportEditRequest reportEditRequest, Long loginId, String token) {
		commonService.isValidToken(loginId, token);
		LOG.debug("editing report");
		Report report = reportRepository.getOne(reportEditRequest.getId());
		report.setJamStatus(reportEditRequest.getJamstatus());
		report.setLocation(reportEditRequest.getLocation());
		LOG.debug("edited report");
		return report = reportRepository.save(report);
	}

	@Transactional
	public void deleteReport(Long id) {
		
		Report reportToDelete = reportRepository.getOne(id);
		reportToDelete.setStatus(Status.DELETED);
		
	}
	@Transactional
	public List<ReportResponse> getAllReportsForMobile() {
		List<ReportResponse> listedReports = new ArrayList<>();
		List<Report> reports = reportRepository.findByReportStatusNot(ReportStatus.EXPIRED);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		
		for(Report r : reports) {
			ReportResponse reportResponse = new ReportResponse();
			
			reportResponse.setLocation(r.getLocation());
			reportResponse.setId(r.getId());
			reportResponse.setJamStatus(r.getJamStatus());
			reportResponse.setReportStatus(r.getReportStatus());
			reportResponse.setCreatedDate(df.format(r.getCreatedDate()));
			reportResponse.setCreatedTime(tf.format(r.getCreatedDate()));
			reportResponse.setReporterId(r.getCreatedBy());
			reportResponse.setLongitude(r.getLongitude());
			reportResponse.setLalitude(r.getLalitude());
			User user = userRepository.getOne(r.getCreatedBy()); 
			reportResponse.setReporter(user.getFullName());
			
			Long contributionCounts = contributionRepository.countByReportIdAndContributionEnum(r.getId() , ContributionEnum.YES);
			reportResponse.setContributiuonCount(contributionCounts);
			
			listedReports.add(reportResponse);
		}
		return listedReports;
	}
	
	public void setNotification(Report report) {
		Notification notification = new Notification();
		notification.setReport(report);
		notification.setNotificationStatus(NotificationStatus.ACTIVE);
		notification.setCreatedBy(report.getCreatedBy());
		notification.setCreatedDate(report.getCreatedDate());
		notificationRepository.save(notification);
		receiverService.setReceiver(notification, report);
	}

	public void setContribution(Report report) {
		Contribution cont = new Contribution();
		cont.setReport(report);
		cont.setCreatedDate(new Date());
		cont.setContributionType(ContributionType.REPORTER);
		cont.setCreatedBy(report.getCreatedBy());
		contributionRepository.save(cont);
		
	}
		
	/*
	 * @Transactional public ReportResponse getLocationReport(String location, Long
	 * loginId, String token) { commonService.isValidToken(loginId, token);
	 * DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 * 
	 * ReportResponse reportResponse = new ReportResponse();
	 * 
	 * Report report = reportRepository.findByLocation(location);
	 * reportResponse.setLocation(report.getLocation());
	 * reportResponse.setId(report.getId());
	 * reportResponse.setJamStatus(report.getJamStatus());
	 * reportResponse.setCreatedDate(df.format(report.getCreatedDate()));
	 * reportResponse.setReporterId(report.getCreatedBy());
	 * reportResponse.setLalitude(report.getLalitude());
	 * reportResponse.setLongitude(report.getLongitude()); return reportResponse; }
	 */

}
