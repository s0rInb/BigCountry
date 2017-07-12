package com.gmail.s0rInb.entities;

import com.gmail.s0rInb.entities.dictionary.ConsultationType;
import com.gmail.s0rInb.entities.dictionary.WhoSentToConsultation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "consultation")
@Getter
@Setter
public class Consultation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "consultation_type_id")
	private ConsultationType consultationType;

	@Size(max = 320)
	@Column(name = "doctor_name")
	private String doctorName;

	@Size(max = 320)
	@Column(name = "doctor_surname")
	private String doctorSurname;

	@Size(max = 320)
	@Column(name = "doctor_patronymic")
	private String doctorPatronymic;

	@Size(max = 320)
	@Column(name = "doctor_lpu")
	private String doctorLPU;

	@Size(max = 320)
	@Column(name = "doctor_position")
	private String doctorPosition;

	@Size(max = 320)
	@Column(name = "doctor_contact_info")
	private String doctorContactInfo;

	@ManyToOne
	@JoinColumn(name = "who_sent_to_cons_id")
	private WhoSentToConsultation whoSentToConsultation;

	@Column(name = "full_document_date")
	private LocalDate fullDocumentDate;

	@Column(name = "full_document_send_date")
	private LocalDate fullDocumentSendDate;

	@Column(name = "consultation_date")
	private LocalDate consultationDate;

	@Size(max = 320)
	@Column(name = "additional_research")
	private String additionalResearch;

	@Column(name = "conclusion_date")
	private LocalDate conclusionDate;

	@Column(name = "send_region_date")
	private LocalDate sendRegionDate;

	@Column(name = "medical_commission")
	private Boolean medicalCommission;

	@Column(name = "medical_commission_date")
	private LocalDate medicalCommissionDate;

	@Column(name = "medical_commission_result")
	private Boolean medicalCommissionResult;

	@Column(name = "medical_commission_confirm")
	private Boolean medicalCommissionConfirm;
}
