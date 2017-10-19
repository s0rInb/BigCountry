package com.gmail.s0rInb.DTO;

import com.gmail.s0rInb.authentication.ScopeComponent;
import com.gmail.s0rInb.entities.Patient;
import com.gmail.s0rInb.entities.PatientComment;
import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PatientCommentDTO {
    Long id;
    Long patientId;
    String lastName;
    String firstName;
    String comment;
    LocalDateTime date;
    String color;

    public PatientCommentDTO(PatientComment patientComment) {
        this.id = patientComment.getId();
        this.patientId = patientComment.getPatientId();
        this.firstName = patientComment.getUser().getFirstName();
        this.lastName = patientComment.getUser().getLastName();
        this.comment = patientComment.getComment();
        this.date = patientComment.getDate();
        patientComment.getNotReadManager().stream().map(User::getId)
                .filter(aLong -> aLong.equals(ScopeComponent.getCurrentUser().getId()))
                .findFirst().ifPresent(aLong -> this.color = "#a9dfbf");
    }
}
