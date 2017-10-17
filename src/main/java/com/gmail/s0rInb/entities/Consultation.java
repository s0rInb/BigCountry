package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.dictionary.ConsultationPatientStatus;
import com.gmail.s0rInb.entities.dictionary.ConsultationType;
import com.gmail.s0rInb.entities.dictionary.WhoSentToConsultation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Audited
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

	public void setFullDocumentDate(LocalDate fullDocumentDate) {
		if(this.getConsultationType()!=null&&Objects.equals(this.getConsultationType().getName(), "Очная")) {
			this.fullDocumentDate = fullDocumentDate;
		} else{
			this.fullDocumentDate=null;
		}
	}

	public void setFullDocumentSendDate(LocalDate fullDocumentSendDate) {
		if(this.getConsultationType()!=null&&Objects.equals(this.getConsultationType().getName(), "Очная")) {
			this.fullDocumentSendDate = fullDocumentSendDate;
		} else{
			this.fullDocumentSendDate=null;
		}
	}

	@Column(name = "full_document_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fullDocumentDate;

	@Column(name = "full_document_send_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate fullDocumentSendDate;

	@Column(name = "consultation_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate consultationDate;

	@Size(max = 320)
	@Column(name = "additional_research")
	private String additionalResearch;

	@Column(name = "conclusion_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate conclusionDate;

	@Column(name = "send_region_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate sendRegionDate;

	@Column(name = "medical_commission")
	private String medicalCommission;

	@Column(name = "medical_commission_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate medicalCommissionDate;

	@Column(name = "medical_commission_result")
	private String medicalCommissionResult;

	@Column(name = "medical_commission_confirm")
	private String medicalCommissionConfirm;

	@ManyToOne
	@JoinColumn(name = "consultation_patient_status_id")
	private ConsultationPatientStatus consultationPatientStatus;

	@Column(name = "comments")
	private String comments;
}

