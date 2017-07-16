package com.gmail.s0rInb.entities.Files;

import com.gmail.s0rInb.entities.Patient;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "INFO_CONSENTS")
public class InfoConsentFile extends Files{
	@ManyToOne
	@JoinColumn(name = "patient_id")
	@JsonIgnore
	@JsonBackReference
	private Patient patient;

}
