package com.gmail.s0rInb.entities;

import com.gmail.s0rInb.entities.dictionary.Diagnosis;
import com.gmail.s0rInb.entities.dictionary.ExpertCenter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "doctor_expert_center")
@Getter
@Setter
@Audited
public class DoctorExpertCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String patientId;

    @Size(max = 320)
    @NotNull
    @Column(name = "name")
    private String name;

    @Size(max = 320)
    @NotNull
    @Column(name = "surname")
    private String surname;

    @Size(max = 320)
    @Column(name = "patronymic")
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "expert_center_id")
    private ExpertCenter expertCenter;

//    @ManyToMany
//    @JoinTable(name = "doctor_ec_expert_center",
//            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "expert_center_id", referencedColumnName = "id"))
//    private List<Diagnosis> diagnoses;
}
