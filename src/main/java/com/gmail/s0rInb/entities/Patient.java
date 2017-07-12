package com.gmail.s0rInb.entities;

import com.gmail.s0rInb.entities.dictionary.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by s0rInb on 11.07.2017.
 */
@Entity
@Table(name = "patient")
@Getter
@Setter
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Transient
	@Setter(AccessLevel.NONE)
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

	@Column(name = "birthday")
	private LocalDate birthday;

	@Transient
	@Setter(AccessLevel.NONE)
	private Integer age;

	@Column(name = "info_consent")
	private Boolean infoConsent;

	@Column(name = "info_consent_date")
	private LocalDate infoConsentDate;

	@ManyToOne
	@JoinColumn(name = "expert_center_id")
	private ExpertCenter expertCenter;

	@ManyToOne
	@JoinColumn(name = "subject_rf_id")
	private SubjectRF subjectRF;

	@Size(max = 320)
	@Column(name = "city")
	private String city;

	@Size(max = 320)
	@Column(name = "med_agent_full_name")
	private String medAgentFullName;

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
	@JoinColumn(name = "who_coll_id")
	private WhoCall whoCall;

	@Size(max = 520)
	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "hot_line_call_date")
	private LocalDate hotLineCallDate;

	public String getPatientId() {
		return (getSurname()!=null?getSurname().substring(0,1):"") +
				(getName()!=null?getName().substring(0,1):"") +
				(getPatronymic()!=null?getPatronymic().substring(0,1):"") +
				id.toString();
	}
	public Integer getAge() {
		return getBirthday()!=null?(LocalDate.ofEpochDay(LocalDate.now().toEpochDay()-getBirthday().toEpochDay()).getYear()):null;
	}
}
