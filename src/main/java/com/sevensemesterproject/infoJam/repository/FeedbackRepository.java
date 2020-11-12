package com.sevensemesterproject.infoJam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sevensemesterproject.infoJam.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

	List<Feedback> findByLoginId(Long loginId);

	List<Feedback> findByOrderByCreatedDateDesc();

}
