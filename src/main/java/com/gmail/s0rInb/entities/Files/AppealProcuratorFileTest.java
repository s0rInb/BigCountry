package com.gmail.s0rInb.entities.Files;

import com.gmail.s0rInb.entities.LegalSupport;
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
@DiscriminatorValue(value = "FACT_Sfa")
public class AppealProcuratorFileTest extends Files {
	@ManyToOne
	@JoinColumn(name = "legalSupport_id")
	private LegalSupport legalSupport;
}

