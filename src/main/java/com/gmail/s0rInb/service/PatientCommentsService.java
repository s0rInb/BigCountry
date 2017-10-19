package com.gmail.s0rInb.service;

import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.PatientComment;
import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.entities.UserRole;
import com.gmail.s0rInb.repository.PatientCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("patientCommentService")
@Transactional
public class PatientCommentsService {

    @Autowired
    PatientCommentRepository patientCommentRepository;

    @Autowired
    UserService userService;
    public List<PatientComment> findAllByPatientId(Long patientId) {
        return patientCommentRepository.findAllByPatientId(patientId);
    }
    public void deleteNotReadByPatientIdAndCurrentUserId(Long patientId) {
        patientCommentRepository.deleteNotReadByPatientIdAndUserId(patientId,ScopeComponent.getCurrentUser().getId());
    }

    public PatientComment createPatientComment(PatientComment patientComment, Long patientId) {
        if (patientComment.getId() != null) return null;

        patientComment.setDate(LocalDateTime.now());
        patientComment.setUser(ScopeComponent.getCurrentUser());
        patientComment.setPatientId(patientId);
        patientComment.setNotReadManager(
                userService.findAll().stream()
                .filter(user -> !user.getId().equals(patientComment.getUser().getId()) && user.getUserRole().equals(UserRole.MANAGER))
                        .collect(Collectors.toList())
        );
        return patientCommentRepository.save(patientComment);
    }

    public List<Long> getNotReadListPatientIdByUserId(Long userId){
        return patientCommentRepository.getNotReadListPatientIdByUserId(userId);
    }
}
