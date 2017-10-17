package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "consultation_patient_status")
@Entity
@Audited
public class ConsultationPatientStatus extends Dictionary{
}
