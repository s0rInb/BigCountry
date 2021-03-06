package com.gmail.s0rInb.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.*;
import com.gmail.s0rInb.entities.dictionary.Diagnosis;
import com.gmail.s0rInb.entities.dictionary.ExpertCenter;
import com.gmail.s0rInb.entities.dictionary.SubjectRF;
import com.gmail.s0rInb.entities.dictionary.WhoCall;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
	private Long id;
	private String patientId;
	private String name;
	private String surname;
	private String patronymic;
	private String sex;
	private String rusSex;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthday;
	private Integer age;
	private String infoConsent;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate infoConsentDate;
	private ExpertCenter expertCenter;
	private SubjectRF subjectRF;
	private String city;
	private Diagnosis diagnosis;
	private String phone;
	private String email;
	private String address;
	private WhoCall whoCall;
	private String contactInfo;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate hotLineCallDate;
	private Consultation consultation;
	private LegalSupport legalSupport;
	private String circulationChannelText;
	private String circulationChannel;
	private String caseStatus;
	private String comments;
	private DoctorExpertCenter doctorExpertCenter;

	private PatientDTO(Patient patient) {
		this.id = patient.getId();
		this.patientId = patient.getPatientId();
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.patronymic = patient.getPatronymic();
		this.sex = patient.getSex();
		this.birthday = patient.getBirthday();
		this.age = patient.getAge();
		this.infoConsent = patient.getInfoConsent();
		this.infoConsentDate = patient.getInfoConsentDate();
		this.expertCenter = patient.getExpertCenter();
		this.subjectRF = patient.getSubjectRF();
		this.city = patient.getCity();
		this.diagnosis = patient.getDiagnosis();
		this.phone = patient.getPhone();
		this.email = patient.getEmail();
		this.address = patient.getAddress();
		this.whoCall = patient.getWhoCall();
		this.contactInfo = patient.getContactInfo();
		this.hotLineCallDate = patient.getHotLineCallDate();
		this.consultation = patient.getConsultation();
		this.legalSupport = patient.getLegalSupport();
		this.rusSex = patient.getRusSex();
		this.circulationChannel=patient.getCirculationChannel();
		this.circulationChannelText=patient.getCirculationChannelText();
		this.caseStatus=patient.getCaseStatus();
		this.comments=patient.getComments();
		this.doctorExpertCenter=patient.getDoctorExpertCenter();
	}

	public PatientDTO(Patient patient, UserRole userRole) {
		this(patient);
		switch (userRole) {
			case MANAGER:
				break;
			case CUSTOMER:
				customerPatientDTO(patient);
				break;
		}
	}

	private void customerPatientDTO(Patient patient) {
		this.name = null;
		this.surname = null;
		this.patronymic = null;
		this.phone = null;
		this.address = null;
		this.email = null;
		this.contactInfo = null;
		this.birthday=null;
		if (this.getConsultation()!=null){
			Consultation consultation = this.getConsultation();
			consultation.setDoctorSurname(null);
			consultation.setDoctorContactInfo(null);
			consultation.setDoctorName(null);
			consultation.setDoctorPatronymic(null);
			consultation.setDoctorLPU(null);
			consultation.setDoctorPosition(null);
			this.consultation=consultation;
		}

	}
}
