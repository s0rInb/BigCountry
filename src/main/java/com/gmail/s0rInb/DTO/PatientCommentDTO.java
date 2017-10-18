package com.gmail.s0rInb.DTO;

import com.gmail.s0rInb.entities.PatientComment;
import com.gmail.s0rInb.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PatientCommentDTO {
    Long id;
    Long patientId;
    String lastName;
    String firstName;
    String comment;
    LocalDateTime date;

    public PatientCommentDTO(PatientComment patientComment) {
        this.id = patientComment.getId();
        this.patientId = patientComment.getPatientId();
        this.firstName = patientComment.getUser().getFirstName();
        this.lastName = patientComment.getUser().getLastName();
        this.comment = patientComment.getComment();
        this.date = patientComment.getDate();
    }
}
