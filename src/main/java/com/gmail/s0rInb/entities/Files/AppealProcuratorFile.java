package com.gmail.s0rInb.entities.Files;

import com.gmail.s0rInb.entities.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "FACT_EXPENSE")
public class AppealProcuratorFile extends Files {
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
}

