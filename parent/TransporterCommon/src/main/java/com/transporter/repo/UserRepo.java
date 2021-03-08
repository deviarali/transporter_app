package com.transporter.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transporter.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("SELECT count(*) from User u where u.userrole.id=:roleId")
	int getTotalUsersCountByRole(@Param(value = "roleId") int roleId);

	@Query("SELECT count(*) from User u where u.userrole.id=:roleId and u.createdOn between :startTime and :endTime")
	int getTotalUsersCountForToday(@Param(value = "roleId") int roleId, @Param(value = "startTime") Date startTime,
			@Param(value = "endTime") Date endTime);

}
