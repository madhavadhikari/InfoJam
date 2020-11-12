package com.sevensemesterproject.infoJam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevensemesterproject.infoJam.model.TrackUser;
import com.sevensemesterproject.infoJam.util.TrackStatus;

public interface TrackUserRepository extends JpaRepository<TrackUser, Long>{

	TrackUser findByLoginId(Long loginId);

	List<TrackUser> findByLocation(String location);

	List<TrackUser> findByLoginIdNotAndTrackStatus(Long createdBy, TrackStatus updated);

	List<TrackUser> findByLocationAndLoginIdNot(String location, Long createdBy);

}
