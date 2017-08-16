package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.DoctorExpertCenter;
import com.gmail.s0rInb.entities.Patient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("DoctorExpertCenterRepository")
public interface DoctorExpertCenterRepository extends PagingAndSortingRepository<DoctorExpertCenter,Long> {
    List<DoctorExpertCenter> findAll();
}
