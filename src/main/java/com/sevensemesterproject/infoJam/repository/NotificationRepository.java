package com.sevensemesterproject.infoJam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevensemesterproject.infoJam.model.Notification;
import com.sevensemesterproject.infoJam.util.NotificationStatus;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

	List<Notification> findByNotificationStatusNot(NotificationStatus expired);

}
