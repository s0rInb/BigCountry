package com.gmail.s0rInb.service;

import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Leporidae on 03.12.2015.
 */

@Service("userService")
//@Repository
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAllByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> findList() {
        return userRepository.findList();
    }

    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
