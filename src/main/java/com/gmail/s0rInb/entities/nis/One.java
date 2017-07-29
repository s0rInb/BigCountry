package com.gmail.s0rInb.entities.nis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class One {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "par_nis_id")
	private Nis parNis;

	private String name;

	private String surname;

	private String address;

	private String index;

	private String country;

	private String email;

	private String phone;

	private String fax;

	private String applicant;

	private String doctorText;

	private String anotherText;

	private String reg;
}
