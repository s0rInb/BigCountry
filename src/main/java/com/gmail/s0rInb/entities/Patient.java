package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.dictionary.Diagnosis;
import com.gmail.s0rInb.entities.dictionary.ExpertCenter;
import com.gmail.s0rInb.entities.dictionary.SubjectRF;
import com.gmail.s0rInb.entities.dictionary.WhoCall;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Created by s0rInb on 11.07.2017.
 */
@Entity
@Table(name = "patient")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Audited
public class Patient {
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

	@Size(max = 24)
	@Column(name = "sex")
	private String sex;

	@Transient
	@Setter(AccessLevel.NONE)
	private String rusSex;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@Column(name = "birthday")
	private LocalDate birthday;

	@Transient
	@Setter(AccessLevel.NONE)
	private Integer age;

	@Column(name = "info_consent")
	private String infoConsent;

	@Column(name = "info_consent_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate infoConsentDate;

//	@OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
//	@JsonManagedReference
//	private Set<InfoConsentFile> infoConsentFiles;

	@ManyToOne
	@JoinColumn(name = "expert_center_id")
	private ExpertCenter expertCenter;

	@ManyToOne
	@JoinColumn(name = "doctor_expert_center_id")
	private DoctorExpertCenter doctorExpertCenter;

	@ManyToOne
	@JoinColumn(name = "subject_rf_id")
	private SubjectRF subjectRF;

	@Size(max = 320)
	@Column(name = "city")
	private String city;

	@ManyToOne
	@JoinColumn(name = "diagnosis_id")
	private Diagnosis diagnosis;


	@Column(name = "phone")
	@Size(max = 30)
	private String phone;

	@Size(max = 120)
	@Column(name = "email")
	private String email;

	@Size(max = 520)
	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "who_call_id")
	private WhoCall whoCall;

	@Size(max = 520)
	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "hot_line_call_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate hotLineCallDate;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "consultation_id")
	private Consultation consultation;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "legal_support_id")
	private LegalSupport legalSupport;

	private String caseStatus;

	private String comments;


	@Size(max = 120)
	@Column(name = "circulationChannel")
	private String circulationChannel;

	@Size(max = 520)
	@Column(name = "circulationChannelText")
	private String circulationChannelText;

	public void setCirculationChannelText(String circulationChannelText) {
		if(Objects.equals(this.getCirculationChannel(), "another")) {
			this.circulationChannelText = circulationChannelText;
		} else{
			this.circulationChannelText=null;
		}
	}

	public String getPatientId() {
		return (getSurname() != null ? getSurname().substring(0, 1) : "") +
				(getName() != null ? getName().substring(0, 1) : "") +
				(getPatronymic() != null ? getPatronymic().substring(0, 1) : "") +
				id.toString();
	}

	public void setPatientId(String patientId) {
		this.patientId = getPatientId();
	}

	public Integer getAge() {
		return getBirthday() != null ? (Period.between(getBirthday(), LocalDate.now()).getYears()) : null;
	}

	public String getRusSex() {
		if (getSex() != null) {
			switch (getSex()) {
				case "male":
					return "Мужской";
				case "female":
					return "Женский";
			}
		}
		return null;
	}
}
