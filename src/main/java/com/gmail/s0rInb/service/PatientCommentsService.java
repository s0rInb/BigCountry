package com.gmail.s0rInb.service;

import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.PatientComment;
import com.gmail.s0rInb.repository.PatientCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service("patientCommentService")
@Transactional
public class PatientCommentsService {

    @Autowired
    PatientCommentRepository patientCommentRepository;

    public List<PatientComment> findAllByPatientId(Long patientId) {
        return patientCommentRepository.findAllByPatientId(patientId);
    }
    public PatientComment createPatientComment(PatientComment patientComment, Long patientId){
        if (patientComment.getId()!=null) return null;

        patientComment.setDate(LocalDateTime.now());
        patientComment.setUser(ScopeComponent.getCurrentUser());
        patientComment.setPatientId(patientId);
        return patientCommentRepository.save(patientComment);
    };
}
