package com.gmail.s0rInb.entities.dictionary;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "legalSupport_patient_status")
@Entity
@Audited
public class LegalSupportPatientStatus extends Dictionary {
}
