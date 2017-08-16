package com.gmail.s0rInb.service;

import com.gmail.s0rInb.entities.DoctorExpertCenter;
import com.gmail.s0rInb.repository.DoctorExpertCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("doctorExpertCenterService")
@Transactional
public class DoctorExpertCenterService {
    @Autowired
    DoctorExpertCenterRepository doctorExpertCenterRepository;

    public List<DoctorExpertCenter> findAll() {
        return doctorExpertCenterRepository.findAll();
    }

    public DoctorExpertCenter findOne(Long id) {
        return doctorExpertCenterRepository.findOne(id);
    }
}
