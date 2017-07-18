package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UseRepository")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    @Query("select new map(u.id as id, u.firstName as firstName, u.lastName as lastName) from User u")
    Page<User> findAllUsers(Pageable pageable);

    @Query("select new map(u.id as id) from User u")
    List<User> findList();

    User findByUsername(String username);
}
