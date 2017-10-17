package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gmail.s0rInb.Utils.LocalDateDeserializer;
import com.gmail.s0rInb.Utils.LocalDateSerializer;
import com.gmail.s0rInb.entities.dictionary.ConsultationPatientStatus;
import com.gmail.s0rInb.entities.dictionary.LegalSupportPatientStatus;
import com.gmail.s0rInb.entities.dictionary.LegalSupportResult;
import com.gmail.s0rInb.entities.dictionary.WhoLegalSupport;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "legal_support")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Audited
public class LegalSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


//	@Column(name = "need_legal_support")
//	private String needLegalSupport;

	@Column(name = "date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;
	@ManyToOne
	@JoinColumn(name = "who_legal_support_id")
	private WhoLegalSupport whoLegalSupport;

	@Column(name = "appeal_min_health")
	private String appealMinHealth;

	@Column(name = "appeal_min_health_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate appealMinHealthDate;

	@Column(name = "result_min_health_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate resultMinHealthDate;

	@Column(name = "appeal_rus_health")
	private String appealRusHealth;

	@Column(name = "appeal_rus_health_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate appealRusHealthDate;

	@Column(name = "result_rus_health_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate resultRusHealthDate;
	
	@Column(name = "appeal_procurator")
	private String appealProcurator;



	@Column(name = "appeal_procurator_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate appealProcuratorHealthDate;

	@Column(name = "result_procurator_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate resultProcuratorHealthDate;
	
	@Column(name = "appeal_court")
	private String appealCourt;

	@Column(name = "appeal_court_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate appealCourtDate;

	@Size(max = 2048)
	@Column(name = "base_court")
	private String baseCourt;

	@Column(name = "planned_court_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate plannedCourtDate;

	@Column(name = "count_court")
	private Long countCourt;

	@Size(max = 2048)
	@Column(name = "result_court")
	private String resultCourt;

	@Size(max = 256)
	@Column(name = "advocate_full_name")
	private String advocateFullName;

	@Size(max = 256)
	@Column(name = "drug")
	private String drug;

	@ManyToOne
	@JoinColumn(name = "legal_support_result_id")
	private LegalSupportResult legalSupportResult;

	@Size(max = 2048)
	@Column(name = "legal_support_result_text")
	private String legalSupportResultText;

	@ManyToOne
	@JoinColumn(name = "legalSupport_patient_status_id")
	private LegalSupportPatientStatus legalSupportPatientStatus;

	@Column(name = "comments")
	private String comments;
}
