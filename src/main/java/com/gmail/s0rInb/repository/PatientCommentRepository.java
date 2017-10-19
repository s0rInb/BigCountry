package com.gmail.s0rInb.repository;

import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.PatientComment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PatientCommentsRepository")
public interface PatientCommentRepository extends PagingAndSortingRepository<PatientComment, Long> {
    List<PatientComment> findAllByPatientId(Long patientId);

    @Query("select distinct pc.patientId from PatientComment pc left join pc.notReadManager nrm where nrm.id=:userId")
    List<Long> getNotReadListPatientIdByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM patientcommentnotread " +
            "WHERE user_id=:userId AND " +
            "comment_id IN (SELECT comment_id FROM patient_comment WHERE patient_id=:patientId)"
            , nativeQuery = true)
    void deleteNotReadByPatientIdAndUserId(@Param("patientId") Long patientId, @Param("userId") Long userId);
}
