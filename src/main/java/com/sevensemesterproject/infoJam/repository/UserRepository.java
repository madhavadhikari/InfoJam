package com.sevensemesterproject.infoJam.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sevensemesterproject.infoJam.model.User;
import com.sevensemesterproject.infoJam.util.Status;
import com.sevensemesterproject.infoJam.util.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);

	List<User> findByUserRoleNotAndStatusNotOrderByFullNameAsc(UserRole admin, Status deleted);
}
