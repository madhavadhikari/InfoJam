package com.sevensemesterproject.infoJam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sevensemesterproject.infoJam.model.NotificationReceivers;
import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.util.SeenStatus;

@Repository
public interface NotificationReceiverRepository extends JpaRepository<NotificationReceivers, Long>{
	
	NotificationReceivers findByReceiverId(Long loginId);

	NotificationReceivers findByReceiverIdAndSeenStatusNot(Long loginId, SeenStatus seen);

	NotificationReceivers findByReceiverIdAndReport(Long loginId, Report report);

	NotificationReceivers findByReceiverIdAndReportIdAndSeenStatusNot(Long loginId, Long id, SeenStatus seen);

	NotificationReceivers findByReceiverIdAndReportId(Long loginId, Long id);

}
