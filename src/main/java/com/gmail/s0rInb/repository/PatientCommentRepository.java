package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.PatientComment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PatientCommentsRepository")
public interface PatientCommentRepository extends PagingAndSortingRepository<PatientComment,Long> {
    List<PatientComment> findAllByPatientId(Long patientId);
}
