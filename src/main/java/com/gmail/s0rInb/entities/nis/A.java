package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class A {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "par_nis_id")
	private Nis parNis;

	private String nisId;

	private String centerId;

	private String patientId;

	private String nis;

	private String exclude;
}
