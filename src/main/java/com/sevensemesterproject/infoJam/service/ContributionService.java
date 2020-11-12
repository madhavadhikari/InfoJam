package com.sevensemesterproject.infoJam.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevensemesterproject.infoJam.exception.AlreadyExistException;
import com.sevensemesterproject.infoJam.model.Contribution;
import com.sevensemesterproject.infoJam.model.NotificationReceivers;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.repository.ContributionRepository;
import com.sevensemesterproject.infoJam.repository.NotificationReceiverRepository;
import com.sevensemesterproject.infoJam.repository.ReportRepository;
import com.sevensemesterproject.infoJam.repository.TrackUserRepository;
import com.sevensemesterproject.infoJam.repository.UserRepository;
import com.sevensemesterproject.infoJam.request.ContributionRequest;
import com.sevensemesterproject.infoJam.response.ContributionResponse;
import com.sevensemesterproject.infoJam.response.NewGetAllMyReportAndContributionResponse;
import com.sevensemesterproject.infoJam.response.RCResponse;
import com.sevensemesterproject.infoJam.response.ReportContributionResponse;
import com.sevensemesterproject.infoJam.util.ContributionEnum;
import com.sevensemesterproject.infoJam.util.ContributionType;
import com.sevensemesterproject.infoJam.util.DistanceCalculation;
import com.sevensemesterproject.infoJam.util.ReportStatus;
import com.sevensemesterproject.infoJam.util.SeenStatus;

@Service
public class ContributionService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ReportRepository reportRepository;
	@Autowired
	TrackUserRepository trackRepository;
	@Autowired
	ContributionRepository contributionRepository;
	@Autowired
	CommonService commonService;
	@Autowired
	NotificationReceiverRepository receiverRepository;
	
	@Transactional
	public void sendContribution(ContributionRequest request, Long loginId, String token) {
		
		commonService.isValidToken(loginId, token);
	
	/*	TrackUser trackUser = trackRepository.findByLoginId(loginId);
		List<Report> reports = reportRepository.findByReportStatusNot(ReportStatus.EXPIRED);
		for(Report r : reports) {
			DistanceCalculation distanceCalculation= new DistanceCalculation(); 
			double d = distanceCalculation.distance(r.getLalitude(), r.getLongitude(), trackUser.getLalitude(), trackUser.getLongitude());
			if(d <= 300) {
				NotificationReceivers receivers = receiverRepository.findByReceiverIdAndReportId(loginId,r.getId());
				Contribution contribution = new Contribution();
				contribution.setCreatedBy(loginId);
				contribution.setCreatedDate(new Date());
				contribution.setReport(receivers.getReport());
				contribution.setContributionEnum(request.getContributionEnum());
				contribution.setContributionType(ContributionType.CONTRIBUTOR);
				NotificationReceivers r1 = receiverRepository.findByReceiverIdAndReport(loginId,receivers.getReport());
				r1.setSeenStatus(SeenStatus.SEEN);
				receiverRepository.save(r1);
				contributionRepository.save(contribution);
			}
		}*/
		
		TrackUser trackedUser = trackRepository.findByLoginId(loginId);
		
		Report report = reportRepository.findByLocationAndReportStatus(trackedUser.getLocation(), ReportStatus.ACTIVE);
		
		Contribution contribution = new Contribution();
		contribution.setCreatedBy(loginId);
		contribution.setCreatedDate(new Date());
		contribution.setReport(report);
		contribution.setContributionEnum(request.getContributionEnum());
		contribution.setContributionType(ContributionType.CONTRIBUTOR);
		NotificationReceivers receiver = receiverRepository.findByReceiverIdAndReport(loginId,report);
		receiver.setSeenStatus(SeenStatus.SEEN);
		receiverRepository.save(receiver);
		contributionRepository.save(contribution);
	}

	@Transactional
	public List<ContributionResponse> getAllContribution(Report report){
		List<ContributionResponse> contributionResponses = new ArrayList<>();
		List<Contribution> contribution = contributionRepository.findByReportId(report.getId());
		contribution.forEach(c ->{
			ContributionResponse contributionResponse = new ContributionResponse();
			contributionResponse.setId(c.getId());
			contributionResponse.setContributionType(c.getContributionType());
			contributionResponse.setContributionEnum(c.getContributionEnum());
			contributionResponse.setContributor(c.getCreatedBy());
			contributionResponses.add(contributionResponse);
		});
		return contributionResponses;
		
	}
	
	@Transactional
	public List<RCResponse> getMyReportAndContribution(Long loginId, String token) throws ParseException {
		
		commonService.isValidToken(loginId, token);
		
		List<NewGetAllMyReportAndContributionResponse> finalResponse = getMyReportsAndContributions(loginId);
		
		Map<String, List<NewGetAllMyReportAndContributionResponse>> result = finalResponse.stream()
				.collect(Collectors.groupingBy(NewGetAllMyReportAndContributionResponse::getCreatedDate));
		// Date is taken as String for Response And It is Mapped By NewGetAllMyReportAndContributionResponse as new response  
		// where date is formatted in the form jane 24, 2019...

		List<RCResponse> listRCresponse = new ArrayList<>();
		result.entrySet().forEach(m -> {
			RCResponse RCresponse = new RCResponse();
			
			RCresponse.setCreatedDate(m.getKey()); // key obtained from Map
			
			List<ReportContributionResponse> listedrcResponse = new ArrayList<>();
			
			m.getValue().stream().forEach(s -> {
			
			ReportContributionResponse rcResponse = new ReportContributionResponse();
			
			rcResponse.setReportId(s.getReportId());
			rcResponse.setContributionType(s.getContributionType());
			User user = userRepository.getOne(loginId);
			rcResponse.setCreatedBy(user.getFullName());
			
			listedrcResponse.add(rcResponse);
			
			});
			
			RCresponse.setRcr(listedrcResponse);
			listRCresponse.add(RCresponse);
			
		
		});
			
	return listRCresponse;

    }
	
	public List<NewGetAllMyReportAndContributionResponse> getMyReportsAndContributions(Long loginId) throws ParseException{
		
			List<NewGetAllMyReportAndContributionResponse> finalresponse = new ArrayList<>();
			List<Contribution> contributions = contributionRepository.findByCreatedBy(loginId);
			int size = contributions.size();
	
			for(int i = 0; i < size; i++){
				DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy");
				NewGetAllMyReportAndContributionResponse res = new NewGetAllMyReportAndContributionResponse();
	
				Date date = contributions.get(i).getCreatedDate();
				String dateWithNoTime = fmt.format(date); 
				res.setCreatedDate(dateWithNoTime);
				if(contributions.get(i).getContributionType() == ContributionType.CONTRIBUTOR && contributions.get(i).getContributionEnum() == ContributionEnum.NO)
					continue;
	    
				res.setContributionType(contributions.get(i).getContributionType());
				res.setReportId(contributions.get(i).getReport().getId());
				finalresponse.add(res);
			}
			return finalresponse;
		}
}