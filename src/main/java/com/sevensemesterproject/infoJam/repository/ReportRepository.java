package com.sevensemesterproject.infoJam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sevensemesterproject.infoJam.model.Report;
import com.sevensemesterproject.infoJam.util.ReportStatus;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	Report findByLocation(String location);

	List<Report> findByCreatedBy(Long loginId);

	Report findByIdAndReportStatusNot(Long id, ReportStatus expired);

	Report findByid(Long id);

	Report findByLocationAndReportStatusNot(String location, ReportStatus expired);

	List<Report> findByReportStatusNot(ReportStatus expired);

	Report findByLocationAndReportStatus(String location, ReportStatus active);

	List<Report> findByOrderByCreatedDateDesc();

}