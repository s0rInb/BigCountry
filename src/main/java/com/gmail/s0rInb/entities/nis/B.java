package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class B {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "par_nis_id")
	private Nis parNis;

	private String programId;

	private String projectName;

	private String agentName;

	private String applicationId;

	private String address;

	private String email;

	private String country;

	private String phone;

	private String fax;
}
