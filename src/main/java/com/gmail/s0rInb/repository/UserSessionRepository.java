package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.entities.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service("userSessionService")
@Repository()
//@Transactional
public interface UserSessionRepository extends PagingAndSortingRepository<UserSession, Long>{
	@Transactional(readOnly=true)
	List<UserSession> findAll();

	UserSession findByUser(User user);

	UserSession findByToken(String token);

	void deleteByUser(User user);
}
