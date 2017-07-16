package com.gmail.s0rInb.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.gmail.s0rInb.entities.Files.AppealProcuratorFile;
import com.gmail.s0rInb.entities.Files.AppealProcuratorFileTest;
import com.gmail.s0rInb.entities.dictionary.ConsultationType;
import com.gmail.s0rInb.entities.dictionary.LegalSupportResult;
import com.gmail.s0rInb.entities.dictionary.WhoLegalSupport;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "legal_support")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class LegalSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "need_legal_support")
	private Boolean needLegalSupport;

	@ManyToOne
	@JoinColumn(name = "who_legal_support_id")
	private WhoLegalSupport whoLegalSupport;

	@Column(name = "appeal_min_health")
	private Boolean appealMinHealth;

	@Column(name = "appeal_min_health_date")
	private LocalDate appealMinHealthDate;

	@Column(name = "result_min_health_date")
	private LocalDate resultMinHealthDate;

	@Column(name = "appeal_rus_health")
	private Boolean appealRusHealth;

	@Column(name = "appeal_rus_health_date")
	private LocalDate appealRusHealthDate;

	@Column(name = "result_rus_health_date")
	private LocalDate resultRusHealthDate;
	
	@Column(name = "appeal_procurator")
	private Boolean appealProcurator;



	@Column(name = "appeal_procurator_date")
	private LocalDate appealProcuratorHealthDate;

	@Column(name = "result_procurator_date")
	private LocalDate resultProcuratorHealthDate;
	
	@Column(name = "appeal_court")
	private Boolean appealCourt;

	@Column(name = "appeal_court_date")
	private LocalDate appealCourtDate;

	@Size(max = 2048)
	@Column(name = "base_court")
	private String baseCourt;

	@Column(name = "planned_court_date")
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

//	@OneToMany(mappedBy = "legalSupport")
//	private Set<AppealProcuratorFileTest> appealProcuratorFileTests;
}
