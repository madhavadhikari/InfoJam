package com.sevensemesterproject.infoJam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sevensemesterproject.infoJam.model.Contribution;
import com.sevensemesterproject.infoJam.util.ContributionEnum;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long>{

	List<Contribution> findByReportId(Long id);

	List<Contribution> findByCreatedBy(Long loginId);

	Long countByReportIdAndContributionEnum(Long id, ContributionEnum yes);

}
