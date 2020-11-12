package com.sevensemesterproject.infoJam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevensemesterproject.infoJam.model.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

	Login findByUserId(Long id);

	Login findByIdAndToken(Long loginId, String token);

}
